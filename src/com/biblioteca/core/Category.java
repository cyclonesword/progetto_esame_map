package com.biblioteca.core;

/**
 * Base interface for representing book categories. (for instance sci-fi, horror etc.) <br>
 * Extends the <code>{@link Comparable}</code> interface to provide sorting capabilities, based on the id of the category.
 * The default strategy for sorting purpose is to use the category's id in ascending order.
 */
public interface Category extends Comparable<Category> {
    /**
     * The unique identifier for this category
     * @return The unique identifier for this category.
     */
    int getId();

    /**
     * The category's name
     * @return The category name
     */
    String getName();
}
