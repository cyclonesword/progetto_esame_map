package com.biblioteca.ui.controller;

import com.biblioteca.datasource.DataSource;
import com.biblioteca.ui.model.TableViewLoan;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class ReservedBooksDialogController implements DialogController {

    public TableColumn<TableViewLoan, Integer> id;
    public TableColumn<TableViewLoan, String> reservedBook;
    public TableColumn<TableViewLoan, String> user;
    public TableColumn<TableViewLoan, String> startDate;
    public TableColumn<TableViewLoan, String> status;
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
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableView.setItems(items);

        MenuItem returnedMenuItem = new MenuItem("Set as returned");
        returnedMenuItem.setOnAction((ActionEvent event) -> {
            var i = tableView.getSelectionModel().getSelectedItem();
            if(i.getStatus().equals("not-returned")) {
                i.setReturnDate(LocalDate.now());
                i.setStatus("returned");
                i.getBook().setQuantity(i.getBook().getQuantity() + 1);
                tableView.refresh();
            }
        });

        MenuItem deleteMenuItem = new MenuItem("Delete selected");
        deleteMenuItem.setOnAction((ActionEvent event) -> {
            var i = tableView.getSelectionModel().getSelectedItem();
            ds.readLoans().remove(i.getLoan());
            items.remove(i);
            if(i.getStatus().equals("not-returned"))
                i.getBook().setQuantity(i.getBook().getQuantity() + 1);
            tableView.refresh();
        });

        ContextMenu menu = new ContextMenu(returnedMenuItem, deleteMenuItem);
        tableView.setContextMenu(menu);
        tableView.setOnMouseClicked(e -> {
            var selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                tableView.getSelectionModel().selectFirst();
            }
        });
    }

}
