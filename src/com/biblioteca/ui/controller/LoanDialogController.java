package com.biblioteca.ui.controller;

import com.biblioteca.core.Book;
import com.biblioteca.core.Customer;
import com.biblioteca.core.Loan;
import com.biblioteca.datasource.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class LoanDialogController implements DialogController{

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
        ObservableList<Customer> allUsers = FXCollections.observableArrayList(ds.readCustomers());
        usersCombobox.setItems(allUsers);

        var maxDate = LocalDate.now().plusDays(60); // Max 2 month
        var minDate = LocalDate.now();

        startDatePicker.setDayCellFactory(d -> new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isBefore(minDate));
                    }});
        startDatePicker.setValue(LocalDate.now());

        endDatePicker.setDayCellFactory(d -> new DateCell() {
            @Override public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isAfter(maxDate));
            }});
        endDatePicker.setValue(LocalDate.now());
        usersCombobox.getSelectionModel().selectFirst();
    }

    public void setReservedBook(Book b) {
        this.book = b;
        reservedBook.setText(b.getId() + " - " + b.getTitle());
    }

    public Loan getLoan() throws LoanDialogException {
        final Customer selectedUser = usersCombobox.getSelectionModel().getSelectedItem();

        if(selectedUser == null)
            throw new LoanDialogException("No user selected for a new loan");

        return new Loan.Builder()
                .setLoanDate(startDatePicker.getValue())
                .setExpectedReturnDate(endDatePicker.getValue())
                .setCustomer(selectedUser)
                .setBook(book)
                .build();

    }

    public static class LoanDialogException extends RuntimeException {
        public LoanDialogException() {
        }

        public LoanDialogException(String message) {
            super(message);
        }
    }
}
