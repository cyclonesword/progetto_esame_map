package com.biblioteca.ui.controller;

import com.biblioteca.core.*;
import com.biblioteca.datasource.DataSource;
import com.biblioteca.ui.Images;
import com.biblioteca.ui.model.BookItem;
import com.biblioteca.ui.model.FilterItem;
import com.biblioteca.ui.model.ListItem;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainWindowController implements Initializable {

    @FXML
    private TextField searchBar;

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
    private TreeView<FilterItem> filtersTreeView;

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
        listView.setCellFactory(BookListCell::new);

        listView.getSelectionModel()
                .selectedItemProperty()
                .addListener((observableValue, oldItem, newItem) -> changeItemDetail(newItem));

        listView.getSelectionModel().select(0);

        filtersTreeView.setCellFactory(FilterTreeCell::new);

        initFilters();
    }

    private void initFilters() {

        var rootItem = new FilterItem(new RootFilter("Filtri"), "/images/filter.png");
        var rootNode = new TreeItem<>(rootItem);

        var rootAuthors = new FilterItem(new RootFilter("Autori"), "/images/authors.png");
        var rootAuthorsNode = new TreeItem<>(rootAuthors);

        var rootPublishers = new FilterItem(new RootFilter("Editori"), "/images/publisher.png");
        var rootPublishersNode = new TreeItem<>(rootPublishers);

        rootNode.getChildren().addAll(List.of(rootAuthorsNode, rootPublishersNode));
        rootNode.setExpanded(true);

        var af1 = new AuthorFilter("Stephen King");
        var af2 = new AuthorFilter("Andy Weir");
        var af3 = new AuthorFilter("Isaac Asimov");

        var pf1 = new PublisherFilter("ACM Press");
        var pf2 = new PublisherFilter("Pearson");
        var pf3 = new PublisherFilter("McGraw-Hill");

        var authorFilters = List.of(af1,af2,af3).stream().map(FilterItem::new).collect(Collectors.toList());
        var publisherFilters = List.of(pf1,pf2,pf3).stream().map(FilterItem::new).collect(Collectors.toList());

        rootAuthorsNode.getChildren().addAll(authorFilters.stream().map(TreeItem::new).collect(Collectors.toList()));
        rootPublishersNode.getChildren().addAll(publisherFilters.stream().map(TreeItem::new).collect(Collectors.toList()));

        filtersTreeView.setRoot(rootNode);

        var pubs = DataSource.getDefault().readPublishers();
        var auths = DataSource.getDefault().readAuthors();
        var cats = DataSource.getDefault().readCategories();
        var books = DataSource.getDefault().readBooks();
        System.out.println("");
        // DataSource.getDefault().readCategories();
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

    @FXML
    public void searchBarKeyTyped(KeyEvent keyEvent) {
        // System.out.println(keyEvent);
        if(keyEvent.getCode() == KeyCode.ENTER) {
            System.out.println("Premuto invio!");
        }

    }
}
