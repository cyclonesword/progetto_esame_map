package com.biblioteca.ui.controller;

import com.biblioteca.core.Author;
import com.biblioteca.core.Book;
import com.biblioteca.core.Category;
import com.biblioteca.core.Publisher;
import com.biblioteca.datasource.DataSource;
import com.biblioteca.ui.utils.Dialogs;

import com.biblioteca.ui.utils.BookImage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This controller class is responsible for managing the addition and the modification of the books. <br>
 * Refer to {@link DialogController} class for the common methods.
 */
public class ModifyBookDialogController implements DialogController<Book> {

    @FXML
    private VBox authorsContainer;

    @FXML
    private Label authorsLabel;

    @FXML
    private Label categoryLabel;

    @FXML
    private ComboBox<Publisher> editoriCombobox;

    @FXML
    private ImageView bookImg;

    @FXML
    private TextField titleTf;

    @FXML
    private TextField subtitleTf;

    @FXML
    private TextArea descriptionTf;

    @FXML
    private TextField yearTf;

    @FXML
    private TextField isbnTf;

    @FXML
    private TextField qntTf;

    @FXML
    private ComboBox<String> formatoCombobox;

    @FXML
    private GridPane rootPane;

    private Book book;
    private boolean modify;

    private List<Author> authors = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    private DataSource ds = DataSource.getInstance();
    private Dialog<ButtonType> dialog;

    private File selectedImage;
    
    /**
     * Modify the book with the given values
     * @return The modified book instance.
     */
    @Override
    public Book confirmAndGet() {

        book.setTitle(titleTf.getText());
        book.setSubtitle(subtitleTf.getText());
        book.setDescription(descriptionTf.getText());
        book.setFormat(formatoCombobox.getSelectionModel().getSelectedItem());
        try {
            if (selectedImage != null) {
                BookImage image = new BookImage(selectedImage);
                book.setImage(image);
                ds.save(image);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        book.setQuantity(Integer.parseInt(qntTf.getText()));
        book.setISBN(isbnTf.getText());
        book.setYear(Integer.parseInt(yearTf.getText()));
        book.setPublisher(editoriCombobox.getSelectionModel().getSelectedItem());

        book.getAuthors().clear();
        authors.forEach(book::addAuthor);

        book.getCategories().clear();
        book.addCategories(categories);

        return book;
    }

    /**
     * Opens a new Browse window dialog to choose a new image for the book.
     * @throws FileNotFoundException If some error occur in the file selection
     */
    @FXML
    public void browseFileClicked() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        var file = fileChooser.showOpenDialog(rootPane.getScene().getWindow());

        if (file != null) {
            Image image = new Image(new FileInputStream(file));
            bookImg.setImage(image);
            selectedImage = file;
        }
    }

    /**
     * Opens a new window dialog to add or remove new categories.
     * @throws IOException If some error occur with the loading of the FXML file
     */
    @FXML
    public void modifyCategoryClicked() throws IOException {
        Dialogs.<AddRemoveDialogController<Category>>showDialog("Modify categories",
                "/fxml/AddRemoveData.fxml",
                rootPane.getScene().getWindow(),
                controller -> {
                    controller.setInitialData(categories);
                    controller.setAllData(ds.getCategories());
                    controller.init();
                },
                controller -> {
                    this.categories = controller.confirmAndGet();
                    fillCategoriesLabel();
                });
    }


    /**
     * Opens a new window dialog to add or remove new authors.
     * @throws IOException If some error occur with the loading of the FXML file
     */
    @FXML
    public void modifyAuthorsClicked() throws IOException {
        Dialogs.<AddRemoveDialogController<Author>>showDialog("Modify authors",
                "/fxml/AddRemoveData.fxml",
                rootPane.getScene().getWindow(),
                controller -> {
                    controller.setInitialData(authors);
                    controller.setAllData(ds.getAuthors());
                    controller.init();
                },
                controller -> {
                    this.authors = controller.confirmAndGet();
                    fillAuthorsLabel();
                });
    }

    public void setBook(Book book, boolean modify) {
        this.book = book;
        this.modify = modify;
        fillFields();
    }

    @Override
    public boolean checkData() {
        String title = titleTf.getText();
        String subtitle = subtitleTf.getText();
        String isbn = isbnTf.getText();

        var test = title == null || subtitle == null || isbn == null
                || title.isEmpty() || subtitle.isEmpty() || isbn.isEmpty()
                || categories.size() == 0 || authors.size() == 0
                || editoriCombobox.getSelectionModel().getSelectedItem() == null
                || formatoCombobox.getSelectionModel().getSelectedItem() == null;

        if (test) {
            Dialogs.showAlertDialog("Si prega di completare tutti i campi", rootPane.getScene().getWindow());
            return false;
        }

        return isNumeric(yearTf.getText(), "Anno") && isNumeric(qntTf.getText(), "Quantità");
    }

    // Initialize all field with the already existent data
    private void fillFields() {

        ObservableList<Publisher> publishers = FXCollections.observableArrayList(ds.getPublishers());
        editoriCombobox.setItems(publishers);

        var formats = FXCollections.observableArrayList(ds.getFormats());
        formatoCombobox.setItems(formats);

        if (book != null) {
            authors.addAll(book.getAuthors()); // Shallow copy
            categories.addAll(book.getCategories());

            titleTf.setText(book.getTitle());
            subtitleTf.setText(book.getSubtitle());
            descriptionTf.setText(book.getDescription());
            yearTf.setText(String.valueOf(book.getYear() > 0 ? book.getYear() : 2020));
            isbnTf.setText(book.getISBN());
            qntTf.setText(String.valueOf(book.getQuantity() > 0 ? book.getQuantity() : 1));
            bookImg.setImage(book.getImage());

            editoriCombobox.getSelectionModel().select(book.getPublisher());
            formatoCombobox.getSelectionModel().select(book.getFormat());

            fillCategoriesLabel();
            fillAuthorsLabel();
        }

    }

    private void fillAuthorsLabel() {
        var authorsString = authors.stream()
                .map(Author::getName)
                .collect(Collectors.joining(" - "));

        // 35 pixel in height for every author
        authorsContainer.setMinHeight(Math.max((authors.size() / 2) * 35, 60));
        authorsLabel.setText(authorsString);
    }

    private void fillCategoriesLabel() {
        var categoriesString = categories.stream()
                .map(Category::getName)
                .collect(Collectors.joining(" - "));

        categoryLabel.setText(categoriesString);
    }

    private boolean isNumeric(String num, String field) {
        try {
            Integer.parseInt(num);
        } catch (NumberFormatException e) {
            Dialogs.showAlertDialog("Si prega di inserire una quantità numerica nella casella '"+field+"'", rootPane.getScene().getWindow());
            return false;
        }
        return true;
    }
}
