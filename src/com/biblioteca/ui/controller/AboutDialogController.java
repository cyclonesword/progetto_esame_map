package com.biblioteca.ui.controller;

import com.biblioteca.ui.start.ApplicationStart;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

/**
 * Controller class that manages the About window dialog
 */
public class AboutDialogController implements DialogController<Void> {


    @FXML
    private Hyperlink linkToGPL;

    @FXML
    private Label appName;

    public void initialize() {
        appName.setText(ApplicationStart.getInstance().getAppName());
    }

    /**
     * Opens the default web browser and displays the GPL 2.0 licence official web page.
     */
    @FXML
    public void linkToGPLClicked() {
        var services = ApplicationStart.getInstance().getHostServices();
        services.showDocument("https://www.gnu.org/licenses/old-licenses/gpl-2.0.html");
    }

    @Override
    public Void confirmAndGet() {
        throw new UnsupportedOperationException();
    }
}
