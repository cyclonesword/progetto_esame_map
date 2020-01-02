package com.biblioteca.core.builder;

import com.biblioteca.core.*;
import com.biblioteca.ui.utils.BookImage;

import java.util.List;

/**
 * The BookBuilder's implementation for Standard Books. <br>See {@link BookBuilder } class for the javadoc.
 */
public class StandardBookBuilder implements BookBuilder {

    private int id;
    private String title;
    private String subTitle;
    private String description;
    private String isbn;
    private String format;
    private Publisher publisher;
    private BookImage image;
    private List<? extends Author> authors;
    private List<? extends Category> categories;
    private int quantity;
    private int year;


    public StandardBookBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public StandardBookBuilder setSubTitle(String subTitle) {
        this.subTitle = subTitle;
        return this;
    }

    public StandardBookBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public StandardBookBuilder setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public StandardBookBuilder setPublisher(Publisher publisher) {
        this.publisher = publisher;
        return this;
    }

    public StandardBookBuilder setImage(BookImage image) {
        this.image = image;
        return this;
    }

    public StandardBookBuilder setAuthors(List<? extends Author> authors) {
        this.authors = authors;
        return this;
    }

    public StandardBookBuilder setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public StandardBookBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public StandardBookBuilder setYear(int value) {
        this.year = value;
        return this;
    }

    public StandardBookBuilder setCategories(List<? extends Category> categories) {
        this.categories = categories;
        return this;
    }

    public StandardBookBuilder setFormat(String format) {
        this.format = format;
        return this;
    }


    public Book build() throws BuilderException {

        if(authors == null || categories == null || publisher == null) {
            throw new BuilderException("Authors, categories and publisher must not be null");
        }

        var book = new BookImpl();

        book.setTitle(title);
        book.setISBN(isbn);
        book.setDescription(description);
        book.setQuantity(quantity);
        book.setId(id);
        book.setSubtitle(subTitle);
        book.setPublisher(publisher);
        book.setImage(image);
        book.setYear(year);
        book.setFormat(format);
        book.addCategories(categories);
        authors.forEach(book::addAuthor);

        return book;
    }
}