package com.biblioteca.core;

import java.util.Objects;

/**
 * The default implementation of the {@link Publisher } interface.
 * The Comparator uses the publisher's name for comparing.
 */
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
        if (!(o instanceof Publisher)) return false;
        Publisher publisher = (Publisher) o;
        return id == publisher.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Publisher o) {
        return Integer.compare(id, o.getId());
    }

    @Override
    public String toString() {
        return name;
    }
}
