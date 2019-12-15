package com.biblioteca.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ApplicationStart extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        var mainWindow = ClassLoader.getSystemResource("fxml/MainWindow.fxml");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainWindow.fxml"));
        primaryStage.setTitle("Super Biblioteca 1.0");
        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.show();
    }

    public static void begin(String[] args) {
        launch(args);
    }
}
