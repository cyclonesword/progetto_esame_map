package com.biblioteca.ui.model;

import javafx.scene.image.Image;

/**
 * A class for managing root filter nodes.
 */
public class RootFilterItem extends FilterItem {

    public RootFilterItem(String name, Image image) {
        super(name,image, null);
    }

    public RootFilterItem(String name, String imagePath) {
        super(name, imagePath, null);
    }

    @Override
    public boolean applyTo(ListItem item) {
        throw new UnsupportedOperationException();
    }
}
