package com.biblioteca.datasource;

import com.biblioteca.core.Author;
import com.biblioteca.core.Book;
import com.biblioteca.core.Category;
import com.biblioteca.core.Publisher;

import java.util.Collection;

public interface DataSource {

    Collection<? extends Category> readCategories();
    Collection<? extends Author> readAuthors();
    Collection<? extends Book> readBooks();
    Collection<? extends Publisher> readPublishers();

    /**
     * This is
     * @return The default reference-implementation of the DataSource interface.
     */
    static DataSource getDefault() {
        return CSVDataSource.getInstance();
    }

}
