package com.biblioteca.ui.controller;

import com.biblioteca.core.Author;
import com.biblioteca.core.AuthorImpl;
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
        var test1 = name.getText().isEmpty()
                || DataSource.getDefault()
                .readAuthors()
                .stream()
                .anyMatch(a -> a.getName().toLowerCase().equals(name.getText().toLowerCase()));

        if (test1) {
            Dialogs.showAlertDialog("Tutti i campi sono obbligatori", rootNode.getScene().getWindow());
            return false;
        }

        return true;
    }

    public Author getAuthor() {
        var id = DataSource.getDefault().readAuthors()
                .stream()
                .map(Author::getId)
                .max(Comparator.naturalOrder()).get() + 1;

        return new AuthorImpl(id, name.getText());
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
