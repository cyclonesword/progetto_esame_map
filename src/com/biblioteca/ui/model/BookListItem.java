package com.biblioteca.ui.model;

import com.biblioteca.core.Author;
import com.biblioteca.core.Book;
import com.biblioteca.core.Category;
import com.biblioteca.core.Publisher;
import javafx.scene.image.Image;

import java.util.List;

// Adapter pattern
public class BookListItem implements ListItem {

    private Book book;
    private int position;
    private boolean selected;

    public BookListItem(Book book) {
        this.book = book;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public String getItemTitle() {
        return book.getTitle();
    }

    @Override
    public String getItemDescription() {
        return book.getDescription();
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
    public List<? extends Author> getAuthors() {
        return book.getAuthors();
    }

    public Book getBook() {
        return book;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public Publisher getPublisher() {
        return book.getPublisher();
    }

    @Override
    public List<? extends Category> getCategories() {
        return book.getCategories();
    }

    @Override
    public String getFormat() {
        return book.getFormat();
    }
}
