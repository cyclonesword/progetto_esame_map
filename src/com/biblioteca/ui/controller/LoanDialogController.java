package com.biblioteca.ui.controller;

import com.biblioteca.core.Book;
import com.biblioteca.core.Customer;
import com.biblioteca.core.Loan;
import com.biblioteca.core.facade.Library;
import com.biblioteca.datasource.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

/**
 * The controller class associated to the New Loan dialog.
 */
public class LoanDialogController implements DialogController <Loan> {

    @FXML
    public DatePicker startDatePicker;

    @FXML
    public DatePicker endDatePicker;

    @FXML
    private TextField reservedBook;

    @FXML
    private ComboBox<Customer> usersCombobox;

    private DataSource ds = DataSource.getDefault();
    private Book book;

    public void initialize() {
        ObservableList<Customer> allUsers = FXCollections.observableArrayList(ds.getCustomers());
        usersCombobox.setItems(allUsers);

        // maxmium day is today + 60 days
        var maxDate = LocalDate.now().plusDays(60); // Max 2 month
        // minimum date is today
        var minDate = LocalDate.now();

        startDatePicker.setDayCellFactory(d -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isBefore(minDate));
            }
        });
        startDatePicker.setValue(LocalDate.now());

        endDatePicker.setDayCellFactory(d -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
            }
        });
        endDatePicker.setValue(LocalDate.now());
        usersCombobox.getSelectionModel().selectFirst();
    }

    public void setLentBook(Book b) {
        this.book = b;
        reservedBook.setText(b.getId() + " - " + b.getTitle());
    }

    /**
     * Confirm the data and creates a new loan .
     * @throws LoanDialogException
     */
    @Override
    public Loan confirmAndGet() throws LoanDialogException {
        final Customer selectedUser = usersCombobox.getSelectionModel().getSelectedItem();

        return Library.getInstance().newLoan(startDatePicker.getValue(), endDatePicker.getValue(), selectedUser, book);
    }

    public static class LoanDialogException extends RuntimeException {
        public LoanDialogException() {
        }

        public LoanDialogException(String message) {
            super(message);
        }
    }
}
