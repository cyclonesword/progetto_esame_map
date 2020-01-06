package com.biblioteca.ui.controller;

import com.biblioteca.core.Loan;
import com.biblioteca.core.facade.Library;
import com.biblioteca.datasource.DataSource;
import com.biblioteca.ui.items.TableViewLoanRow;
import com.biblioteca.ui.utils.Dialogs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * Controller class of the Lent Books window dialog for managing the lent books.
 */
public class LentBooksDialogController implements DialogController {

    public TableColumn<TableViewLoanRow, Integer> id;
    public TableColumn<TableViewLoanRow, String> reservedBook;
    public TableColumn<TableViewLoanRow, String> user;
    public TableColumn<TableViewLoanRow, String> startDate;
    public TableColumn<TableViewLoanRow, String> status;
    public TableColumn<TableViewLoanRow, String> expectedReturnDate;

    @FXML
    private TableView<TableViewLoanRow> tableView;

    private DataSource ds = DataSource.getInstance();
    private Library library = Library.getInstance();


    public void initialize() {
        var items = FXCollections.observableArrayList(ds.getLoans()
                .stream()
                .map(TableViewLoanRow::new)
                .collect(Collectors.toList())
        );

        id.setCellValueFactory(new PropertyValueFactory<>("loanId"));
        reservedBook.setCellValueFactory(new PropertyValueFactory<>("reservedBook"));
        user.setCellValueFactory(new PropertyValueFactory<>("user"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        expectedReturnDate.setCellValueFactory(new PropertyValueFactory<>("expectedReturnDate"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableView.setItems(items);

        MenuItem returnedMenuItem = new MenuItem("Segna come restituito");
        MenuItem downloadPDF = new MenuItem("Scarica PDF");
        MenuItem deleteMenuItem = new MenuItem("Elimina");

        ContextMenu menu = new ContextMenu(downloadPDF, returnedMenuItem, deleteMenuItem);
        tableView.setContextMenu(menu);

        returnedMenuItem.setOnAction(event -> {
            getSelectedLoan().setAsReturned();
            tableView.refresh();
        });

        deleteMenuItem.setOnAction(event -> {
            removeLoan(items, getSelectedLoan());
            tableView.refresh();
        });

        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Seleziona cartella");

        downloadPDF.setOnAction(event -> {
            var directory = chooser.showDialog(tableView.getScene().getWindow());

            if (directory != null)
                copyFileTo(directory);
        });

        tableView.setOnMouseClicked(e -> {
            var selected = getSelectedLoan();

            if (selected == null)
                tableView.getSelectionModel().selectFirst();
            else if (selected.getStatus().equals(Loan.STATUS_RETURNED)) {
                menu.getItems().remove(returnedMenuItem);
                menu.getItems().remove(downloadPDF);
            }
            else {
                menu.getItems().add(returnedMenuItem);
                menu.getItems().add(downloadPDF);
            }

        });
    }

    private void copyFileTo(File source) {
        FileInputStream fis = null;

        try {
            File file = getSelectedLoan().generatePdfFile();
            fis = new FileInputStream(file);
            Path target = Paths.get(source.getPath() + File.separator + "loan_" + file.getName());
            Files.copy(fis, target);
            showFileSavedDialog(source);
            fis.close();
        } catch (IOException e) {
            Dialogs.showAlertDialog("Si è verificato un errore nel salvataggio del file. (Un file con lo stesso nome potrebbe già esistere nella cartella selezionata)", tableView.getScene().getWindow());
            try { fis.close(); } catch (Exception ignored) { }
        }
    }

    private void showFileSavedDialog(File dir) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "File copiato correttamente nella cartella " + dir.getName(), ButtonType.OK);
        alert.initOwner(tableView.getScene().getWindow());
        alert.showAndWait();
    }

    @Override
    public Object confirmAndGet() {
        throw new UnsupportedOperationException();
    }

    private TableViewLoanRow getSelectedLoan() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    private void removeLoan(ObservableList<TableViewLoanRow> items, TableViewLoanRow loan) {
        library.removeLoan(loan);
        items.remove(loan);
    }
}
