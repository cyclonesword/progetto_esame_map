package com.biblioteca.datasource;

import com.biblioteca.core.*;
import com.biblioteca.core.employee.Employee;
import com.biblioteca.core.employee.EmployeeFactory;
import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This is the reference implementation of the DataSource interface. It uses simple CSV files to read and store the data.
 * It is package-private because it is not intended to be used directly by the clients. <br />
 * To obtain the singleton instance, you can use the
 * <code>
 * Datasource.getDefault()
 * </code>
 * method of the <code>DataSource</code> interface.
 */
class CSVDataSource implements DataSource {

    private static DataSource instance = new CSVDataSource();

    private final String categoriesCsv = "/csv/categories.csv";
    private final String authorsCsv = "/csv/authors.csv";
    private final String publishersCsv = "/csv/publishers.csv";
    private final String booksCsv = "/csv/books.csv";
    private final String usersCsv = "/csv/users.csv";
    private final String loansCsv = "/csv/loans.csv";
    private final String employeesCsv = "/csv/employees.csv";

    private List<? extends Publisher> publishers;
    private List<? extends Author> authors;
    private List<? extends Book> books;
    private List<? extends Category> categories;
    private List<Customer> customers;
    private String imagesPath = "/images/";
    private List<String> formats = List.of("Paper Book", "ePub", "PDF", "Audiobook");
    private List<Loan> loans;
    private List<Employee> employees;

    static DataSource getInstance() {
        return instance;
    }

    @Override
    public List<? extends Category> readCategories() {
        if (categories == null)
            categories = readDataFromCsv(categoriesCsv, CategoryImpl::new);

        return categories;
    }

    @Override
    public List<? extends Author> readAuthors() {
        if (authors == null)
            authors = readDataFromCsv(authorsCsv, AuthorImpl::new);

        return authors;
    }

    @Override
    public List<? extends Publisher> readPublishers() {
        if (publishers == null)
            publishers = readDataFromCsv(publishersCsv, PublisherImpl::new);

        return publishers;
    }

    @Override
    public List<String> readFormats() {
        return formats;
    }

    @Override
    public List<? extends Customer> readUsers() {
        if (customers == null)
            customers = readDataFromCsv(usersCsv, line -> new Customer(Integer.parseInt(line[0]), line[1], line[2], line[3], line[4], line[5], false)); // Si poteva usare un Builder

        return customers;
    }

    @Override
    public List<? extends Loan> readLoans() {
        if(loans == null)
            loans = readDataFromCsv(loansCsv, line -> {
                Customer customer = readUsers().stream()
                        .filter(c -> c.getId() == Integer.parseInt(line[3]))
                        .findFirst()
                        .orElse(null);
                Book book = readBooks().stream()
                        .filter(b -> b.getId() == Integer.parseInt(line[4]))
                        .findFirst()
                        .orElse(null);;
                return new Loan.Builder()
                       .setId(Integer.parseInt(line[0]))
                       .setLoanDate(LocalDate.parse(line[1]))
                       .setExpectedReturnDate(LocalDate.parse(line[2]))
                       .setCustomer(customer)
                       .setBook(book)
                       .build();

            });

        return  loans;
    }

    @Override
    public void delete(Book book) {
        books.remove(book);
    }

    @Override
    public void modify(Book book) {

    }

    @Override
    public void save(Customer user) {
        if (customers == null)
            readUsers();
        this.customers.add(user);
    }

    @Override
    public void save(Loan loan) {
        if(loans == null)
            readLoans();
        this.loans.add(loan);
    }

    @Override
    public List<? extends Employee> getEmployees() {
        if(employees == null) {
            var factory = new EmployeeFactory();
            employees = readDataFromCsv(employeesCsv, line -> {
                factory.setEmployeeNumber(line[0]);
                factory.setPassword(line[1]);
                factory.setName(line[2]);
                factory.setLastName(line[3]);
                factory.setEmail(line[4]);
                String level = line[5];

                return factory.getEmployee(level);
            });
        }

        return employees;
    }

    @Override
    public void save(Employee emp) {
        this.employees.add(emp);
    }

    @Override
    public List<? extends Book> readBooks() {

        if (books == null) {
            books = readDataFromCsv(booksCsv, line -> { // Invoked on every line of the Csv file

                var publisher = readPublishers().stream()
                        .filter(p -> p.getId() == Integer.parseInt(line[6].trim()))
                        .findFirst()
                        .orElse(null);

                var authorIds = getIDs(line[4]);
                var categoryIds = getIDs(line[10]);

                var auths = readAuthors().stream()
                        .filter(author -> authorIds.contains(String.valueOf(author.getId())))
                        .collect(Collectors.toList());

                var cats = readCategories().stream()
                        .filter(category -> categoryIds.contains(String.valueOf(category.getId())))
                        .collect(Collectors.toList());

                return new Book.Builder()
                        .setId(Integer.parseInt(line[0]))
                        .setTitle(line[1])
                        .setSubTitle(line[2])
                        .setDescription(line[3])
                        .setAuthors(auths)
                        .setYear(Integer.parseInt(line[5].trim()))
                        .setPublisher(publisher)
                        .setIsbn(line[7])
                        .setQuantity(Integer.parseInt(line[8].trim()))
                        .setImage(new Image(getClass().getResourceAsStream(imagesPath + line[9])))
                        .setCategories(cats)
                        .setFormat(line[11])
                        .build();
            });
        }

        return books;
    }

    private List<String> getIDs(String s) {
        return List.of(s.replaceAll("[\\[\\] ]", "").split(";"));
    }


    private List<String[]> readCSVFile(String csvFilePath) {
        String line;
        List<String[]> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(csvFilePath)))) {

            while ((line = br.readLine()) != null)
                lines.add(line.split(","));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    private <T> List<T> readDataFromCsv(String csvFilePath, BiFunction<Integer, String, T> action) { // Bi function for two input paramenters
        return readCSVFile(csvFilePath)
                .stream()
                .map(line -> action.apply(Integer.valueOf(line[0]), line[1])) // Using lambda expression
                .collect(Collectors.toList());
    }

    private <T> List<T> readDataFromCsv(String csvFilePath, Function<String[], T> action) { // Function with String array for n parameters.
        return readCSVFile(csvFilePath)
                .stream()
                .map(action) // Using qualifiers
                .collect(Collectors.toList());
    }

//    private <T> Collection<T> readCSVFile(String csvFilePath, BiFunction<Integer, String, T> action) {
//
//        String line;
//        List<T> elements = new ArrayList<>();
//
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(csvFilePath)))) {
//
//            while((line = br.readLine()) != null) {
//                String[] splittedLine = line.split(",");
//                elements.add(action.apply(Integer.valueOf(splittedLine[0]), splittedLine[1]));
//            }
//
//            return elements;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return Collections.emptyList(); // Return empty collection to avoid returning null or throwing exceptions.
//    }
}
