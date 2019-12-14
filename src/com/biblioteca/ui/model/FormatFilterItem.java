package com.biblioteca.ui.model;

import com.biblioteca.core.Category;

public class FormatFilterItem extends AbstractFilterItem {

    private String format;

    public FormatFilterItem(String format) {
       super(format);
        this.format = format;
    }

    @Override
    public boolean applyTo(ListItem item) {
        return format.equalsIgnoreCase(item.getFormat());
    }
}
