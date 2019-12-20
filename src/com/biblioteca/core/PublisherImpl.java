package com.biblioteca.core;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PublisherImpl)) return false;
        PublisherImpl publisher = (PublisherImpl) o;
        return id == publisher.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Publisher o) {
        return name.compareTo(o.getName());
    }

    @Override
    public String toString() {
        return name;
    }
}
