package com.biblioteca.core;

/**
 * Default reference implementation of the {@link Author} interface.<br>
 * The sorting strategy uses the author's name.
 */
public class BookAuthor implements Author {

    private int id;
    private String name;

    /**
     * Constructs a new BookAuthor instance
     * @param id A unique identifier for this Author.
     * @param name The author's name.
     */
    public BookAuthor(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Author o) {
        return name.compareTo(o.getName());
    }
}
