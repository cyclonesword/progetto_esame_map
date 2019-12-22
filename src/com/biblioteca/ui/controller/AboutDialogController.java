package com.biblioteca.ui.controller;

import com.biblioteca.ui.start.ApplicationStart;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

/**
 * Controller class that manages the About dialog window
 */
public class AboutDialogController implements DialogController<Void> {


    @FXML
    private Hyperlink linkToGPL;

    @FXML
    private Label appName;

    public void initialize() {
        appName.setText(ApplicationStart.getInstance().getAppName());
    }

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
