package com.biblioteca.datasource;

import com.biblioteca.core.*;
import com.biblioteca.core.employee.Employee;

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
                .map(loan -> String.format("%d,%s,%s,%d,%d",
                        loan.getLoanId(),
                        loan.getLoanDate(),
                        loan.getExpectedReturnDate(),
                        loan.getCustomer().getId(),
                        loan.getBook().getId()))
                .collect(Collectors.joining("\n"));
    }

    // The other 3 converters are intentionally left in a non-functional way
    // to underline the different ways of doing the same thing (in this case, functional or non functional style)

    public static Converter<Customer> getCustomerConverter() {
        return new Converter.CustomerConverter();
    }

    public static Converter<Book> getBookConverter() {
        return new Converter.BookConverter();
    }

    public static Converter<Employee> getEmployeeConverter() {
        return new Converter.EmployeeConverter();
    }
}
