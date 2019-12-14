package com.biblioteca.ui.model;

import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;

import java.util.Objects;

public abstract class AbstractFilterItem {

    private boolean isSelected = false;
    private boolean rootItem;

    private String name;
    private Image image;

    private TreeItem<AbstractFilterItem> treeItem;

    public AbstractFilterItem(String name, Image image) {
        this.name = name;
        this.image = image;
        this.rootItem = true;
        treeItem = new TreeItem<>(this);
    }

    public AbstractFilterItem(String name, String imagePath) {
        this(name, new Image(AbstractFilterItem.class.getResourceAsStream(imagePath)));
    }

    public AbstractFilterItem(String name) {
        this.name = name;
        treeItem = new TreeItem<>(this);
    }

    public TreeItem<AbstractFilterItem> getTreeItem() {
        return treeItem;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getText() { return name; }

    public Image getImage() {
        return image;
    }

    public boolean isRootItem() {
        return rootItem;
    }

    public abstract boolean applyTo(ListItem item);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractFilterItem)) return false;
        AbstractFilterItem that = (AbstractFilterItem) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Filter: "+name;
    }
}
