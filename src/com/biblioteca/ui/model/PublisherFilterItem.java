package com.biblioteca.ui.model;

import com.biblioteca.core.Publisher;

public class PublisherFilterItem  extends AbstractFilterItem {

    private Publisher publisher;

    public PublisherFilterItem(Publisher publisher) {
        super(publisher.getName());
        this.publisher = publisher;
    }

    @Override
    public boolean applyTo(ListItem item) {
        return publisher == item.getPublisher(); // instance equality since the instances are all taken from the datasource.
    }
}
