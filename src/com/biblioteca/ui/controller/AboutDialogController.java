package com.biblioteca.ui.controller;

import com.biblioteca.ui.ApplicationStart;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

public class AboutDialogController implements DialogController {


    @FXML
    private Hyperlink linkToGPL;

    @FXML
    private Label appName;

    public void initialize() {
        appName.setText(ApplicationStart.instance.getAppName());
    }

    @FXML
    public void linkToGPLClicked() {
        var services = ApplicationStart.instance.getHostServices();
        services.showDocument("https://www.gnu.org/licenses/old-licenses/gpl-2.0.html");
    }
}
