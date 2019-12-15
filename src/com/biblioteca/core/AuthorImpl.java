package com.biblioteca.core;

import java.util.Objects;

public class AuthorImpl implements Author {

    private int id;
    private String name;

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
        if (!(o instanceof AuthorImpl)) return false;
        AuthorImpl author = (AuthorImpl) o;
        return id == author.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
