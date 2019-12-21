package com.biblioteca.ui.controller;

import com.biblioteca.core.Author;
import com.biblioteca.core.AuthorImpl;
import com.biblioteca.core.facade.Library;
import com.biblioteca.datasource.DataSource;
import com.biblioteca.ui.Dialogs;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.Comparator;

public class AddAuthorDialogController implements DialogController {

    @FXML
    private TextField name;
    @FXML
    private GridPane rootNode;
    @FXML
    private Dialog<ButtonType> dialog;

    @Override
    public boolean checkData() {
        var test1 = name.getText().isEmpty();
        var test2 = DataSource.getDefault()
                .readAuthors()
                .stream()
                .anyMatch(a -> a.getName().toLowerCase().equals(name.getText().toLowerCase()));

        if (test1) {
            Dialogs.showAlertDialog("Tutti i campi sono obbligatori", rootNode.getScene().getWindow());
            return false;
        } else if(test2) {
            Dialogs.showAlertDialog(String.format("L'autore '%s' è gia presente nel database", name.getText()), rootNode.getScene().getWindow());
            return false;
        }

        return true;
    }

    public Author confirmAndGet() {
        return Library.getInstance().newAuthor(name.getText());
    }

    @Override
    public void setDialog(Dialog<ButtonType> dialog) {
        this.dialog = dialog;
        DialogController.super.setDialog(dialog);
    }

    @Override
    public Dialog<ButtonType> getDialog() {
        return dialog;
    }
}
