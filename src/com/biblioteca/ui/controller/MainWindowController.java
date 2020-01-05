package com.biblioteca.ui.controller;

import com.biblioteca.core.Author;
import com.biblioteca.core.Book;
import com.biblioteca.core.BookImpl;
import com.biblioteca.core.Loan;
import com.biblioteca.core.facade.Library;
import com.biblioteca.datasource.DataSource;
import com.biblioteca.ui.items.BookListItem;
import com.biblioteca.ui.items.FilterItem;
import com.biblioteca.ui.items.ListItem;
import com.biblioteca.ui.utils.Dialogs;
import com.biblioteca.ui.utils.Images;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.biblioteca.ui.items.FilterItem.FilterCategory;

/**
 * This controller class is responsible of all the behaviour management of the central window of the application.<br>
 * It is responsible for managing the user interaction with the UI.<br><br>
 * All methods with the annotation <code>@FXML</code> means that it is invoked by the JavaFX Runtime
 * when the user interacts with the UI. <br><br>
 * <p>
 * In the Model View Controller architectural pattern used here,
 * this class represents the Controller,
 * the MainWindow.fxml is the View and
 * the Model is represented mainly by the Library Facade class and by other classes in the <code>com.biblioteca.core</code> package, representing the business logic of the application.
 */
public class MainWindowController {

    @FXML
    private Label authenticatedEmployeeName;

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

    private static final ObservableList<BookListItem> allBooks = FXCollections.observableArrayList();
    private static final ObservableList<BookListItem> filteredItems = FXCollections.observableArrayList();
    public static final Set<FilterItem> selectedFilters = new HashSet<>();

    private final DataSource ds = DataSource.getInstance();
    private final Library library = Library.getInstance();

    /**
     * Initialize the ListView and the TreeView with initial data.
     * This method is called reflectively by the JavaFX Runtime.
     */
    // This method will be called automatically by the JavaFX runtime.
    public void initialize() {

        // Wraps every book with a BookListItem
        allBooks.addAll(ds.getBooks().stream()
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

    public void initCompleted() {
        authenticatedEmployeeName.setText("Impiegato autenticato: " + Library.getInstance().getLoggedEmployee().getFullName());
    }
    // changes the content of the book detail section on the right

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
     */
    @FXML
    public void deleteBookClicked() {
        final Book book = listView.getSelectionModel().getSelectedItem().getBook();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                String.format("Sei sicuro di volere eliminare %s?", book.getTitle()),
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            try {
                library.removeBook(book);
            } catch (DataSource.BookDependencyException e) {
                Dialogs.showAlertDialog("Non è possibile eliminare un libro che è stato prestato, anche se già restituito. Devi prima eliminare tutti i prestiti ad esso associati.", getRootWindow());
                return;
            }
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
                getRootWindow(),
                controller -> controller.setLentBook(book),
                controller -> {
                    var loan = controller.confirmAndGet();
                    viewPdfDialog(loan);
                    refreshListView();
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
        Dialogs.<AddCustomerDialogController>showDialog("Aggiungi utente", "/fxml/AddUserDialog.fxml",
                getRootWindow(),
                null,
                AddCustomerDialogController::confirmAndGet);
    }

    /**
     * Invoked by the JavaFX Runtime when the user click onto the "Add Author" menu button. This method will open a new window dialog .
     *
     * @throws IOException If something goes wrong with the FXML file loading.
     */
    @FXML
    public void addAuthorClicked(ActionEvent actionEvent) throws IOException {
        Dialogs.<AddAuthorDialogController>showDialog("Aggiungi autore", "Add",
                "/fxml/AddAuthorDialog.fxml",
                getRootWindow(),
                null,
                controller -> {
                    Author a = controller.confirmAndGet();
                    filtersTreeView.getRoot().getChildren().get(1).getChildren().add(new FilterItem(a.getName(), i -> i.getAuthors().contains(a)).getTreeItem());
                });
    }

    /**
     * Invoked by the JavaFX Runtime when the user click onto the "Add Publisher" menu button. This method will open a new window dialog .
     *
     * @throws IOException If something goes wrong with the FXML file loading.
     */
    @FXML
    public void addPublisherClicked() throws IOException {
        Dialogs.<AddPublisherDialogController>showDialog("Aggiungi editore", "Ok",
                "/fxml/AddPublisherDialog.fxml",
                getRootWindow(),
                null,
                controller -> {
                    var p = controller.confirmAndGet();
                    filtersTreeView.getRoot().getChildren().get(2).getChildren().add(new FilterItem(p.getName(), i -> i.getPublisher() == p).getTreeItem());
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
                getRootWindow(),
                null,
                controller -> refreshListView());
    }

    /**
     * Invoked by the JavaFX Runtime when the user click onto the "About" menu button.<br> This method will open a new window dialog .
     *
     * @throws IOException If something goes wrong with the FXML file loading.
     */
    @FXML
    public void onAboutClicked() throws IOException {
        Dialogs.<AboutDialogController>showDialog("About", "Ok", "/fxml/About.fxml",
                getRootWindow(),
                null, null);
    }

    /**
     * Invoked by the JavaFX Runtime when the user click onto the "Add Book" menu button. <br> This method will open a new window dialog .
     *
     * @throws IOException If something goes wrong with the FXML file loading.
     */
    @FXML
    public void addBookClicked() throws IOException {
        var book = new BookImpl();

        Dialogs.<ModifyBookDialogController>showDialog("Nuovo libro", "Aggiungi", "/fxml/ModifyBookDialog.fxml", getRootWindow(),
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
     * Invoked by the JavaFX Runtime when the user click onto the "Search for updates" menu button. <br>This method will open a new window dialog .
     */
    @FXML
    public void onSearchUpdateClicked() {
        Dialogs.showAlertDialog("Non ci sono aggiornamenti disponibili", getRootWindow());
    }



    // =============== ***** private methods **** ================== //
    private void viewPdfDialog(Loan pdfGenerator) {
        Dialogs.showInfoDialog("Vuoi visualizzare il pdf relativo al prestito?",
                getRootWindow(),
                () -> {
                    try {
                        File f = pdfGenerator.generatePdfFile();
                        Desktop.getDesktop().open(f);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void changeItemDetail(ListItem item) {
        if (item == null)
            return;

        bookDetailTitle.setText(item.getItemTitle());
        bookDetailDescription.setText(item.getItemDescription());
        bookDetailImageView.setImage(item.getImage());
        availabilityImage.setImage(item.getQuantity() > 0 ? Images.GREEN_CIRCLE : Images.RED_CIRCLE);
        availabilityText.setText(item.isAvailable() ? item.getQuantity() + " Disponibili" : "Prestato");
        prenotaButton.setDisable(!item.isAvailable());
    }

    // Configures the filters displayed in the left section.
    private void initFilters() {
        filtersTreeView.setCellFactory(FilterTreeCell::new);

        var rootFilterNode = new FilterItem("Filtri", "/images/filter.png").getTreeItem();
        var rootCategoryFilter = new FilterItem("Categorie", "/images/category.png");
        var rootAuthorsFilter = new FilterItem("Autori", "/images/authors.png");
        var rootPublishersFilter = new FilterItem("Editori", "/images/publisher.png");
        var rootFormatsFilter = new FilterItem("Formato", "/images/format.png");
        // var rootTagFilter = new RootFilterItem("Tag", "/images/tag.png");

        var filterList = List.of(rootCategoryFilter, rootAuthorsFilter, rootPublishersFilter, rootFormatsFilter);

        rootFilterNode.getChildren().addAll(filterList.stream().map(FilterItem::getTreeItem).collect(Collectors.toList()));
        rootFilterNode.setExpanded(true);
        filtersTreeView.setRoot(rootFilterNode);

        rootCategoryFilter.getTreeItem()
                .getChildren()
                .addAll(ds.getCategories()
                        .stream()
                        .sorted()
                        .map(c -> new FilterItem(c.getName(), FilterCategory.CATEGORY, item -> item.getCategories().contains(c)))
                        .map(FilterItem::getTreeItem)
                        .collect(Collectors.toList()));

        rootAuthorsFilter.getTreeItem()
                .getChildren()
                .addAll(ds.getAuthors()
                        .stream()
                        .sorted()
                        .map(author -> new FilterItem(author.getName(), FilterCategory.AUTHOR, item -> item.getAuthors().contains(author)))
                        .map(FilterItem::getTreeItem)
                        .collect(Collectors.toList()));

        rootPublishersFilter.getTreeItem()
                .getChildren()
                .addAll(ds.getPublishers()
                        .stream()
                        .sorted()
                        .map(publisher -> new FilterItem(publisher.getName(), FilterCategory.PUBLISHER, item -> item.getPublisher() == publisher))
                        .map(FilterItem::getTreeItem)
                        .collect(Collectors.toList()));

        rootFormatsFilter.getTreeItem()
                .getChildren()
                .addAll(ds.getFormats()
                        .stream()
                        .map(format -> new FilterItem(format, FilterCategory.FORMAT, item -> item.getFormat().equalsIgnoreCase(format)))
                        .map(FilterItem::getTreeItem)
                        .collect(Collectors.toList()));

    }

    /**
     * Called by the FilterTreeCell class when the employee select a new filter from the left section of the application
     */
    public static void applyFilters() {
        filteredItems.clear();
        filteredItems.addAll(getFilteredItems());
    }

    private void refreshListView() {
        listView.refresh();
        changeItemDetail(listView.getSelectionModel().getSelectedItem());
    }

    private Window getRootWindow() {
        return rootPane.getScene().getWindow();
    }

    private static List<BookListItem> getFilteredItems() {
        return allBooks.stream()
                .filter(book -> selectedFilters.stream()
                        .anyMatch(filter -> filter.applyTo(book)) || selectedFilters.isEmpty())
                .collect(Collectors.toList());



//        var satisfiedCategories = new HashSet<FilterCategory>();
//        List<BookListItem> filteredBooks = new ArrayList<>();
//
//        for (FilterItem f : selectedFilters) {
//            if(!satisfiedCategories.contains(f.getFilterCategory())) {
//                allBooks.forEach(book -> {
//
//                   if(f.applyTo(book))  {
//                       satisfiedCategories.add(f.getFilterCategory());
//                       filteredBooks.add(book);
//                   }
//
//                });
//            }
//        }
//        return filteredBooks;

//        return allBooks.stream()
//                .filter(book -> {
//                    selectedFilters
//                            .stream()
//                            .filter(f -> !satisfiedCategories.contains(f.getFilterCategory()))
//                            .forEach(filterItem -> {
//                                var filterResult = filterItem.applyTo(book);
//                                if(filterResult) {
//                                    satisfiedCategories.add(filterItem.getFilterCategory());
//                                }
//                            });
//                })
//                .collect(Collectors.toList());
    }

    // ============ ***** private methods start ***** ============ //
}
