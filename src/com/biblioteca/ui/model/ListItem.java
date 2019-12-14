package com.biblioteca.ui.model;

import com.biblioteca.core.Author;
import com.biblioteca.core.Category;
import com.biblioteca.core.Publisher;
import javafx.scene.image.Image;

import java.util.List;

public interface ListItem  {
    int getPosition();
    boolean isSelected();
    String getItemTitle();
    String getItemDescription();
    Image getImage();

    int getQuantity();

    boolean isAvailable();

    List<? extends Author> getAuthors();

    void setPosition(int index);

    Publisher getPublisher();

    List<? extends Category> getCategories();

    String getFormat();
}
