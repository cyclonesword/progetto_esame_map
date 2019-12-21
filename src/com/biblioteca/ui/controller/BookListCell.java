package com.biblioteca.ui.controller;

import com.biblioteca.core.Book;
import com.biblioteca.ui.Images;
import com.biblioteca.ui.model.BookListItem;
import com.biblioteca.ui.model.ListItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

/**
 * A JavaFX required class to represent a Book in the ListView.
 * For more information see the official documentation:
 * <pre>https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/ListCell.html</pre>
 */
public class BookListCell extends ListCell<BookListItem> {

    @FXML
    private GridPane rootNode;

    @FXML
    private Label titleLabel;

    @FXML
    private Label subtitleLabel;

    @FXML
    private Label itemNumberLabel;

    @FXML
    private ImageView imageView;

    @FXML
    private ImageView availabilityImage;

    @FXML
    private Label availabilityText;

    private FXMLLoader fxmlLoader;

    public BookListCell() {
        super();
    }

    // Created to allow method reference usage.
    public BookListCell(ListView<BookListItem> source) { }

    @Override
    protected void updateItem(BookListItem item, boolean empty) {
        super.updateItem(item, empty);

        if(empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {

            if(fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/BookItemCell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            final Book book = item.getBook();

            setGraphic(rootNode);
            titleLabel.setText(item.getItemTitle());
            item.setPosition(getIndex()+1);
            subtitleLabel.setText(book.getSubtitle().equals("-") ? book.getDescription() : book.getSubtitle());
            imageView.setImage(item.getImage());
            itemNumberLabel.setText(String.valueOf(getIndex()+1));
            availabilityImage.setImage(item.isAvailable() ? Images.GREEN_CIRCLE : Images.RED_CIRCLE);
            availabilityText.setText(item.isAvailable() ? "Disponibile" : "Prestato");



        }
    }
}
