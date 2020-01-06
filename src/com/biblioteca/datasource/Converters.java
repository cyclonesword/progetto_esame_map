package com.biblioteca.datasource;

import com.biblioteca.core.*;
import com.biblioteca.core.employee.Employee;

import java.util.stream.Collectors;

/**
 * Utility class to get converters for all entity classes.
 */
public class Converters {

    /**
     *
     * @return A lambda expression that converts Authors in a String in CSV format.
     */
    public static Converter<Author> getAuthorConverter() {
        return authors -> authors.stream()
                .map(author -> author.getId() + "," + author.getName())
                .collect(Collectors.joining("\n"));
    }

    /**
     *
     * @return A lambda expression that converts Categories in a String in CSV format.
     */
    public static Converter<Category> getCategoryConverter() {
        return categories -> categories.stream()
                .map(cat -> cat.getId() + "," + filter(cat.getName()))
                .collect(Collectors.joining("\n"));
    }

    /**
     *
     * @return A lambda expression that converts Publishers in a String in CSV format.
     */
    public static Converter<Publisher> getPublisherConverter() {
        return p -> p.stream()
                .map(pub -> pub.getId() + "," + pub.getName())
                .collect(Collectors.joining("\n"));
    }

    /**
     *
     * @return A lambda expression that converts Loan classes in a String in CSV format.
     */
    public static Converter<Loan> getLoanConverter() {
        return loans -> loans.stream()
                .map(loan -> String.format("%d,%s,%s,%d,%d,%s",
                        loan.getLoanId(),
                        loan.getLoanDate(),
                        loan.getExpectedReturnDate(),
                        loan.getCustomer().getId(),
                        loan.getBook().getId(),
                        loan.getStatus()))
                .collect(Collectors.joining("\n"));
    }

    /**
     *
     * @return A lambda expression that converts Customers in a String in CSV format.
     */
    public static Converter<Customer> getCustomerConverter() {
        return entities -> entities.stream()
                .map(customer -> String.format("%d,%s,%s,%s,%s,%s",
                        customer.getId(),
                        filter(customer.getFirstName()),
                        filter(customer.getLastName()),
                        customer.getEmail(),
                        customer.getFiscalCode(),
                        customer.getPhoneNumber()))
                .collect(Collectors.joining("\n"));
    }

    /**
     *
     * @return A lambda expression that converts Employees in a String in CSV format.
     */
    public static Converter<Employee> getEmployeeConverter() {

        return employees -> employees.stream()
                .map(employee -> String.format("%d,%s,%s,%s,%s",
                        employee.getEmployeeNumber(),
                        employee.getPassword(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getEmail()))
                .collect(Collectors.joining("\n"));
    }

    /**
     *
     * @return A converter for books. It converts books in CSV String format.
     */
    public static Converter<Book> getBookConverter() {
        return new BookConverter();
    }

    static String filter(String string) {
        return string.replace(",", "__").replace("\n", " ");
    }
}
