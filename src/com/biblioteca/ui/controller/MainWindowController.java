package com.biblioteca.ui.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainWindowController {

    @FXML
    public void handleExit(ActionEvent event) {
        Platform.exit();
    }

}
