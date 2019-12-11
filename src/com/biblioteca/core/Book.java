package com.biblioteca.core;

import javafx.scene.image.Image;

import java.util.List;

public interface Book {
    String getName();
    String getDescription();
    String getISBN();
    Image getImage();

    List<Author> getAuthors();
    void addAuthor(Author author);

    int getQuantity();
}
