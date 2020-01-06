package com.biblioteca.core;

import java.util.Objects;

/**
 * Default implementation of {@link Category} interface. The natural ordering defined here is by category name ascending.
 */
public class CategoryImpl implements Category {

    private String name;
    private int id;

    /**
     * Creates a new CategoryImpl instance for the given unique identifier (id) and a name.
     * @param id The unique identifier
     * @param name The category name
     */
    public CategoryImpl(int id, String name) {
        this.name = name;
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return id == category.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Category o) {
        return name.compareTo(o.getName());
    }
}
