package com.biblioteca.ui.controller;

import com.biblioteca.ui.Images;
import com.biblioteca.ui.model.ListItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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

    @FXML
    private ImageView availabilityImage;

    @FXML
    private Label availabilityText;

    private FXMLLoader fxmlLoader;

    public ListViewCell() {
        super();
    }

    // Created to allow method reference usage.
    public ListViewCell(ListView<T> source) { }

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
            itemNumberLabel.setText(String.valueOf(getIndex()+1));
            availabilityImage.setImage(item.isAvailable() ? Images.GREEN_CIRCLE : Images.RED_CIRCLE);
            availabilityText.setText(item.isAvailable() ? "Disponibile" : "Prestato");



        }
    }
}
