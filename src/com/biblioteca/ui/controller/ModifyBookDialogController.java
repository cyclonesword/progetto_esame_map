package com.biblioteca.ui.controller;

import com.biblioteca.core.Author;
import com.biblioteca.core.Book;
import com.biblioteca.core.Category;
import com.biblioteca.core.Publisher;
import com.biblioteca.datasource.DataSource;
import com.biblioteca.ui.Dialogs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModifyBookDialogController {

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

    private List<Author> authors = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    private DataSource ds = DataSource.getDefault();

    public void fillFields() {
        authors.addAll(book.getAuthors()); // Shallow copy
        categories.addAll(book.getCategories());

        titleTf.setText(book.getTitle());
        subtitleTf.setText(book.getSubtitle());
        descriptionTf.setText(book.getDescription());
        yearTf.setText(String.valueOf(book.getYear()));
        isbnTf.setText(book.getISBN());
        qntTf.setText(String.valueOf(book.getQuantity()));
        bookImg.setImage(book.getImage());

        ObservableList<Publisher> publishers = FXCollections.observableArrayList(ds.readPublishers());
        editoriCombobox.setItems(publishers);

        var formats = FXCollections.observableArrayList(ds.readFormats());
        formatoCombobox.setItems(formats);

        editoriCombobox.getSelectionModel().select(book.getPublisher());
        formatoCombobox.getSelectionModel().select(book.getFormat());

        fillCategoriesLabel();
        fillAuthorsLabel();
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


    public void applyData() {
        book.setTitle(titleTf.getText());
        book.setSubtitle(subtitleTf.getText());
        book.setDescription(descriptionTf.getText());
        book.setFormat(formatoCombobox.getSelectionModel().getSelectedItem());
        book.setImage(bookImg.getImage());
        book.setQuantity(Integer.parseInt(qntTf.getText()));
        book.setISBN(isbnTf.getText());
        book.setYear(Integer.parseInt(yearTf.getText()));
        book.setPublisher(editoriCombobox.getSelectionModel().getSelectedItem());

        book.getAuthors().clear();
        authors.forEach(book::addAuthor);

        book.getCategories().clear();
        book.addCategories(categories);
    }

    @FXML
    public void browseFileClicked(MouseEvent mouseEvent) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        var file = fileChooser.showOpenDialog(rootPane.getScene().getWindow());

        if (file != null) {
            Image image = new Image(new FileInputStream(file));
            bookImg.setImage(image);
        }
    }

    @FXML
    public void modifyCategoryClicked(MouseEvent mouseEvent) throws IOException {
        Dialogs.<AddRemoveDialogController<Category>>showDialog("Modify categories",
                "/fxml/AddRemoveData.fxml",
                rootPane.getScene().getWindow(),
                controller -> {
                    controller.setInitialData(categories);
                    controller.setAllData(ds.readCategories());
                    controller.init();
                },
                controller -> {
                    this.categories = controller.getSelectedItems();
                    fillCategoriesLabel();
                });
    }



    @FXML
    public void modifyAuthorsClicked(MouseEvent mouseEvent) throws IOException {
        Dialogs.<AddRemoveDialogController<Author>>showDialog("Modify authors",
                "/fxml/AddRemoveData.fxml",
                rootPane.getScene().getWindow(),
                controller -> {
                    controller.setInitialData(authors);
                    controller.setAllData(ds.readAuthors());
                    controller.init();
                },
                controller -> {
                    this.authors = controller.getSelectedItems();
                    fillAuthorsLabel();
                });
    }

    public void setBook(Book book) {
        this.book = book;
        fillFields();
    }
}
