package com.biblioteca.core;

import java.time.LocalDate;
import java.util.Calendar;

public class StandardLoan implements Loan {

    private int id;
    private Customer customer;
    private Book book;

    private LocalDate loanDate;
    private LocalDate expectedReturnDate;
    private String status;

    public StandardLoan(int id, Customer customer, Book book, LocalDate loanDate, LocalDate expectedReturnDate) {
        this.id = id;
        this.customer = customer;
        this.book = book;
        this.loanDate = loanDate;
        this.expectedReturnDate = expectedReturnDate;
    }

    @Override
    public void confirm() {
        id = Math.toIntExact(Calendar.getInstance().getTimeInMillis());
        customer.addLoan(this);
    }

    @Override
    public Book getBook() {
        return book;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public int getLoanId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public LocalDate getLoanDate() {
        return loanDate;
    }

    @Override
    public LocalDate getExpectedReturnDate() {
        return expectedReturnDate;
    }

    @Override
    public void setReturnDate(LocalDate returnDate) {
        this.expectedReturnDate = returnDate;
    }

    @Override
    public String toString() {
        return id + " - " + book.toString() + " - " + customer;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
