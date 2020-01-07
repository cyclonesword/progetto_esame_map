package com.biblioteca.core;

/**
 * Base interface for representing book publishers. (for instance APress, Pearson etc.) <br>
 */
public interface Publisher extends Comparable<Publisher> {
    /**
     *
     * @return The unique identifier for this publisher.
     */
    int getId();

    /**
     *
     * @return The publisher's name
     */
    String getName();
}
