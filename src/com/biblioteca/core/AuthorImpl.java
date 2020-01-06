package com.biblioteca.core;

import java.util.Objects;

/**
 * The default implementation of the Author interface.<br>
 * The ordering defined here is by name ascending.
 */
public class AuthorImpl implements Author {

    // Nota per il prof. Fici: I metodi public standard come equals, hashCode e toString non vengono commentati in quanto la javadoc viene ereditata da Object.
    // Gli altri metodi public non commentati ereditano la javadoc della loro interfaccia.

    private int id;
    private String name;

    /**
     * Constructs a new AuthorImpl instance. <br>
     * The caller is responsible for providing a unique (not already existing) id.
     * @param id A unique identifier for the author
     * @param name The author's name
     */
    public AuthorImpl(int id, String name) {
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
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        Author author = (Author) o;
        return id == author.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Author o) {
        return name.compareTo(o.getName());
    }
}
