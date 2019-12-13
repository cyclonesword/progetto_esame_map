package com.biblioteca.ui.model;

import com.biblioteca.core.Author;
import com.biblioteca.core.Book;
import com.biblioteca.core.Publisher;
import javafx.scene.image.Image;

import java.util.List;

// Adapter pattern
public class BookItem implements Book, ListItem {

    private Book book;

    public BookItem(Book book) {
        this.book = book;
    }

    @Override
    public String getTitle() {
        return book.getTitle();
    }

    @Override
    public String getISBN() {
        return book.getISBN();
    }

    @Override
    public List<? extends Author> getAuthors() {
        return book.getAuthors();
    }

    @Override
    public void addAuthor(Author author) {

    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getSubtitle() {
        return null;
    }

    @Override
    public int getYear() {
        return 0;
    }

    @Override
    public Publisher getPublisher() {
        return null;
    }

    @Override
    public String getDescription() {
        return book.getDescription();
    }

    @Override
    public int getPosition() {
        return 0;
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public Image getImage() {
        return book.getImage();
    }

    @Override
    public int getQuantity() {
        return book.getQuantity();
    }

    @Override
    public boolean isAvailable() {
        return getQuantity() > 0;
    }

    @Override
    public String getItemTitle() {
        return getTitle();
    }

    @Override
    public String getItemDescription() {
        return getDescription();
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
