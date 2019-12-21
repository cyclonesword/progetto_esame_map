package com.biblioteca.core;

/**
 * Base interface for the authors. Extends the <code>{@link Comparable}</code> interface to provide sorting capabilities, based on the name of the author.
 * The default strategy for sorting purpose is to use the author's name in ascending order.
 */
public interface Author extends Comparable<Author> {

    /**
     * @return The author id
     */
    int getId();

    /**
     * @return The name of the author
     */
    String getName();
}
