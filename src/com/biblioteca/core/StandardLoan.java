package com.biblioteca.core;

import java.time.LocalDate;
import java.util.Calendar;

public class StandardLoan implements Loan {

    private int id;
    private Customer customer;
    private Book book;

    private LocalDate loanDate;
    private LocalDate expectedReturnDate;
    private LocalDate actualReturnDate;

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
    public int getLoanId() {
        return id;
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
    public LocalDate getReturnDate() {
        return actualReturnDate;
    }

    @Override
    public void setReturnDate(LocalDate returnDate) {
        this.actualReturnDate = returnDate;
    }

    @Override
    public String toString() {
        return id + " - " + book.toString() + " - " + customer;
    }
}
