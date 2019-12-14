package com.biblioteca.ui.controller;

import com.biblioteca.datasource.DataSource;
import com.biblioteca.ui.Images;
import com.biblioteca.ui.model.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.*;
import java.util.stream.Collectors;

public class MainWindowController {

    @FXML
    private Button searchButton;

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
    private ListView<BookListItem> listView;

    @FXML
    private TreeView<AbstractFilterItem> filtersTreeView;

    @FXML
    public void handleExit(ActionEvent event) {
        Platform.exit();
    }

    private static final ObservableList<BookListItem> allBooks = FXCollections.observableArrayList();
    private static final ObservableList<BookListItem> filteredItems = FXCollections.observableArrayList();
    private static final DataSource ds = DataSource.getDefault();

    public static final Set<AbstractFilterItem> selectedFilters = new HashSet<>();

    public static void notifyFiltersChanged() {
        filteredItems.clear();
        filteredItems.addAll(filterItems());
    }

    private static List<BookListItem> filterItems() {
        return allBooks.stream()
                .filter(book -> selectedFilters.stream()
                        .allMatch(filter -> filter.applyTo(book)))
                .collect(Collectors.toList());
    }


    // This method will be called automatically by the JavaFX runtime.
    public void initialize() {
        allBooks.addAll(ds.readBooks().stream()
                .map(BookListItem::new)
                .collect(Collectors.toList())
        );

        filteredItems.addAll(allBooks);

        listView.setItems(filteredItems);
        listView.setCellFactory(BookListCell::new);

        // When the user selects a book in the listview, update the book details section (right)
        listView.getSelectionModel()
                .selectedItemProperty()
                .addListener((observableValue, oldItem, newItem) -> changeItemDetail(newItem));

        listView.getSelectionModel().select(0);

        // Select the first book when the underlying dataset changes.
        listView.getItems().addListener((ListChangeListener<? super BookListItem>) change -> {
            listView.getSelectionModel().selectFirst();
        });

        initFilters();
    }

    private void changeItemDetail(ListItem item) {
        if (item == null)
            return;

        bookDetailTitle.setText(item.getItemTitle());
        bookDetailDescription.setText(item.getItemDescription());
        bookDetailImageView.setImage(item.getImage());
        availabilityImage.setImage(item.getQuantity() > 0 ? Images.GREEN_CIRCLE : Images.RED_CIRCLE);
        availabilityText.setText(item.isAvailable() ? "Disponibile" : "Prestato");
        prenotaButton.setDisable(!item.isAvailable());
    }

    private void initFilters() {
        filtersTreeView.setCellFactory(FilterTreeCell::new);

        var rootFilterNode = new RootFilterItem("Filtri", "/images/filter.png").getTreeItem();
        var rootCategoryFilter = new RootFilterItem("Categorie", "/images/category.png");
        var rootAuthorsFilter = new RootFilterItem("Autori", "/images/authors.png");
        var rootPublishersFilter = new RootFilterItem("Editori", "/images/publisher.png");
        var rootFormatsFilter = new RootFilterItem("Formato", "/images/format.png");
        // var rootTagFilter = new RootFilterItem("Tag", "/images/tag.png");

        var filterList = List.of(rootCategoryFilter, rootAuthorsFilter, rootPublishersFilter, rootFormatsFilter);

        rootFilterNode.getChildren().addAll(filterList.stream().map(AbstractFilterItem::getTreeItem).collect(Collectors.toList()));
        rootFilterNode.setExpanded(true);
        filtersTreeView.setRoot(rootFilterNode);

        rootCategoryFilter.getTreeItem()
                .getChildren()
                .addAll(ds.readCategories().stream()
                        .map(CategoryFilterItem::new)
                        .map(AbstractFilterItem::getTreeItem)
                        .collect(Collectors.toList()));

        rootAuthorsFilter.getTreeItem()
                .getChildren()
                .addAll(ds.readAuthors().stream()
                        .map(AuthorFilterItem::new)
                        .map(AbstractFilterItem::getTreeItem)
                        .collect(Collectors.toList()));

        rootPublishersFilter.getTreeItem()
                .getChildren()
                .addAll(ds.readPublishers().stream()
                        .map(PublisherFilterItem::new)
                        .map(AbstractFilterItem::getTreeItem)
                        .collect(Collectors.toList()));


        rootFormatsFilter.getTreeItem()
                .getChildren()
                .addAll(ds.readFormats().stream()
                        .map(FormatFilterItem::new)
                        .map(AbstractFilterItem::getTreeItem)
                        .collect(Collectors.toList()));

    }

    @FXML
    public void searchButtonClicked(MouseEvent mouseEvent) {
        var result = filterItems().stream() // Then apply the search text filtering
                .filter(b -> b.getBook().getTitle().toLowerCase().contains(searchBar.getText().toLowerCase()))
                .collect(Collectors.toList());

        filteredItems.clear();
        filteredItems.addAll(result);
    }

    @FXML
    public void searchBarKeyTyped(KeyEvent keyEvent) {
        System.out.println(keyEvent);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            searchButtonClicked(null);
        } else {
            if (searchBar.getText().isEmpty() && filteredItems.size() != allBooks.size()) {
                notifyFiltersChanged();
            }
        }

    }

}
