package com.biblioteca.datasource;

import com.biblioteca.core.*;
import com.biblioteca.core.employee.Employee;
import com.biblioteca.ui.ApplicationStart;
import com.biblioteca.ui.model.BookImage;

import java.io.*;
import java.nio.file.*;
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

    private static final DataSource instance = new CSVDataSource();

    private final String categoriesCsv = "categories.csv";
    private final String authorsCsv = "authors.csv";
    private final String publishersCsv = "publishers.csv";
    private final String booksCsv = "books.csv";
    private final String customersCsv = "users.csv";
    private final String loansCsv = "loans.csv";
    private final String employeesCsv = "employees.csv";


    private final String basePath = System.getProperty("user.home") + File.separator + ApplicationStart.instance.getAppName() + File.separator;
    private final String basePathCsv = basePath + "csv" + File.separator;
    private final String basePathImgs = basePath + "images" + File.separator;

    private List<? extends Publisher> publishers;
    private List<? extends Author> authors;
    private List<Book> books;
    private List<? extends Category> categories;
    private List<Customer> customers;
    private String classathImagesFolder = "/images/";
    private List<String> formats = List.of("Paper Book", "ePub", "PDF", "Audiobook");
    private List<Loan> loans;
    private List<Employee> employees;

    private CSVDataSource() { // Creates a local copy of the internal database with mock entity.
        new File(basePathCsv).mkdirs(); // Create directory if not exists;

        var filesToCopy = List.of(categoriesCsv, authorsCsv, publishersCsv, booksCsv, customersCsv, loansCsv, employeesCsv);

        filesToCopy.forEach(csv -> {
            try {
                Path target = Paths.get(basePathCsv + csv);
                if (!Files.exists(target)) {
                    InputStream stream = getClass().getResourceAsStream("/csv/" + csv);
                    Files.copy(stream, target);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

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
    public List<? extends Customer> readCustomers() {
        if (customers == null)
            customers = readDataFromCsv(customersCsv, line -> new Customer(Integer.parseInt(line[0]), line[1], line[2], line[3], line[4], line[5], false)); // Si poteva usare un Builder

        return customers;
    }

    @Override
    public List<? extends Loan> readLoans() {
        if (loans == null)
            loans = readDataFromCsv(loansCsv, line -> {

                Customer customer = readCustomers().stream()
                        .filter(c -> c.getId() == Integer.parseInt(line[3]))
                        .findFirst()
                        .orElse(null);
                Book book = readBooks().stream()
                        .filter(b -> b.getId() == Integer.parseInt(line[4]))
                        .findFirst()
                        .orElse(null);

                return new Loan.Builder()
                        .setId(Integer.parseInt(line[0]))
                        .setLoanDate(LocalDate.parse(line[1]))
                        .setExpectedReturnDate(LocalDate.parse(line[2]))
                        .setCustomer(customer)
                        .setBook(book)
                        .build();

            });

        return loans;
    }

    @Override
    public void modify(Book book) { }

    @Override
    public List<? extends Employee> getEmployees() {
        if (employees == null) {
            employees = readDataFromCsv(employeesCsv, line ->
                    new Employee.Builder()
                            .setNumber(line[0])
                            .setPassword(line[1])
                            .setFirstName(line[2])
                            .setLastName(line[3])
                            .setEmail(line[4])
                            .build());
        }

        return employees;
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

                String[] imgSplit = line[9].split("=");
                InputStream is = null;

                if (imgSplit[0].equals("local"))
                    is = getClass().getResourceAsStream("/images/" + imgSplit[1]);
                else {
                    try {
                        is = new FileInputStream(basePathImgs + imgSplit[1]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

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
                        .setImage(new BookImage(is, imgSplit[0], imgSplit[1]))
                        .setCategories(cats)
                        .setFormat(line[11])
                        .build();
            });
        }

        return books;
    }

    @Override
    public void save(Customer user) {
        if (customers == null)
            readCustomers();
        int lastId = customers.stream().map(Customer::getId).max(Comparator.naturalOrder()).get();
        user.setId(lastId);
        this.customers.add(user);
    }

    @Override
    public void save(Loan loan) {
        if (loans == null)
            readLoans();

        int lastId = loans.stream().map(Loan::getLoanId).max(Comparator.naturalOrder()).get();
        loan.setId(lastId + 1);
        this.loans.add(loan);
    }

    @Override
    public void save(Employee emp) {
        this.employees.add(emp);
    }

    @Override
    public void save(Book book) {
        book.setId(books.stream().map(Book::getId).max(Comparator.naturalOrder()).get() + 1);
        books.add(book);
    }

    @Override
    public void saveImage(BookImage image) {
        try {
            writeDataToFile(image.getName(), basePathImgs, new FileInputStream(image.getFile()).readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveAll() {
        // Saves the authors contained in the list back to the CSV file. The same thing occur for the other lines.
        writeCsvDataToFile(authorsCsv, Converters.getAuthorConverter().convert(readAuthors()));
        writeCsvDataToFile(categoriesCsv, Converters.getCategoryConverter().convert(readCategories()));
        writeCsvDataToFile(publishersCsv, Converters.getPublisherConverter().convert(readPublishers()));
        writeCsvDataToFile(customersCsv, Converters.getCustomerConverter().convert(readCustomers()));
        writeCsvDataToFile(employeesCsv, Converters.getEmployeeConverter().convert(getEmployees()));
        writeCsvDataToFile(loansCsv, Converters.getLoanConverter().convert(readLoans()));
        writeCsvDataToFile(booksCsv, Converters.getBookConverter().convert(readBooks()));
    }

    @Override
    public void delete(Book book) {
        books.remove(book);
    }

    /* ========= Private utility methods ====== */

    private List<String[]> readCSVFile(String csvFileName) {
        String line;
        List<String[]> lines = new ArrayList<>();

        String path = basePathCsv + csvFileName;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {

            while ((line = br.readLine()) != null)
                lines.add(line.split(","));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
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

    private void writeDataToFile(String fileName, String path, byte[] data) {
        try (BufferedOutputStream bou = new BufferedOutputStream(new FileOutputStream(new File(path + fileName), false))) {
            bou.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeCsvDataToFile(String fileName, String data) {
        writeDataToFile(fileName, basePathCsv, data.getBytes());
    }

    // Utility method to get all IDs splitted by the ; separator and removing the [] parenthesis.
    private List<String> getIDs(String s) {
        return List.of(s.replaceAll("[\\[\\] ]", "").split(";"));
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
