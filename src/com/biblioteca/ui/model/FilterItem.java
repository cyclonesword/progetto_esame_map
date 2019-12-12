package com.biblioteca.ui.model;

import com.biblioteca.core.Filter;
import javafx.scene.image.Image;

public class FilterItem {

    private final Filter filter;
    // private String text;
    private boolean isSelected = false;

    private boolean rootItem = false;

    private Image image;
    private String imagePath;

    public FilterItem(Filter filter) {
        this.filter = filter;
    }

    public FilterItem(Filter filter, Image image) {
        this.filter = filter;
        this.image = image;
        this.rootItem = true;
    }


    public FilterItem(Filter filter, String imagePath) {
        this.filter = filter;
        this.image = new Image(getClass().getResourceAsStream(imagePath));
        this.rootItem = true;
    }

    public Filter getFilter() {
        return filter;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getText() {
        return filter.getValue();
    }

    public Image getImage() {
        return image;
    }

    public void setAsRootItem(boolean value) {
        rootItem = value;
    }

    public boolean isRootItem() {
        return rootItem;
    }

    @Override
    public String toString() {
        return "Filter[selected="+isSelected+"]: "+ filter.getName() + " = " +filter.getValue();
    }
}
