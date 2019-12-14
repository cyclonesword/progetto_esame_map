package com.biblioteca.datasource;

import com.biblioteca.core.*;
import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private String publishersCsv = "/csv/publishers.csv";
    private String booksCsv = "/csv/books.csv";

    private Collection<? extends Publisher> publishers;
    private Collection<? extends Author> authors;
    private Collection<? extends Book> books;
    private Collection<? extends Category> categories;
    private String imagesPath = "/images/";
    private Collection<String> formats = List.of("Paper Book", "ePub", "PDF", "Audiobook");

    static DataSource getInstance() {
        return instance;
    }

    @Override
    public Collection<? extends Category> readCategories() {
        if(categories == null)
            categories = readDataFromCsv(categoriesCsv, CategoryImpl::new);

        return categories;
    }

    @Override
    public Collection<? extends Author> readAuthors() {
        if (authors == null)
            authors = readDataFromCsv(authorsCsv, AuthorImpl::new);

        return authors;
    }

    @Override
    public Collection<? extends Publisher> readPublishers() {
        if (publishers == null)
            publishers = readDataFromCsv(publishersCsv, PublisherImpl::new);

        return publishers;
    }

    @Override
    public Collection<String> readFormats() {
        return formats;
    }

    @Override
    public Collection<? extends Book> readBooks() {

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

    private <T> Collection<T> readDataFromCsv(String csvFilePath, BiFunction<Integer, String, T> action) { // Bi function for two input paramenters
        return readCSVFile(csvFilePath)
                .stream()
                .map(line -> action.apply(Integer.valueOf(line[0]), line[1])) // Using lambda expression
                .collect(Collectors.toList());
    }

    private <T> Collection<T> readDataFromCsv(String csvFilePath, Function<String[], T> action) { // Function with String array for n parameters.
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