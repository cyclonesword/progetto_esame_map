package com.biblioteca.core;

import javafx.scene.image.Image;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookImpl implements Book {

    private String name;
    private String ISBN;
    private List<Author> authors = new ArrayList<>();
    //  private BookStatus bookStatus = BookStatus.defaultStatus;
    private Loan currentLoan;
    private List<Loan> loanHistory = new ArrayList<>();
    private String description;
    private Image image;
    private int quantity;

    public BookImpl(String name, String ISBN) {
        this.name = name;
        this.ISBN = ISBN;
    }

    public BookImpl(String name, String ISBN, String description) {
        this.name = name;
        this.ISBN = ISBN;
        this.description = description;
    }

    public BookImpl(String name, String ISBN, String description, int quantity) {
        this.name = name;
        this.ISBN = ISBN;
        this.description = description;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getISBN() {
        return ISBN;
    }

    @Override
    public Image getImage() {
        return image;
    }

    public BookImpl setImage(Image image) {
        this.image = image;
        return this;
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
        BookImpl book = (BookImpl) o;
        return ISBN.equals(book.ISBN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ISBN);
    }

    public List<Author> getAuthors() {
        return authors;
    }

    @Override
    public void addAuthor(Author author) {
        authors.add(author);
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    public BookImpl setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public String getDescription() {
        return description;
    }

    private static class LoanException extends RuntimeException { }
}
