package com.biblioteca.ui.start;

import com.biblioteca.core.facade.Library;
import com.biblioteca.ui.controller.AuthenticationSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The starter point of this JavaFX Application.
 */
public class ApplicationStart extends Application {

    private static ApplicationStart instance;

    private String appName = "Memento legere";

    @Override
    public void start(Stage primaryStage) throws Exception{
//        var mainWindow = ClassLoader.getSystemResource("fxml/MainWindow.fxml");
     //   Parent root = FXMLLoader.load(getClass().getResource("/fxml/AuthenticationScene.fxml"));
        instance = this;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/AuthenticationScene.fxml"));
        Parent root = fxmlLoader.load();

        AuthenticationSceneController controller = fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Autenticazione Impiegato");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static ApplicationStart getInstance() {
        return instance;
    }

    public static void begin(String[] args) {
        launch(args);
    }

    public String getAppName() {
        return appName;
    }

    @Override
    public void stop()  {
        Library.getInstance().applicationWillClose();
    }
}
