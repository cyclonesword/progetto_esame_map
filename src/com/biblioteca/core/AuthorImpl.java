package com.biblioteca.core;

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

}
