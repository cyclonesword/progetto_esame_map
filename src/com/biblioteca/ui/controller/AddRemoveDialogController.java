package com.biblioteca.ui.controller;

import com.biblioteca.datasource.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class AddRemoveDialogController<T> implements DialogController {

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

    public List<T> getSelectedItems() {
        return data;
    }

    public void setInitialData(List<? extends T> initialDAta) {
        this.data = FXCollections.observableArrayList(initialDAta);
    }

    public void setAllData(List<? extends T> allData) {
        this.allData = FXCollections.observableArrayList(allData);
    }
}
