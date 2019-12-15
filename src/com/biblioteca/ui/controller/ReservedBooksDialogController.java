package com.biblioteca.ui.controller;

import com.biblioteca.core.Loan;
import com.biblioteca.datasource.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ReservedBooksDialogController {

    @FXML
    private ListView<Loan> listView;

    private DataSource ds = DataSource.getDefault();

    public void initialize() {
        ObservableList<Loan> items = FXCollections.observableArrayList(ds.readLoans());
        listView.setItems(items);
    }

}
