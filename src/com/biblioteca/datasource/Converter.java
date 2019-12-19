package com.biblioteca.datasource;

import com.biblioteca.core.*;
import com.biblioteca.core.employee.Employee;

import java.util.List;
import java.util.stream.Collectors;

@FunctionalInterface
public interface Converter<T> {

    String convert(List<? extends T> entities);

    // These 5 converters has not been translated in the equivalent functional style to show different ways of doing the same thing.

    class EmployeeConverter implements Converter<Employee> {
        @Override
        public String convert(List<? extends Employee> entities) {
            return entities.stream()
                    .map(employee -> String.format("%s,%s,%s,%s,%s",
                            employee.getEmployeeNumber(),
                            employee.getPassword(),
                            employee.getFirstName(),
                            employee.getLastName(),
                            employee.getEmail()))
                    .collect(Collectors.joining("\n"));
        }
    }

    class CustomerConverter implements Converter<Customer> {
        @Override
        public String convert(List<? extends Customer> entities) {
            return entities.stream()
                    .map(customer -> String.format("%d,%s,%s,%s,%s,%s",
                            customer.getId(),
                            customer.getFirstName(),
                            customer.getLastName(),
                            customer.getEmail(),
                            customer.getFiscalCode(),
                            customer.getPhoneNumber()))
                    .collect(Collectors.joining("\n"));
        }
    }

    class BookConverter implements Converter<Book> {
        @Override
        public String convert(List<? extends Book> entities) {
            return entities.stream()
                    .map(book -> {
                        StringBuilder sb = new StringBuilder();

                        var authors = book.getAuthors().stream()
                                .map(Author::getId)
                                .map(String::valueOf)
                                .collect(Collectors.joining(";"));

                        var categories = book.getCategories().stream()
                                .map(Category::getId)
                                .map(String::valueOf)
                                .collect(Collectors.joining(";"));

                        sb.append(book.getId());
                        sb.append(",");
                        sb.append(book.getTitle());
                        sb.append(",");
                        sb.append(book.getSubtitle());
                        sb.append(",");
                        sb.append(book.getDescription());
                        sb.append(",");
                        sb.append("[" + authors + "]");
                        sb.append(",");
                        sb.append(book.getYear());
                        sb.append(",");
                        sb.append(book.getPublisher().getId());
                        sb.append(",");
                        sb.append(book.getISBN());
                        sb.append(",");
                        sb.append(book.getQuantity());
                        sb.append(",");
                        sb.append(book.getImage().getLocation() + "=" + book.getImage().getName());
                        sb.append(",");
                        sb.append("[" + categories + "]");
                        sb.append(",");
                        sb.append(book.getFormat());

                        return sb.toString();
                    })
                    .collect(Collectors.joining("\n"));
        }
    }


//    public static String convert(Author author) {
//        return author.getId() + "," + author.getName();
//    }
//
//    public static String convert(Category category) {
//        return category.getId() + "," + category.getName();
//    }
//
//    public static String convert(Publisher publisher) {
//        return publisher.getId() + "," + publisher.getName();
//    }
//
//    public static String convert(Customer customer) {
//        return String.format("%d,%s,%s,%s,%s,%s",
//                customer.getId(),
//                customer.getFirstName(),
//                customer.getLastName(),
//                customer.getEmail(),
//                customer.getFiscalCode(),
//                customer.getPhoneNumber());
//    }
//
//    public static String convert(Employee employee) {
//        return String.format("%s,%s,%s,%s,%s",
//                employee.getEmployeeNumber(),
//                employee.getPassword(),
//                employee.getFirstName(),
//                employee.getLastName(),
//                employee.getEmail());
//    }
//
//    public static String convert(Loan loan) {
//        return String.format("%d,%s,%s,%d,%d",
//                loan.getLoanId(),
//                loan.getLoanDate(),
//                loan.getExpectedReturnDate(),
//                loan.getCustomer().getId(),
//                loan.getBook().getId());
//    }
//
//    public static String convert(Book book) {
//        StringBuilder sb = new StringBuilder();
//
//        DataSource ds = DataSource.getDefault();
//
//        var authors = ds.readAuthors().stream()
//                .map(Author::getId)
//                .map(String::valueOf)
//                .collect(Collectors.joining(";"));
//
//        var categories = ds.readCategories().stream()
//                .map(Category::getId)
//                .map(String::valueOf)
//                .collect(Collectors.joining(";"));
//
//        sb.append(book.getId());
//        sb.append(",");
//        sb.append(book.getTitle());
//        sb.append(",");
//        sb.append(book.getSubtitle());
//        sb.append(",");
//        sb.append(book.getDescription());
//        sb.append(",");
//        sb.append("[" + authors + "]");
//        sb.append(",");
//        sb.append(book.getYear());
//        sb.append(",");
//        sb.append(book.getPublisher().getId());
//        sb.append(",");
//        sb.append(book.getISBN());
//        sb.append(",");
//        sb.append(book.getQuantity());
//        sb.append(",");
//        sb.append("/images/default.png");
//        sb.append(",");
//        sb.append("[" + categories + "]");
//        sb.append(",");
//        sb.append(book.getFormat());
//
//        return sb.toString();
//    }

}
