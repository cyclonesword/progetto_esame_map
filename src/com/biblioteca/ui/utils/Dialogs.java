package com.biblioteca.ui.utils;

import com.biblioteca.ui.controller.DialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Window;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Utility class to create window dialogs.
 */
public class Dialogs {

    private static final Dialogs instance = new Dialogs(); // This instance is necessary because the getClass().getResource(..) method does not work on static contexts.

    /**
     * Opens a window dialog
     * @param dialogTitle The dialog title
     * @param okButton The text displayed in the ok button
     * @param fxml The FXML file containing the user interface to be displayed
     * @param ownerWindow  The window that owns this dialog (i.e the window that requested to open the new dialog). Interaction outside the dialog is disabled.
     * @param preConditions A lambda expression called BEFORE the user clicks on the Ok button
     * @param postConditions  A lambda expression called AFTER the user clicks on the Ok button
     * @param <T> The type of the controller. must be coherent with the FXML file.
     * @throws IOException In case of errors reading the FXML file.
     */
    public static <T extends DialogController> void showDialog(String dialogTitle, String okButton, String fxml, Window ownerWindow, Consumer<T> preConditions, Consumer<T> postConditions) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(instance.getClass().getResource(fxml)); // Loads the FXML containing the Graphical user interface of the Dialog.

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.getDialogPane().setContent(fxmlLoader.load()); // Set the GUI to the dialog
        dialog.initOwner(ownerWindow); // Set the parent window as its owner
        // dialog.getDialogPane().getButtonTypes().add(new ButtonType(okButton, ButtonBar.ButtonData.OK_DONE));
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.setTitle(dialogTitle);

        T controller = fxmlLoader.getController();
        controller.setOnConfirmClickedEventListener(dialog);
        if(preConditions != null)
            preConditions.accept(controller); // do pre-conditions stuff

        dialog.showAndWait();

        //if (dialog.getResult().getButtonData().equals(ButtonBar.ButtonData.OK_DONE)) {
        if (dialog.getResult().equals(ButtonType.OK)) {
            if(postConditions != null)
                postConditions.accept(controller); // do post-conditions stuff
        }
    }

    public static <T extends DialogController> void showDialog(String dialogTitle, String fxml, Window ownerWindow, Consumer<T> preConditions, Consumer<T> postConditions) throws IOException {
        showDialog(dialogTitle, "Save", fxml, ownerWindow,preConditions,postConditions);
    }

    /**
     * Shows a simple Alert Dialog with a given message.
     * @param message The message to display in the alert dialog.
     * @param owner Return the alert dialog for further usage.
     */
    public static void showAlertDialog(String message, Window owner) {
        Alert alert = getDialog(Alert.AlertType.WARNING, message, owner);
        alert.showAndWait();
    }

    public static void showInfoDialog(String message, Window owner, Runnable doSomething) {
        var alert = getDialog(Alert.AlertType.INFORMATION, message,owner);
        alert.getButtonTypes().add(ButtonType.CANCEL);
        alert.showAndWait();
        if(alert.getResult().equals(ButtonType.OK)) {
            if(doSomething != null)
                doSomething.run();
        }
    }


    private static Alert getDialog(Alert.AlertType type, String message, Window owner) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.initOwner(owner);
        return alert;
    }


//    public static void showInfoDialog(String message, Window owner) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
//        alert.initOwner(owner);
//        alert.showAndWait();
//    }
}
