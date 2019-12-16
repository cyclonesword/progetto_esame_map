package com.biblioteca.ui.controller;

import com.biblioteca.core.Authentication;
import com.biblioteca.core.EmployeeCodePasswordAuthentication;
import com.biblioteca.core.Library;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class AuthenticationSceneController {

    public TextField matricola;
    public PasswordField passwrord;

    private Scene scene;
    private Stage primaryStage;
    private Parent root;

    public AuthenticationSceneController() {
        // Loads the MainWindow.fxml in another thread to avoid lag effect when the user press the Login button.
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
        root = FXMLLoader.load(getClass().getResource("/fxml/MainWindow.fxml"));
        scene = new Scene(root, 1024, 768);
    }

    public void loginClicked(MouseEvent mouseEvent) throws IOException {
        if (root == null)
            loadMainWindow();

        Authentication authStrategy = Authentication.from(matricola.getText(), passwrord.getText());

        if (authStrategy.authenticate()) {
            primaryStage.close();
            primaryStage.setTitle("Super Biblioteca 1.0");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
