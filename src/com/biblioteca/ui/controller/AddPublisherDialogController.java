package com.biblioteca.ui.controller;

import com.biblioteca.core.Publisher;
import com.biblioteca.core.facade.Library;
import com.biblioteca.datasource.DataSource;
import com.biblioteca.ui.utils.Dialogs;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Controller class that manages the window dialog for adding new publishers.
 *
 */
public class AddPublisherDialogController implements DialogController<Publisher> {

    @FXML
    private GridPane rootNode;

    @FXML
    private TextField name;

    @Override
    public boolean checkData() {
        var test1 = name.getText().isEmpty();

        var test2 = DataSource.getInstance()
                .getPublishers()
                .stream()
                .anyMatch(p -> p.getName().toLowerCase().equals(name.getText().toLowerCase()));

        if (test1) {
            Dialogs.showAlertDialog("Per favore inserisci un nome valido", rootNode.getScene().getWindow());
            return false;
        } else if (test2) {
            Dialogs.showAlertDialog(String.format("L'editore '%s' è gia presente nel database", name.getText()), rootNode.getScene().getWindow());
            return false;
        }

        return true;
    }

    public Publisher confirmAndGet() {
        return Library.getInstance().newPublisher(name.getText());
    }
}
