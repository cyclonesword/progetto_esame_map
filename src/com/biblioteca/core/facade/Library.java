package com.biblioteca.core.facade;


import com.biblioteca.core.*;
import com.biblioteca.core.builder.EmployeeBuilder;
import com.biblioteca.core.employee.Employee;
import com.biblioteca.datasource.DataSource;
import com.biblioteca.ui.utils.Utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Comparator;

/**
 * This is a Facade class that encapsulate the business logic to perform various operations.<br><br>
 * For instance, creating a new Employee involves creating a new unique id, the creation of the new object and saving it to the database: <br><br>
 * <pre>
 * var id = ds.getEmployees().stream()
 * .map(Employee::getEmployeeNumber)
 * .map(Integer::parseInt)
 * .max(Comparator.naturalOrder())
 * .get();
 *
 * Employee admin = new Employee.Builder()
 * .setId(String.valueOf(id + 1))
 * .setPassword(password)
 * .setFirstName(firstName)
 * .setLastName(lastName)
 * .setEmail(email)
 * .setEmployeeType("admin")
 * .build();
 *
 * ds.save(admin);
 * </pre>
 * <p>
 * The code above can be simplified by calling the method<br><br>
 * <pre>
 * Library.getInstance().newEmployee(firstName, lastName, ...);
 * </pre>
 * This Facade class has been created to simplify all this operations.
 * See the relative methods for more information.
 */
public class Library {

    private Employee loggedEmployee;

    private static Library instance;
    private static final DataSource ds = DataSource.getInstance();

    /**
     * The Singleton instance of this Facade class.
     *
     * @return The Singleton instance of this Facade class.
     */
    public static Library getInstance() {
        if (instance == null)
            instance = new Library();
        return instance;
    }

    /**
     * Constructs a new Loan instance with the given parameters, assigning a new id and saving it to the database.
     *
     * @param start    The start date of the Loan
     * @param end      The end date of the Loan
     * @param customer The associated customer
     * @param book     The lent book
     * @return The newly created Loan
     */
    public Loan newLoan(LocalDate start, LocalDate end, Customer customer, Book book) {
        var id = ds.getLoans().stream().map(Loan::getLoanId).max(Comparator.naturalOrder()).get() + 1;

        var loan = new Loan.Builder()
                .setId(id)
                .setLoanDate(start)
                .setExpectedReturnDate(end)
                .setCustomer(customer)
                .setStatus(Loan.STATUS_NOT_RETURNED)
                .setBook(book)
                .build();

        ds.save(loan);

        book.decrementQuantityBy(1);
        customer.addLoan(loan);

        return loan;
    }

    /**
     * Constructs a new Author instance with the given parameters, assigning a new id and saving it to the database.
     *
     * @param name The author's name
     * @return the newly created Author
     */
    public Author newAuthor(String name) {

        var id = DataSource.getInstance().getAuthors()
                .stream()
                .map(Author::getId)
                .max(Comparator.naturalOrder()).get() + 1;

        Author author = new AuthorImpl(id, name);
        ds.save(author);
        return author;
    }

    /**
     * Constructs a new Customer instance with the given parameters, , assigning a new id and saving it to the database.
     *
     * @param name
     * @param lastName
     * @param email
     * @param fiscalCode
     * @param phoneNumber
     * @return The newly created Customer
     */
    public Customer newCustomer(String name, String lastName, String email, String fiscalCode, String phoneNumber) {

        int id = ds.getCustomers().stream().map(Customer::getId).max(Comparator.naturalOrder()).get() + 1;

        Customer customer = new Customer(id, name, lastName, email, fiscalCode, phoneNumber);
        ds.save(customer);

        return customer;
    }

    /**
     * Constructs a new Employee instance with the given parameters, assigning a new id and saving it to the database.
     *
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @return The newly created Employee
     */
    public Employee newEmployee(String firstName, String lastName, String email, String password) {

        var lastNum = ds.getEmployees().stream()
                .map(Employee::getEmployeeNumber)
                .max(Comparator.naturalOrder())
                .get();

        Employee admin = EmployeeBuilder.getDefault()
                .setId(lastNum + 1)
                .setPassword(Utils.sha1Digest(password))
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .build();

        ds.save(admin);

        return admin;
    }

    /**
     * Creates a new Publisher instance and save it to the database.
     *
     * @param name The publissher name
     * @return The newly created publisher
     */
    public Publisher newPublisher(String name) {

        var id = ds.getPublishers().stream()
                .map(Publisher::getId)
                .max(Comparator.naturalOrder())
                .get() + 1;

        var p = new PublisherImpl(id, name);
        ds.save(p);

        return p;
    }

    /**
     * Removes the given book.
     *
     * @param book The book to be removed
     */
    public void removeBook(Book book) {
        ds.delete(book);
    }

    /**
     * Removes the given loan.
     *
     * @param loan The loan to be removed
     */
    public void removeLoan(Loan loan) {

        if (loan.getStatus().equals(Loan.STATUS_NOT_RETURNED))
            loan.getBook().incrementQuantityBy(1);

        ds.delete(loan);

        try {
            Files.delete(Paths.get(ds.getApplicationFilesRootPath() + "pdf" + File.separator + loan.getLoanId() + ".pdf"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setLoggedEmployee(Employee loggedEmployee) {
        this.loggedEmployee = loggedEmployee;
    }

    /**
     * Invoked when the application will be closed. It saves all data to the underlying data store. (CSV files in its default implementation)
     */
    public void applicationWillClose() {
        ds.saveAll();
    }

    public Employee getLoggedEmployee() {
        return loggedEmployee;
    }
}
