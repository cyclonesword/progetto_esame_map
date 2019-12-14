package com.biblioteca.ui.model;

import javafx.scene.image.Image;

public class RootFilterItem extends AbstractFilterItem {

    public RootFilterItem(String name, Image image) {
        super(name, image);
    }

    public RootFilterItem(String name, String imagePath) {
        super(name, imagePath);
    }

    @Override
    public boolean applyTo(ListItem item) {
        throw new UnsupportedOperationException();
    }
}
