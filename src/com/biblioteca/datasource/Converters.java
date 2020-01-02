package com.biblioteca.datasource;

import com.biblioteca.core.*;
import com.biblioteca.core.employee.Employee;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;

public class Converters {

    public static Converter<Author> getAuthorConverter() {
        return authors -> authors.stream()
                .map(author -> author.getId() + "," + author.getName())
                .collect(Collectors.joining("\n"));
    }

    public static Converter<Category> getCategoryConverter() {
        return categories -> categories.stream()
                .map(cat -> cat.getId() + "," + cat.getName())
                .collect(Collectors.joining("\n"));
    }

    public static Converter<Publisher> getPublisherConverter() {
        return p -> p.stream()
                .map(pub -> pub.getId() + "," + pub.getName())
                .collect(Collectors.joining("\n"));
    }

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

    public static Converter<Customer> getCustomerConverter() {
        return entities -> entities.stream()
                .map(customer -> String.format("%d,%s,%s,%s,%s,%s",
                        customer.getId(),
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getEmail(),
                        customer.getFiscalCode(),
                        customer.getPhoneNumber()))
                .collect(Collectors.joining("\n"));
    }

    public static Converter<Book> getBookConverter() {
        return new BookConverter();
    }

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
}
