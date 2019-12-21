package com.biblioteca.core;

/**
 * Base interface for representing book categories. (for instance sci-fi, horror etc.) <br>
 * It extends the {@link Comparable} interface providing an ordering based on the category name.
 */
public interface Category extends Comparable<Category> {
    /**
     *
     * @return The unique identifier for this category.
     */
    int getId();

    /**
     *
     * @return The category name
     */
    String getName();
}
