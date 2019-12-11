package com.biblioteca.ui.controller;

import com.biblioteca.core.BookAuthor;
import com.biblioteca.core.BookImpl;
import com.biblioteca.ui.Images;
import com.biblioteca.ui.model.BookItem;
import com.biblioteca.ui.model.ListItem;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    private Button prenotaButton;

    @FXML
    private ImageView availabilityImage;

    @FXML
    private Label availabilityText;

    @FXML
    private Label bookDetailTitle;

    @FXML
    private Label bookDetailDescription;

    @FXML
    private ImageView bookDetailImageView;

    @FXML
    private ListView<ListItem> listView;

    @FXML
    private TreeView<TreeItem<String>> filtersTreeView;

    private ObservableList<ListItem> items = FXCollections.observableArrayList();

    public void initialize() {

        var book1 = new BookImpl("IT", "1222132323", "Nell'estate del 1989, un gruppo di bambini vittime di bullismo si uniscono per distruggere un mostro mutevole, che si maschera da clown e preda dei bambini di Derry, la loro piccola città del Maine.",2);
        var book2 = new BookImpl("The Martian", "22311211", "Un astronauta viene bloccato su Marte dopo che la sua squadra lo ha ritenuto morto e deve fare affidamento sulla sua ingegnosità per trovare un modo per segnalare alla Terra di essere vivo.",0);
        book1.setImage(new Image(getClass().getResourceAsStream("/images/it.jpg")));
        book1.addAuthor(new BookAuthor(1, "Stephen King"));
        book2.setImage(new Image(getClass().getResourceAsStream("/images/martian.jpg")));
        book2.addAuthor(new BookAuthor(2, "Andy Weir"));

        items.add(new BookItem(book1));
        items.add(new BookItem(book2));
        listView.setItems(items);
        listView.setCellFactory(ListViewCell::new);
        listView.getSelectionModel()
                .selectedItemProperty()
                .addListener((observableValue, oldItem, newItem) -> changeItemDetail(newItem));
        listView.getSelectionModel().select(0);


    }

    @FXML
    public void handleExit(ActionEvent event) {
        Platform.exit();
    }

    private void changeItemDetail(ListItem item) {
        bookDetailTitle.setText(item.getItemTitle());
        bookDetailDescription.setText(item.getItemDescription());
        bookDetailImageView.setImage(item.getImage());
        availabilityImage.setImage(item.getQuantity() > 0 ? Images.GREEN_CIRCLE : Images.RED_CIRCLE);
        availabilityText.setText(item.isAvailable() ? "Disponibile" : "Prestato");
        prenotaButton.setDisable(!item.isAvailable());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialize();
    }
}
