package com.biblioteca.core.facade;

import com.biblioteca.core.*;
import com.biblioteca.core.employee.Employee;
import com.biblioteca.datasource.DataSource;
import com.biblioteca.ui.Images;
import com.biblioteca.ui.model.BookImage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Library {

    private Employee loggedEmployee;

    private static Library instance;

    private static final DataSource ds = DataSource.getDefault();

    public static Library getInstance() {
        if (instance == null)
            instance = new Library();
        return instance;
    }

    /**
     * @param start    The start date of the Loan
     * @param end      The end date of the Loan
     * @param customer The associated customer
     * @param book     The lent book
     */
    public Loan newLoan(LocalDate start, LocalDate end, Customer customer, Book book) {
        var id = ds.readLoans().stream().map(Loan::getLoanId).max(Comparator.naturalOrder()).get() + 1;

        var loan = new Loan.Builder()
                .setId(id)
                .setLoanDate(start)
                .setExpectedReturnDate(end)
                .setCustomer(customer)
                .setStatus("not-returned")
                .setBook(book)
                .build();

        ds.save(loan);

        return loan;
    }

    public Author newAuthor(String name) {

        var id = DataSource.getDefault().readAuthors()
                .stream()
                .map(Author::getId)
                .max(Comparator.naturalOrder()).get() + 1;

        AuthorImpl author = new AuthorImpl(id, name);
        ds.save(author);
        return author;
    }

    public Customer newCustomer(String name, String lastName, String email, String fiscalCode, String phoneNumber) {

        int id = ds.readCustomers().stream().map(Customer::getId).max(Comparator.naturalOrder()).get() + 1;

        Customer customer = new Customer(id, name, lastName, email, fiscalCode, phoneNumber);
        ds.save(customer);

        return customer;
    }

    public void removeBook(Book book) {
        ds.delete(book);
    }

    public void removeLoan(Loan loan) {
        ds.delete(loan);
    }

    public void setLoggedEmployee(Employee loggedEmployee) {
        this.loggedEmployee = loggedEmployee;
    }

    /**
     * Invoked when the application will be closed.
     */
    public void applicationWillClose() {
        ds.saveAll();
    }

    /**
     * Constructs a new Employee instance with the given parameters.
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @return
     */
    public Employee newEmployee(String firstName, String lastName, String email, String password) {
        var lastNum = ds.getEmployees().stream()
                .map(Employee::getEmployeeNumber)
                .map(Integer::parseInt)
                .max(Comparator.naturalOrder())
                .get();

        Employee admin = new Employee.Builder()
                .setNumber(String.valueOf(lastNum + 1))
                .setPassword(password)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setEmployeeType("admin")
                .build();

        ds.save(admin);

        return admin;
    }
}
