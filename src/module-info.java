module Progetto {

    requires javafx.fxml;
    requires javafx.controls;
    requires jfxglib;

    opens com.biblioteca.core;
    opens com.biblioteca.ui;

    exports com.biblioteca.core;
    exports com.biblioteca.ui;
    exports com.biblioteca.ui.controller;
    exports com.biblioteca.ui.model;
}