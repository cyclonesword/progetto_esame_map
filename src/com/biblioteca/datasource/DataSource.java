package com.biblioteca.datasource;

import com.biblioteca.core.*;
import com.biblioteca.core.employee.Employee;
import com.biblioteca.ui.model.BookImage;

import java.util.List;

/**
 * This interface encapsulate the business logic to retrive, update and save entities to the underlying Data source.
 * The data are stored using simple CSV files .
 * To obtain the singleton instance, you can use the <br><br>
 * <code><pre>
 * Datasource.getDefault()
 * </pre></code>
 * method of the <code>DataSource</code> interface.
 */
public interface DataSource {

    /**
     * Returns a list containing all the categories retrived from the underlying datasource
     */
    List<? extends Category> readCategories();
    /**
     * Returns a list containing all the authors retrived from the underlying datasource
     */
    List<? extends Author> readAuthors();
    /**
     * Returns a list containing all the books retrived from the underlying datasource
     */
    List<? extends Book> readBooks();
    /**
     * Returns a list containing all the publishers retrived from the underlying datasource
     */
    List<? extends Publisher> readPublishers();
    /**
     * Returns a list containing all the book formats retrived from the underlying datasource
     */
    List<String> readFormats();
    /**
     * Returns a list containing all the customers retrived from the underlying datasource
     */
    List<? extends Customer> readCustomers();
    /**
     * Returns a list containing all the loans retrived from the underlying datasource
     */
    List<? extends Loan> readLoans();

    /**
     * Returns the list of employees read from the datasource
     * @return The list of employees.
     */
    List<? extends Employee> getEmployees();

    /**
     * Delete the given book
     * @param book the book to be deleted from the datasource.
     */
    void delete(Book book);
    /**
     * Store the given customer
     * @param customer The customer to store in the datasource
     */
    void save(Customer customer);

    /**
     * Store the given loan
     * @param loan The Loan to store in the datasource
     */
    void save(Loan loan);

    /**
     * Store the given employee
     * @param emp The Employee to store in the datasource
     */
    void save(Employee emp);

    /**
     * Store the given book
     * @param book The Book to store in the datasource
     */
    void save(Book book);

    /**
     * Store the path of the image in the datasource. <br><br>
     * When the image is saved back to disk, it will be copied in the default directory for the images.
     * See {@link CSVDataSource} for more informations.
     * @param image The image to store in the datasource
     */
    void save(BookImage image);

    /**
     * Store the given author
     * @param a The Author to store in the datasource
     */
    void save(Author a);

    /**
     * Store all the data contained in memory to the underlying datasource (CSV files in the default implementation)
     */
    void saveAll();

    /**
     * @return The default reference-implementation of the DataSource interface.
     */
    static DataSource getDefault() {
        return CSVDataSource.getInstance();
    }

    void delete(Loan loan);
}
