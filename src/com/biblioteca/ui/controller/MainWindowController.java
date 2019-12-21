package com.biblioteca.ui.controller;

import com.biblioteca.core.Author;
import com.biblioteca.core.Book;
import com.biblioteca.core.BookImpl;
import com.biblioteca.core.facade.Library;
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
    private TreeView<FilterItem> filtersTreeView;

    @FXML
    public void handleExit(ActionEvent event) {
        Platform.exit();
    }

    private static final ObservableList<BookListItem> allBooks = FXCollections.observableArrayList();
    private static final ObservableList<BookListItem> filteredItems = FXCollections.observableArrayList();
    private static final DataSource ds = DataSource.getDefault();

    public static final Set<FilterItem> selectedFilters = new HashSet<>();

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

        // Wraps every book with a BookListItem
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

    // changes the content of the book detail section on the right
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

    /**
     * Invoked by the JavaFX Runtime when the user search a book in the search bar by pressing the search button.
     */
    @FXML
    public void searchButtonClicked(MouseEvent mouseEvent) {
        var result = getFilteredItems().stream() // Then apply the search text filtering
                .filter(b -> b.getBook().getTitle().toLowerCase().contains(searchBar.getText().toLowerCase()))
                .collect(Collectors.toList());

        filteredItems.clear();
        filteredItems.addAll(result);
    }

    /**
     * Invoked by the JavaFX Runtime when the user search a book in the search bar, pressing the Enter key of the keyboard.
     */
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

    /**
     * Invoked by the JavaFX Runtime when the user click onto the "Add Book" menu button. <br> This method will open a new window dialog .
     *
     * @throws IOException If something goes wrong with the FXML file loading.
     */
    @FXML
    public void addBookClicked() throws IOException {
        var book = new BookImpl();

        Dialogs.<ModifyBookDialogController>showDialog("Nuovo libro", "Aggiungi", "/fxml/ModifyBookDialog.fxml", rootPane.getScene().getWindow(),
                controller -> controller.setBook(book, false),
                controller -> {
                    controller.confirmAndGet();
                    ds.save(book);
                    allBooks.add(new BookListItem(book));
                    applyFilters();
                    refreshListView();
                });
    }

    /**
     * Invoked by the JavaFX Runtime when the user click onto the "Modify" button on the book detail right panel.
     * This method will open a new window dialog .
     *
     * @throws IOException If something goes wrong with the FXML file loading.
     */
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
            controller.confirmAndGet();
            refreshListView();
        }
    }

    /**
     * Invoked by the JavaFX Runtime when the user click onto the "Delete" button on the book detail right panel.
     * This method will open a new window dialog .
     *
     * @throws IOException If something goes wrong with the FXML file loading.
     */
    @FXML
    public void deleteBookClicked() {
        final Book book = listView.getSelectionModel().getSelectedItem().getBook();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                String.format("Sei sicuro di volere eliminare %s - %s?", book.getTitle(), book.getSubtitle()),
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            Library.getInstance().removeBook(book);
            filteredItems.removeIf(i -> i.getBook() == book);
            allBooks.removeIf(i -> i.getBook() == book);
        }
    }

    /**
     * Invoked by the JavaFX Runtime when the user click onto the "Start Loan" button on the book detail right panel.
     * This method will open a new window dialog .
     *
     * @throws IOException If something goes wrong with the FXML file loading.
     */
    @FXML
    public void lendBookClicked() throws IOException {
        final Book book = listView.getSelectionModel().getSelectedItem().getBook();

        Dialogs.<LoanDialogController>showDialog("Nuovo prestito",
                "Conferma",
                "/fxml/LoanDialog.fxml",
                rootPane.getScene().getWindow(),
                controller -> controller.setLentBook(book),
                controller -> {
                    if (controller.checkData()) {
                        controller.confirmAndGet();
                        refreshListView();
                    }
                });
    }

    /**
     * Invoked by the JavaFX Runtime when the user click onto the "Add User" menu button. This method will open a new window dialog .
     *
     * @throws IOException If something goes wrong with the FXML file loading.
     */
    @FXML
    public void addUserClicked(ActionEvent actionEvent) throws IOException {
        // ds.save(controller.getUser());
        Dialogs.<AddUserDialogController>showDialog("Add user", "/fxml/AddUserDialog.fxml",
                rootPane.getScene().getWindow(),
                null,
                AddUserDialogController::confirmAndGet);
    }

    /**
     * Invoked by the JavaFX Runtime when the user click onto the "Add Author" menu button. This method will open a new window dialog .
     *
     * @throws IOException If something goes wrong with the FXML file loading.
     */
    @FXML
    public void addAuthorClicked(ActionEvent actionEvent) throws IOException {
        Dialogs.<AddAuthorDialogController>showDialog("Add Book Author", "Add",
                "/fxml/AddAuthorDialog.fxml",
                rootPane.getScene().getWindow(),
                null,
                controller -> {
                    Author a = controller.confirmAndGet();
                    filtersTreeView.getRoot().getChildren().get(1).getChildren().add(new FilterItem(a.getName(), i -> i.getAuthors().contains(a)).getTreeItem());
                });
    }

    /**
     * Invoked by the JavaFX Runtime when the user click onto the "show loans" button. This method will open a new window dialog .
     *
     * @param mouseEvent The mouse event occurred
     * @throws IOException If something horrible happens
     */
    @FXML
    public void showReservedBooksClicked(MouseEvent mouseEvent) throws IOException {
        Dialogs.<LentBooksDialogController>showDialog("Prestiti", "Ok", "/fxml/ReservedBookDialog.fxml",
                rootPane.getScene().getWindow(),
                null,
                controller -> refreshListView());
    }

    /**
     * Invoked by the JavaFX Runtime when the user click onto the "About" menu button. This method will open a new window dialog .
     *
     * @throws IOException If something goes wrong with the FXML file loading.
     */
    @FXML
    public void onAboutClicked() throws IOException {
        Dialogs.<AboutDialogController>showDialog("About", "Ok", "/fxml/About.fxml",
                rootPane.getScene().getWindow(),
                null, null);
    }

    private void refreshListView() {
        listView.refresh();
        changeItemDetail(listView.getSelectionModel().getSelectedItem());
    }

    // Configures the filters displayed in the left section.
    private void initFilters() {
        filtersTreeView.setCellFactory(FilterTreeCell::new);

        var rootFilterNode = new RootFilterItem("Filtri", "/images/filter.png").getTreeItem();
        var rootCategoryFilter = new RootFilterItem("Categorie", "/images/category.png");
        var rootAuthorsFilter = new RootFilterItem("Autori", "/images/authors.png");
        var rootPublishersFilter = new RootFilterItem("Editori", "/images/publisher.png");
        var rootFormatsFilter = new RootFilterItem("Formato", "/images/format.png");
        // var rootTagFilter = new RootFilterItem("Tag", "/images/tag.png");

        var filterList = List.of(rootCategoryFilter, rootAuthorsFilter, rootPublishersFilter, rootFormatsFilter);

        rootFilterNode.getChildren().addAll(filterList.stream().map(FilterItem::getTreeItem).collect(Collectors.toList()));
        rootFilterNode.setExpanded(true);
        filtersTreeView.setRoot(rootFilterNode);

        rootCategoryFilter.getTreeItem()
                .getChildren()
                .addAll(ds.readCategories()
                        .stream()
                        .sorted()
                        .map(c -> new FilterItem(c.getName(), item -> item.getCategories().contains(c)))
                        .map(FilterItem::getTreeItem)
                        .collect(Collectors.toList()));

        rootAuthorsFilter.getTreeItem()
                .getChildren()
                .addAll(ds.readAuthors()
                        .stream()
                        .sorted()
                        .map(author -> new FilterItem(author.getName(), item -> item.getAuthors().contains(author)))
                        .map(FilterItem::getTreeItem)
                        .collect(Collectors.toList()));

        rootPublishersFilter.getTreeItem()
                .getChildren()
                .addAll(ds.readPublishers()
                        .stream()
                        .sorted()
                        .map(publisher -> new FilterItem(publisher.getName(), item -> item.getPublisher() == publisher))
                        .map(FilterItem::getTreeItem)
                        .collect(Collectors.toList()));

        rootFormatsFilter.getTreeItem()
                .getChildren()
                .addAll(ds.readFormats()
                        .stream()
                        .map(format -> new FilterItem(format, item -> item.getFormat().equalsIgnoreCase(format)))
                        .map(FilterItem::getTreeItem)
                        .collect(Collectors.toList()));

    }
}
