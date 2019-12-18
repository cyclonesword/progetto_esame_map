package com.biblioteca.ui.model;

import com.biblioteca.core.Book;
import com.biblioteca.core.Customer;
import com.biblioteca.core.Loan;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;


// === !! Adapter pattern used here. === //
public class TableViewLoan implements Loan {

    private Loan loan;

    private SimpleIntegerProperty loanId = new SimpleIntegerProperty();
    private SimpleStringProperty user = new SimpleStringProperty();
    private SimpleStringProperty reservedBook = new SimpleStringProperty();

    private SimpleStringProperty startDate = new SimpleStringProperty();
    private SimpleStringProperty expectedReturnDate = new SimpleStringProperty();
    private SimpleStringProperty endDate = new SimpleStringProperty();

    public TableViewLoan(Loan loan) {
        this.loan = loan;
        loanId.set(getLoanId());
        user.set(getCustomer().getId() + " - " +getCustomer().getFullName());
        reservedBook.set(getBook().getTitle());
        startDate.set(getLoanDate().toString());
        expectedReturnDate.set(getExpectedReturnDate().toString());
        endDate.set(getReturnDate() != null ? getReturnDate().toString() : "");
    }

    @Override
    public Book getBook() {
        return loan.getBook();
    }

    @Override
    public Customer getCustomer() {
        return loan.getCustomer();
    }

    @Override
    public LocalDate getLoanDate() {
        return loan.getLoanDate();
    }

    @Override
    public LocalDate getExpectedReturnDate() {
        return loan.getExpectedReturnDate();
    }

    @Override
    public LocalDate getReturnDate() {
        return loan.getReturnDate();
    }

    @Override
    public void setReturnDate(LocalDate returnDate) {
        loan.setReturnDate(returnDate);
    }

    @Override
    public int getLoanId() {
        return loan.getLoanId();
    }

    @Override
    public void setId(int id) {
        loan.setId(id);
    }

    public String getUser() {
        return user.get();
    }

    public String getReservedBook() {
        return reservedBook.get();
    }

    public String getStartDate() {
        return startDate.get();
    }


    public String getEndDate() {
        return endDate.get();
    }

    @Override
    public void confirm() {
        loan.confirm();
    }
}
