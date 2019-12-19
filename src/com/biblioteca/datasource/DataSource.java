package com.biblioteca.datasource;

import com.biblioteca.core.*;
import com.biblioteca.core.employee.Employee;
import com.biblioteca.ui.model.BookImage;

import java.util.List;

public interface DataSource {

    List<? extends Category> readCategories();
    List<? extends Author> readAuthors();
    List<? extends Book> readBooks();
    List<? extends Publisher> readPublishers();
    List<String> readFormats();
    List<? extends Customer> readCustomers();
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

    List<? extends Employee> getEmployees();

    void save(Employee emp);

    void save(Book book);

    void saveAll();

    void saveImage(BookImage image);
}
