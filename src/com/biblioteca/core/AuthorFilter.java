package com.biblioteca.core;

import com.biblioteca.ui.model.Filter;
import com.biblioteca.ui.model.ListItem;

public class AuthorFilter implements Filter {

    private String value;

    public AuthorFilter(String value) {
        this.value = value;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean apply(ListItem item) {
        return false;
    }

}
