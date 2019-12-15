package com.biblioteca.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Window;

import java.io.IOException;
import java.util.function.Consumer;

public class Dialogs {

    private static final Dialogs instance = new Dialogs(); // This instance is necessary because the getClass().getResource(..) method does not work on static contexts.

    public static <T> void showDialog(String dialogTitle,  String fxml, Window ownerWindow, Consumer<T> preConditions, Consumer<T> postConditions) throws IOException {
        showDialog(dialogTitle, "Save", fxml, ownerWindow,preConditions,postConditions);
    }

    public static <T> void showDialog(String dialogTitle, String okButton, String fxml, Window ownerWindow, Consumer<T> preConditions, Consumer<T> postConditions) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(instance.getClass().getResource(fxml)); // Loads the FXML containing the Graphical user interface of the Dialog.

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.getDialogPane().setContent(fxmlLoader.load()); // Set the GUI to the dialog
        dialog.initOwner(ownerWindow); // Set the parent window as its owner
        dialog.getDialogPane().getButtonTypes().add(new ButtonType(okButton, ButtonBar.ButtonData.OK_DONE));
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.setTitle(dialogTitle);

        T controller = fxmlLoader.getController();
        preConditions.accept(controller); // do pre-conditions stuff

        dialog.showAndWait();

        if (dialog.getResult().getButtonData().equals(ButtonBar.ButtonData.OK_DONE)) {
            postConditions.accept(controller); // do post-conditions stuff
        }
    }
}
