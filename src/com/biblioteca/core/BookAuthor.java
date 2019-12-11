package com.biblioteca.core;

public class BookAuthor implements Author {

    private int id;
    private String name;

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
}
