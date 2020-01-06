package com.biblioteca.datasource;

import com.biblioteca.core.*;
import com.biblioteca.core.builder.BookBuilder;
import com.biblioteca.core.builder.EmployeeBuilder;
import com.biblioteca.core.employee.Employee;
import com.biblioteca.ui.start.ApplicationStart;
import com.biblioteca.ui.utils.BookImage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This is the default implementation of the {@link DataSource} interface,.
 * It is package-private because it is intended to be used internally.<br><br>
 * <p>
 * ******* <b>Singleton Design Pattern</b> used here *******
 */
// package-private
class CSVDataSource implements DataSource {

    private static final DataSource instance = new CSVDataSource();

    private final String categoriesCsv = "categories.csv";
    private final String authorsCsv = "authors.csv";
    private final String publishersCsv = "publishers.csv";
    private final String booksCsv = "books.csv";
    private final String customersCsv = "users.csv";
    private final String loansCsv = "loans.csv";
    private final String employeesCsv = "employees.csv";

    private final String basePath = System.getProperty("user.home") + File.separator + ApplicationStart.getInstance().getAppName() + File.separator;
    private final String basePathCsv = basePath + "csv" + File.separator;
    private final String basePathImgs = basePath + "images" + File.separator;

    private List<Publisher> publishers;
    private List<Author> authors;
    private List<Book> books;
    private List<? extends Category> categories;
    private List<Customer> customers;
    private String classathImagesFolder = "/images/";
    private List<Loan> loans;
    private List<Employee> employees;

    // ====  Nota per il prof. Fici ==== //
    // La javadoc per questa classe viene ereditata dalla sua interfaccia DataSource.
    // In questa classe si fa largo uso di lambda expressions, Method references e streams.
    // ====================================


    /**
     * This constructor creates a local copy of the internal database with mock objects. <br>
     * It also creates all the necessary directories and files in the Operating System default user home directory.
     * <pre>
     * The path is this:  {home directory of you computer}/Memento Legere/csv        ==> for CSV files
     * {home directory of you computer}/Memento Legere/images     ==> for images
     * </pre>
     */
    private CSVDataSource() {

        // Create directories for csv files and for images if not exists.
        new File(basePathCsv).mkdirs();
        new File(basePathImgs).mkdirs();

        var filesToCopy = List.of(categoriesCsv, authorsCsv, publishersCsv, booksCsv, customersCsv, loansCsv, employeesCsv);

        // ====  Nota per il prof. Fici ==== //
        // Nel suo computer verrà creata una cartella contenente
        // i file necessari al funzionamento dell'applicativo.
        // Verrà creata nel path indicato sopra, nella javadoc del costruttore
        // ====================================
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

    /**
     * @return The singleton instance of this class
     */
    static DataSource getInstance() {
        return instance;
    }

    @Override
    public List<? extends Category> getCategories() {
        if (categories == null) {
            categories = readDataFromCsv(categoriesCsv, CategoryImpl::new);
            Collections.sort(categories);
        }

        return categories;
    }

    @Override
    public List<? extends Author> getAuthors() {
        if (authors == null) {
            authors = readDataFromCsv(authorsCsv, AuthorImpl::new);
            Collections.sort(authors);
        }

        return authors;
    }

    @Override
    public List<? extends Publisher> getPublishers() {
        if (publishers == null)
            publishers = readDataFromCsv(publishersCsv, PublisherImpl::new);

        return publishers;
    }

    @Override
    public List<String> getFormats() {
        return Book.ALL_BOOK_FORMATS;
    }

    @Override
    public List<? extends Customer> getCustomers() {
        if (customers == null)
            customers = readDataFromCsv(customersCsv, line -> new Customer(Integer.parseInt(line[0]), line[1], line[2], line[3], line[4], line[5])); // Si poteva usare un Builder

        return customers;
    }

    @Override
    public List<? extends Loan> getLoans() {
        if (loans == null)
            loans = readDataFromCsv(loansCsv, line -> {

                Customer customer = getCustomers().stream()
                        .filter(c -> c.getId() == Integer.parseInt(line[3]))
                        .findFirst()
                        .orElse(null);
                Book book = getBooks().stream()
                        .filter(b -> b.getId() == Integer.parseInt(line[4]))
                        .findFirst()
                        .orElse(null);

                var loan = new Loan.Builder()
                        .setId(Integer.parseInt(line[0]))
                        .setLoanDate(LocalDate.parse(line[1]))
                        .setExpectedReturnDate(LocalDate.parse(line[2]))
                        .setCustomer(customer)
                        .setStatus(line[5])
                        .setBook(book)
                        .build();

                customer.addLoan(loan);

                return loan;

            });

        return loans;
    }

    @Override
    public List<? extends Employee> getEmployees() {
        if (employees == null) {
            employees = readDataFromCsv(employeesCsv, line ->
                    EmployeeBuilder.getDefault()
                            .setId(Integer.parseInt(line[0]))
                            .setPassword(line[1])
                            .setFirstName(replaceUnderscore(line[2]))
                            .setLastName(replaceUnderscore(line[3]))
                            .setEmail(line[4])
                            .build());
        }

        return employees;
    }

    @Override
    public String getApplicationFilesRootPath() {
        return basePath;
    }

    @Override
    public List<? extends Book> getBooks() {

        if (books == null) {
            books = readDataFromCsv(booksCsv, line -> { // Invoked on every line of the Csv file

                var publisher = getPublishers().stream()
                        .filter(p -> p.getId() == Integer.parseInt(line[6].trim()))
                        .findFirst()
                        .orElse(null);

                var authorIds = getIDs(line[4]);
                var categoryIds = getIDs(line[10]);

                var auths = getAuthors().stream()
                        .filter(author -> authorIds.contains(String.valueOf(author.getId())))
                        .collect(Collectors.toList());

                var cats = getCategories().stream()
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

                return BookBuilder.getDefault()
                        .setId(Integer.parseInt(line[0]))
                        .setTitle(replaceUnderscore(line[1]))
                        .setSubTitle(replaceUnderscore(line[2]))
                        .setDescription(replaceUnderscore(line[3]))
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

            Collections.sort(books);
        }

        return books;
    }

    @Override
    public void save(Customer customer) {
        if (customers == null)
            getCustomers();

        this.customers.add(customer);
    }

    @Override
    public void save(Loan loan) {
        if (loans == null)
            getLoans();

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
        Collections.sort(books);
    }

    @Override
    public void save(BookImage image) {
        try {
            writeDataToFile(image.getName(), basePathImgs, new FileInputStream(image.getFile()).readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Author a) {
        authors.add(a);
    }

    @Override
    public void saveAll() {
        // Saves the authors contained in the list back to the CSV file. The same thing occur for the other lines.

        writeCsvDataToFile(authorsCsv, Converters.getAuthorConverter().convert(getAuthors()));
        writeCsvDataToFile(categoriesCsv, Converters.getCategoryConverter().convert(getCategories()));
        writeCsvDataToFile(publishersCsv, Converters.getPublisherConverter().convert(getPublishers()));
        writeCsvDataToFile(customersCsv, Converters.getCustomerConverter().convert(getCustomers()));
        writeCsvDataToFile(employeesCsv, Converters.getEmployeeConverter().convert(getEmployees()));
        writeCsvDataToFile(loansCsv, Converters.getLoanConverter().convert(getLoans()));
        writeCsvDataToFile(booksCsv, Converters.getBookConverter().convert(getBooks()));
    }

    @Override
    public void delete(Loan loan) {
        loans.remove(loan);
    }

    @Override
    public void save(Publisher p) {
        publishers.add(p);
    }

    @Override
    public void delete(Book book) {
        books.remove(book);
    }

    /* ========= Private utility methods ====== */

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
        writeDataToFile(fileName, basePathCsv, data.getBytes(StandardCharsets.UTF_8));
    }

    private List<String[]> readCSVFile(String csvFileName) {
        String line;
        List<String[]> lines = new ArrayList<>();

        String path = basePathCsv + csvFileName;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))) {
            while ((line = br.readLine()) != null) {
                lines.add(line.split(","));
            }

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

    // Utility method to get all IDs splitted by the ; separator and removing the [] parenthesis.
    private List<String> getIDs(String s) {
        return List.of(s.replaceAll("[\\[\\] ]", "").split(";"));
    }

    private String replaceUnderscore(String string) {
        return string.replace("__", ",");
    }

}
