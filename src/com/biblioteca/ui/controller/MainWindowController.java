package com.biblioteca.ui.controller;

import com.biblioteca.core.Book;
import com.biblioteca.core.BookImpl;
import com.biblioteca.datasource.DataSource;
import com.biblioteca.ui.Dialogs;
import com.biblioteca.ui.Images;
import com.biblioteca.ui.model.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.stream.Collectors;

public class MainWindowController {

    @FXML
    private BorderPane rootPane;

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

    public static void applyFilters() {
        filteredItems.clear();
        filteredItems.addAll(getFilteredItems());
    }

    private static List<BookListItem> getFilteredItems() {
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

    @FXML
    public void searchButtonClicked(MouseEvent mouseEvent) {
        var result = getFilteredItems().stream() // Then apply the search text filtering
                .filter(b -> b.getBook().getTitle().toLowerCase().contains(searchBar.getText().toLowerCase()))
                .collect(Collectors.toList());

        filteredItems.clear();
        filteredItems.addAll(result);
    }

    @FXML
    public void searchBarKeyReleased(KeyEvent keyEvent) {
        System.out.println(keyEvent);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            searchButtonClicked(null);
        } else {
            if (searchBar.getText().isEmpty() && filteredItems.size() != allBooks.size()) {
                applyFilters();
            }
        }

    }

    @FXML
    public void addBookClicked() throws IOException {
        var book = new BookImpl();

        Dialogs.<ModifyBookDialogController>showDialog("Nuovo libro", "Aggiungi", "/fxml/ModifyBookDialog.fxml", rootPane.getScene().getWindow(),
                controller -> controller.setBook(book, false),
                controller -> {
                    controller.applyData();
                    ds.save(book);
                    allBooks.add(new BookListItem(book));
                    applyFilters();
                    refreshListView();
                });
    }

    @FXML
    public void editBookClicked() {
        Dialog<ButtonType> dialog = new Dialog<>();
        FXMLLoader fxmlLoader = new FXMLLoader();

        try {
            fxmlLoader.setLocation(getClass().getResource("/fxml/ModifyBookDialog.fxml"));
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ModifyBookDialogController controller = fxmlLoader.getController();
        controller.setBook(listView.getSelectionModel().getSelectedItem().getBook(), true);
        dialog.initOwner(listView.getScene().getWindow());

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.setTitle("Modify Book");

        dialog.showAndWait();

        if (dialog.getResult().equals(ButtonType.OK)) {
            System.out.println("Ok clicked");
            controller.applyData();
            refreshListView();
        }
    }

    @FXML
    public void deleteBookClicked(MouseEvent mouseEvent) {
        final Book book = listView.getSelectionModel().getSelectedItem().getBook();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                String.format("Sei sicuro di volere eliminare %s - %s?", book.getTitle(), book.getSubtitle()),
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            ds.delete(book);
            filteredItems.removeIf(i -> i.getBook() == book);
            allBooks.removeIf(i -> i.getBook() == book);
        }
    }

    @FXML
    public void reserveBookClicked(MouseEvent mouseEvent) throws IOException {
        final Book book = listView.getSelectionModel().getSelectedItem().getBook();

        Dialogs.<LoanDialogController>showDialog("Nuovo prestito",
                "Conferma",
                "/fxml/LoanDialog.fxml",
                rootPane.getScene().getWindow(),
                controller -> {
                    controller.setReservedBook(book);
                }, controller -> {
                    var loan = controller.getLoan();
                    ds.save(loan);
                    book.decrementQuantity();
                    refreshListView();
                });
    }

    @FXML
    public void addUserClicked(ActionEvent actionEvent) throws IOException {
        Dialogs.<AddUserDialogController>showDialog("Add user", "/fxml/AddUserDialog.fxml", rootPane.getScene().getWindow(),
                controller -> {
                }, controller -> {
                    ds.save(controller.getUser());
                    var u = ds.readCustomers();
                    System.out.println(u);
                });
    }

    /**
     * Invoked when the user click onto the "show loans" button. This method will open a new window dialog .
     *
     * @param mouseEvent The mouse event occurred
     * @throws IOException If something horrible happens
     */
    @FXML
    public void showReservedBooksClicked(MouseEvent mouseEvent) throws IOException {
        Dialogs.<ReservedBooksDialogController>showDialog("Prestiti", "Ok", "/fxml/ReservedBookDialog.fxml",
                rootPane.getScene().getWindow(),
                controller -> {
                }, controller -> {
                });
    }

    private void refreshListView() {
        listView.refresh();
        changeItemDetail(listView.getSelectionModel().getSelectedItem());
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
    public void onAboutClicked() throws IOException {
        Dialogs.<AboutDialogController>showDialog("About", "Ok", "/fxml/About.fxml",
                rootPane.getScene().getWindow(),
                null, null);
    }
}
