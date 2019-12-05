package com.biblioteca.core;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class StandardLoan implements Loan {

    private Customer customer;
    private LocalDateTime loanDate;
    private LocalDateTime expectedReturnDate;
    private LocalDateTime actualReturnDate;
    private int id;

    public StandardLoan(Customer customer, LocalDateTime loanDate, LocalDateTime expectedReturnDate) {
        this.customer = customer;
        this.loanDate = loanDate;
        this.expectedReturnDate = expectedReturnDate;
    }

    @Override
    public void confirm() {
        id = Math.toIntExact(Calendar.getInstance().getTimeInMillis());
        customer.addLoan(this);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    @Override
    public LocalDateTime getExpectedReturnDate() {
        return expectedReturnDate;
    }

    @Override
    public LocalDateTime getReturnDate() {
        return actualReturnDate;
    }

    @Override
    public void setReturnDate(LocalDateTime returnDate) {
        this.actualReturnDate = returnDate;
    }
}
