package com.biblioteca.core;

import javafx.scene.image.Image;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class BookImpl implements Book {

    private String title;
    private String ISBN;
    private List<Author> authors = new ArrayList<>();
    //  private BookStatus bookStatus = BookStatus.defaultStatus;
    private Loan currentLoan;
    private List<Loan> loanHistory = new ArrayList<>();
    private String description;
    private Image image;
    private int quantity;
    private String subTitle;
    private int year;
    private Publisher publisher;
    private int id;
    private List<Category> categories = new ArrayList<>();
    private String format;

    public BookImpl() {
    }

    public BookImpl(String title, String ISBN) {
        this.title = title;
        this.ISBN = ISBN;
    }

    public BookImpl(String title, String ISBN, String description) {
        this.title = title;
        this.ISBN = ISBN;
        this.description = description;
    }

    public BookImpl(String title, String ISBN, String description, int quantity) {
        this.title = title;
        this.ISBN = ISBN;
        this.description = description;
        this.quantity = quantity;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public List<? extends Category> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public String getTitle() {
        return title;
    }

    public String getISBN() {
        return ISBN;
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public String getFormat() {
        return format;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<? extends Author> getAuthors() {
        return authors;
    }

    @Override
    public void addAuthor(Author author) {
        authors.add(author);
    }

    @Override
    public void addCategories(List<? extends Category> categories) {
        this.categories.addAll(categories);
    }

    @Override
    public void setFormat(String format) {
        this.format = format;
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

    @Override
    public String getSubtitle() {
        return subTitle;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public Publisher getPublisher() {
        return publisher;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setCurrentLoan(Loan currentLoan) {
        this.currentLoan = currentLoan;
    }

    public void setLoanHistory(List<Loan> loanHistory) {
        this.loanHistory = loanHistory;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    private static class LoanException extends RuntimeException { }
}
