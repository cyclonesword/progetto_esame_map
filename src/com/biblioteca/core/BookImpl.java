package com.biblioteca.core;

import com.biblioteca.ui.utils.Images;
import com.biblioteca.ui.utils.BookImage;

import java.util.*;

/**
 * The default implementation of the Book interface. See {@link Book} for the javadoc of the getters and setters.
 */
public class BookImpl implements Book {

    private String title;
    private String ISBN;
    private List<Author> authors = new ArrayList<>();
    //  private BookStatus bookStatus = BookStatus.defaultStatus;
    private Loan currentLoan;
    private List<Loan> loanHistory = new ArrayList<>();
    private String description;

    // Setting the default image
    private BookImage image = Images.BOOK_DEFAULT;

    private int quantity;
    private String subtitle;
    private int year;
    private Publisher publisher;
    private int id;
    private List<Category> categories = new ArrayList<>();
    private String format;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public List<? extends Category> getCategories() {
        return categories;
    }

    public String getTitle() {
        return title;
    }

    public String getISBN() {
        return ISBN;
    }

    @Override
    public BookImage getImage() {
        return image;
    }

    @Override
    public String getFormat() {
        return format;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<? extends Author> getAuthors() {
        return authors;
    }

    @Override
    public void addAuthor(Author author) {
        if (!authors.contains(author))
            authors.add(author);
    }

    @Override
    public void addCategories(List<? extends Category> categories) {
        this.categories.addAll(categories);
    }

    @Override
    public void setFormat(String format) {
        if (!Book.ALL_BOOK_FORMATS.contains(format))
            throw new IllegalArgumentException("Invalid book format given: " + format);
        this.format = format;
    }


    @Override
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getSubtitle() {
        return subtitle;
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

    public void setImage(BookImage image) {
        this.image = image;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void decrementQuantityBy(int num) {
        quantity -= num;
    }

    @Override
    public void incrementQuantityBy(int quantity) {
        this.quantity += quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return id == book.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(ISBN);
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public int compareTo(Book o) {
        return title.compareTo(o.getTitle());
    }

}
