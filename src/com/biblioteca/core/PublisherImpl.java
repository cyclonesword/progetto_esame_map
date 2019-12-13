package com.biblioteca.core;

public class PublisherImpl implements Publisher {

    private int id;
    private String name;

    public PublisherImpl(int id, String name) {
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
