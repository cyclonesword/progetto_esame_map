package com.biblioteca.ui.controller;

import com.biblioteca.core.facade.Library;
import com.biblioteca.core.auth.Authentication;
import com.biblioteca.datasource.DataSource;
import com.biblioteca.ui.Dialogs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.Executors;

public class AuthenticationSceneController {

    @FXML
    private VBox rootNode;

    @FXML
    private TextField emailOrNumber;

    @FXML
    private PasswordField passwrord;

    private Scene mainWindowScene;
    private Stage primaryStage;
    private Parent mainWindowRootNode;

    private DataSource ds = DataSource.getDefault();

    public AuthenticationSceneController() {
        // Loads the MainWindow.fxml in another thread(asynchronously) to avoid lag effect when the user press the Login button.
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                System.out.println("Loading asynchronously the MainWindow.fxml UI .");
                loadMainWindow();
                System.out.println("Loading complete.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadMainWindow() throws IOException {
        mainWindowRootNode = FXMLLoader.load(getClass().getResource("/fxml/MainWindow.fxml"));
        mainWindowScene = new Scene(mainWindowRootNode, 1024, 768);
    }

    /**
     * This method is invoked by the JavaFX Runtime when the user press the Login button.
     *
     * @throws IOException if the .fxml file cannot be loaded. This is a critical error that will abort the application.
     */
    @FXML
    public void loginClicked() throws IOException {
        if (mainWindowRootNode == null)
            loadMainWindow();

        Authentication authStrategy = Authentication.from(emailOrNumber.getText(), passwrord.getText());

        try {
            var emp = authStrategy.authenticate();
            Library.getInstance().setLoggedEmployee(emp);

            primaryStage.close();
            primaryStage.setTitle("Super Biblioteca 1.0");
            primaryStage.setScene(mainWindowScene);
            primaryStage.show();

        } catch (Authentication.InvalidCredentialsException e) {
            e.printStackTrace();
            showErrorDialog();
        }

    }

    @FXML
    public void registerClicked() throws IOException {
        Dialogs.<RegisterEmployeeDialogController>showDialog("Nuovo impiegato",
                "Aggiungi Impiegato",
                "/fxml/NewEmployeeDialog.fxml", rootNode.getScene().getWindow(),
                controller -> { // Before showing the dialog

                },
                controller -> { // After the user press some confirmation button
                    var emp = controller.getEmployee();
                    ds.save(emp);

                });
    }

    private void showErrorDialog() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Credenziali non corrette!", ButtonType.OK);
        alert.initOwner(rootNode.getScene().getWindow());
        alert.showAndWait();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
