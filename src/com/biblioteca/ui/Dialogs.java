package com.biblioteca.ui;

import com.biblioteca.ui.controller.DialogController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Window;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Dialogs {

    private static final Dialogs instance = new Dialogs(); // This instance is necessary because the getClass().getResource(..) method does not work on static contexts.

    public static <T extends DialogController> void showDialog(String dialogTitle, String fxml, Window ownerWindow, Consumer<T> preConditions, Consumer<T> postConditions) throws IOException {
        showDialog(dialogTitle, "Save", fxml, ownerWindow,preConditions,postConditions);
    }

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
        controller.setDialog(dialog);
        if(preConditions != null)
            preConditions.accept(controller); // do pre-conditions stuff

        dialog.showAndWait();

        //if (dialog.getResult().getButtonData().equals(ButtonBar.ButtonData.OK_DONE)) {
        if (dialog.getResult().equals(ButtonType.OK)) {
            if(postConditions != null)
                postConditions.accept(controller); // do post-conditions stuff
        }
    }

    public static void showAlertDialog(String message, Window owner) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.initOwner(owner);
        alert.showAndWait();
    }

    public static void showInfoDialog(String message, Window owner) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.initOwner(owner);
        alert.showAndWait();
    }
}
