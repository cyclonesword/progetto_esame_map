package com.biblioteca.ui.model;

import javafx.scene.image.Image;

public interface ListItem {
    int getPosition();
    boolean isSelected();
    String getItemTitle();
    String getItemDescription();
    Image getImage();

    int getQuantity();

    boolean isAvailable();
}
