package com.biblioteca.ui.model;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class ListViewCell<T extends ListItem> extends ListCell<T> {

    @FXML
    private GridPane rootNode;

    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label itemNumberLabel;

    @FXML
    private ImageView imageView;

    private FXMLLoader fxmlLoader;


    @Override
    protected void updateItem(T item, boolean empty) {
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

            setGraphic(rootNode);
            titleLabel.setText(item.getItemTitle());
            descriptionLabel.setText(item.getItemDescription());
            imageView.setImage(item.getImage());
            itemNumberLabel.setText(String.valueOf(getIndex()));

        }
    }
}
