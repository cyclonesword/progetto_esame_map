package com.biblioteca.core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class Book {

    private String name;
    private String ISBN;
    //  private BookStatus bookStatus = BookStatus.defaultStatus;
    private Loan currentLoan;
    private List<Loan> loanHistory = new ArrayList<>();

    public Book(String name, String ISBN) {
        this.name = name;
        this.ISBN = ISBN;
    }

    public String getName() {
        return name;
    }

    public String getISBN() {
        return ISBN;
    }

    public Loan getCurrentLoan() {
        return currentLoan;
    }

    public void lendBookTo(Customer customer, LocalDateTime expectedReturnDate, LocalDateTime loanDate) throws LoanException {
        if (currentLoan != null)
            throw new LoanException();
        
        currentLoan = new Loan.Builder()
                .setCustomer(customer)
                .setLoanDate(loanDate)
                .setExpectedReturnDate(expectedReturnDate)
                .build();


    }

    public void setAsReturned(LocalDateTime returnDate) {
        currentLoan.setReturnDate(returnDate);
        loanHistory.add(currentLoan);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return ISBN.equals(book.ISBN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ISBN);
    }

    private class LoanException extends Exception {
    }
}
