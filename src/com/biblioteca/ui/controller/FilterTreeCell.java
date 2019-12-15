package com.biblioteca.ui.controller;

import com.biblioteca.ui.model.AbstractFilterItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class FilterTreeCell<T extends AbstractFilterItem> extends TreeCell<T> {

    private FXMLLoader fxmlLoader;

    @FXML
    private Label filterName;

    @FXML
    private CheckBox checkBox;

    @FXML
    private ImageView imageView;

    @FXML
    private GridPane rootNode;

    public FilterTreeCell(TreeView<T> source) { }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if(empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {

            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TreeItemCell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // checkBox.setSelected(false);
            }

            setGraphic(rootNode);

            filterName.setText(item.getText());
            if(item.isRootItem()) {
                imageView.setImage(item.getImage());
                checkBox.setVisible(false);
                checkBox.setManaged(false);
            } else {
                checkBox.setVisible(true);
                checkBox.setManaged(true);
                imageView.setImage(null);
                checkBox.setSelected(item.isSelected());
                checkBox.setOnMouseClicked(event -> {
                    item.setSelected(checkBox.isSelected());

                    if(checkBox.isSelected())
                        MainWindowController.selectedFilters.add(item);
                    else
                        MainWindowController.selectedFilters.remove(item);

                    MainWindowController.applyFilters();

                    System.out.println("Filter selected: "+item);
                });
            }

        }

    }

}
