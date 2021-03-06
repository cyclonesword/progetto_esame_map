package com.biblioteca.core;

/**
 * Base interface for representing book publishers. (for instance APress, Pearson etc.) <br>
 */
public interface Publisher extends Comparable<Publisher> {
    /**
     * The unique identifier of this publisher
     * @return The unique identifier for this publisher.
     */
    int getId();

    /**
     * The publisher's name
     * @return The publisher's name
     */
    String getName();
}
