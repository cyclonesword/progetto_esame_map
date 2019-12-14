package com.biblioteca.ui.model;

import com.biblioteca.core.Category;

public class CategoryFilterItem extends AbstractFilterItem {

    private Category category;

    public CategoryFilterItem(Category category) {
       super(category.getName());
        this.category = category;
    }

    @Override
    public boolean applyTo(ListItem item) {
        return item.getCategories().contains(category);
    }
}
