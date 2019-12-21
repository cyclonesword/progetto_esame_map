package com.biblioteca.ui.model;

import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * This classs is used to model filters displayed in the TreeView at the left section of the Application.<br>
 * Every filter has an associated Predicate that encapsulate the filtering strategy.
 * This class is used in the MainWindowController to initialize the TreeView with all the filters divided in 4 sections:
 * Author,Category,Publisher and Format.
 */
public class FilterItem {

    private boolean isSelected = false;
    private boolean rootItem;

    private String name;
    private Image image;
    private Predicate<ListItem> filterStrategy;

    private TreeItem<FilterItem> treeItem;

    public FilterItem(String name, Image image, Predicate<ListItem> filterStrategy) {
        this.name = name;
        this.image = image;
        this.filterStrategy = filterStrategy;
        this.rootItem = true;
        treeItem = new TreeItem<>(this);
    }

    public FilterItem(String name, String imagePath, Predicate<ListItem> filterStrategy) {
        this(name, new Image(FilterItem.class.getResourceAsStream(imagePath)), filterStrategy);
    }

    public FilterItem(String name, Predicate<ListItem> filterStrategy) {
        this.name = name;
        this.filterStrategy = filterStrategy;
        treeItem = new TreeItem<>(this);
    }

    public TreeItem<FilterItem> getTreeItem() {
        return treeItem;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getText() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public boolean isRootItem() {
        return rootItem;
    }

    public boolean applyTo(ListItem item) {
        return filterStrategy.test(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilterItem)) return false;
        FilterItem that = (FilterItem) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Filter: " + name;
    }
}
