package com.biblioteca.ui.items;

import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * This class is used to model filters displayed in the TreeView at the left section of the Application.<br>
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

    public FilterItem(String name, Predicate<ListItem> filterStrategy) {
        this.name = name;
        this.filterStrategy = filterStrategy;
        treeItem = new TreeItem<>(this);
    }

    public FilterItem(String name, String imagePath) {
        this(name, new Image(FilterItem.class.getResourceAsStream(imagePath)), null);
    }

    /**
     * Returns the filter item wrapped in a TreeItem class, necessary to add it in the TreeView.
     */
    public TreeItem<FilterItem> getTreeItem() {
        return treeItem;
    }

    /**
     * Returns true if the filter is selected, false otherwise.
     */
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    /**
     * Get the name of the filter
     */
    public String getText() {
        return name;
    }

    /**
     * Get the image (if any) of the filter.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Returns true if this filter is at the root of the TreeView
     */
    public boolean isRootItem() {
        return rootItem;
    }

    /**
     * Returns true if this item satisfy the filtering strategy, false otherwise
     * @param item The ListItem to apply the filter strategy
     * @return
     */
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
