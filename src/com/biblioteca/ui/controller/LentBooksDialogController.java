package com.biblioteca.ui.controller;

import com.biblioteca.core.facade.Library;
import com.biblioteca.datasource.DataSource;
import com.biblioteca.ui.items.TableViewLoanRow;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * The controller responsible for the Lent Books Dialog management.
 */
public class LentBooksDialogController implements DialogController {

    public TableColumn<TableViewLoanRow, Integer> id;
    public TableColumn<TableViewLoanRow, String> reservedBook;
    public TableColumn<TableViewLoanRow, String> user;
    public TableColumn<TableViewLoanRow, String> startDate;
    public TableColumn<TableViewLoanRow, String> status;
    public TableColumn<TableViewLoanRow, String> expectedReturnDate;

    @FXML
    private TableView<TableViewLoanRow> tableView;

    private DataSource ds = DataSource.getDefault();

    public void initialize() {
        var items = FXCollections.observableArrayList(ds.getLoans()
                .stream()
                .map(TableViewLoanRow::new)
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
        returnedMenuItem.setOnAction(event -> {
            var i = tableView.getSelectionModel().getSelectedItem();
            if (i.getStatus().equals("not-returned")) {
                i.setReturnDate(LocalDate.now());
                i.setStatus("returned");
                i.getBook().setQuantity(i.getBook().getQuantity() + 1);
                tableView.refresh();
            }
        });

        MenuItem deleteMenuItem = new MenuItem("Delete selected");
        deleteMenuItem.setOnAction(event -> {
            var i = tableView.getSelectionModel().getSelectedItem();
            Library.getInstance().removeLoan(i.getLoan());
            if (i.getStatus().equals("not-returned"))
                i.getBook().setQuantity(i.getBook().getQuantity() + 1);
            items.remove(i);
            tableView.refresh();
        });

        ContextMenu menu = new ContextMenu(returnedMenuItem, deleteMenuItem);
        tableView.setContextMenu(menu);
        tableView.setOnMouseClicked(e -> {
            var selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                tableView.getSelectionModel().selectFirst();
            } else if (selected.getStatus().equals("returned")) {
                menu.getItems().remove(returnedMenuItem);
            } else {
                menu.getItems().add(returnedMenuItem);
            }
        });
    }

    @Override
    public Object confirmAndGet() {
        throw new UnsupportedOperationException();
    }
}
