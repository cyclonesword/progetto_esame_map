package com.biblioteca.ui.model;

import com.biblioteca.core.Book;
import com.biblioteca.core.Customer;
import com.biblioteca.core.Loan;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.StringJoiner;


// === !! Adapter pattern used here. === //
public class TableViewLoan implements Loan {

    private Loan loan;

    private SimpleIntegerProperty loanId = new SimpleIntegerProperty();
    private SimpleStringProperty user = new SimpleStringProperty();
    private SimpleStringProperty reservedBook = new SimpleStringProperty();

    private SimpleStringProperty startDate = new SimpleStringProperty();
    private SimpleStringProperty expectedReturnDate = new SimpleStringProperty();
    private SimpleStringProperty status = new SimpleStringProperty();

    public TableViewLoan(Loan loan) {
        this.loan = loan;
        loanId.set(getLoanId());
        user.set(getCustomer().getId() + " - " +getCustomer().getFullName());
        reservedBook.set(getBook().getTitle());
        startDate.set(getLoanDate().toString());
        expectedReturnDate.set(getExpectedReturnDate().toString());
        status.set(loan.getStatus());
     //   endDate.set(getReturnDate() != null ? getReturnDate().toString() : "Not Returned");
    }

    public Loan getLoan() {
        return loan;
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
    public void setReturnDate(LocalDate returnDate) {
        this.expectedReturnDate.set(returnDate.toString());
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


    public String getStatus() {
        return status.get();
    }

    @Override
    public void setStatus(String status) {
        loan.setStatus(status);
        this.status.set(status);
    }

    @Override
    public void confirm() {
        loan.confirm();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TableViewLoan.class.getSimpleName() + "[", "]")
                .add("loan=" + loan)
                .add("loanId=" + loanId)
                .add("user=" + user)
                .add("reservedBook=" + reservedBook)
                .add("startDate=" + startDate)
                .add("expectedReturnDate=" + expectedReturnDate)
                .add("endDate=" + status)
                .toString();
    }
}
