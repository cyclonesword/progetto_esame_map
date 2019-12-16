package com.biblioteca.ui.controller;

import com.biblioteca.core.Loan;
import com.biblioteca.datasource.DataSource;
import com.biblioteca.ui.model.TableViewLoan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.stream.Collectors;

public class ReservedBooksDialogController {

    public TableColumn<TableViewLoan, Integer> id;
    public TableColumn<TableViewLoan, String> reservedBook;
    public TableColumn<TableViewLoan, String> user;
    public TableColumn<TableViewLoan, String> startDate;
    public TableColumn<TableViewLoan, String> endDate;
    public TableColumn<TableViewLoan, String> expectedReturnDate;

    @FXML
    private TableView<TableViewLoan> tableView;

    private DataSource ds = DataSource.getDefault();

    public void initialize() {
        var items = FXCollections.observableArrayList(ds.readLoans()
                .stream()
                .map(TableViewLoan::new)
                .collect(Collectors.toList())
        );

        id.setCellValueFactory(new PropertyValueFactory<>("loanId"));
        reservedBook.setCellValueFactory(new PropertyValueFactory<>("reservedBook"));
        user.setCellValueFactory(new PropertyValueFactory<>("user"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        expectedReturnDate.setCellValueFactory(new PropertyValueFactory<>("expectedReturnDate"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        tableView.setItems(items);
    }

}
