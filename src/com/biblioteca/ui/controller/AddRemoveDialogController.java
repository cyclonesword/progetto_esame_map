package com.biblioteca.ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.util.List;

/**
 *  Controller class used to add or remove authors or categories when the Add/Modify book dialog is displayed.
 *  The relative view file is AddRemoveData.fxml
 * @param <T>
 */
public class AddRemoveDialogController<T extends Comparable<T>> implements DialogController<List<T>> {

    public ComboBox<T> dataCombobox;
    public ListView<T> listView;

   // private DataSource ds = DataSource.getDefault();

    private ObservableList<T> data;
    private ObservableList<T> allData;

    public void init() {
        dataCombobox.setItems(allData);
        dataCombobox.getSelectionModel().selectFirst();
        listView.setItems(data);
    }

    public void addButton(MouseEvent mouseEvent) {
        final T selectedItem = dataCombobox.getSelectionModel().getSelectedItem();

        if(!data.contains(selectedItem))
            data.add(selectedItem);

        listView.refresh();
    }

    public void removeButton(MouseEvent mouseEvent) {
        data.remove(listView.getSelectionModel().getSelectedItem());
        listView.refresh();
    }

    public List<T> confirmAndGet() {
        return data;
    }

    public void setInitialData(List<? extends T> initialDAta) {
        this.data = FXCollections.observableArrayList(initialDAta);
    }

    public void setAllData(List<? extends T> allData) {
        this.allData = FXCollections.observableArrayList(allData);
    }
}
