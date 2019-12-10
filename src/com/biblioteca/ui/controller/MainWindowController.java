package com.biblioteca.ui.controller;

import com.biblioteca.core.Book;
import com.biblioteca.core.BookImpl;
import com.biblioteca.ui.model.BookItem;
import com.biblioteca.ui.model.ListItem;
import com.biblioteca.ui.model.ListViewCell;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    private ListView<ListItem> listView;

    @FXML
    private TreeView<TreeItem<String>> filtersTreeView;

    private ObservableList<ListItem> items = FXCollections.observableArrayList();

    public void initialize() {

        var book1 = new BookImpl("IT - Il pagliaccio assassino", "1222132323", "Breve descrizione terrificante...");
        book1.setImage(new Image(getClass().getResourceAsStream("/images/it.jpg")));
        items.add(new BookItem(book1));
        listView.setItems(items);
        listView.setCellFactory(listView -> new ListViewCell<>());
        listView.refresh();

    }

    @FXML
    public void handleExit(ActionEvent event) {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialize();
    }
}
