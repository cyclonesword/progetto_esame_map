package com.biblioteca.ui.controller;

import com.biblioteca.ui.ApplicationStart;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;

import java.net.URL;

public class AboutDialogController implements DialogController {

    public Hyperlink linkToGPL;

    @FXML
    public void linkToGPLClicked() {
        var services = ApplicationStart.applicationInstance.getHostServices();
        services.showDocument("https://www.gnu.org/licenses/old-licenses/gpl-2.0.html");
    }
}
