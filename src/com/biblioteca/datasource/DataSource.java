package com.biblioteca.datasource;

import com.biblioteca.core.*;

import java.util.List;

public interface DataSource {

    List<? extends Category> readCategories();
    List<? extends Author> readAuthors();
    List<? extends Book> readBooks();
    List<? extends Publisher> readPublishers();
    List<String> readFormats();
    List<? extends Customer> readUsers();
    List<? extends Loan> readLoans();


    void delete(Book book);
    void modify(Book book);

    void save(Customer user);
    void save(Loan loan);

    /**
     * This is
     * @return The default reference-implementation of the DataSource interface.
     */
    static DataSource getDefault() {
        return CSVDataSource.getInstance();
    }
}
