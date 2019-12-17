module Progetto {

    requires javafx.fxml;
    requires javafx.controls;
    requires jfxglib;

    opens com.biblioteca.core;
    opens com.biblioteca.core.auth;
    opens com.biblioteca.core.employee;
    opens com.biblioteca.ui;
    opens com.biblioteca.ui.controller;
    opens com.biblioteca.ui.model;

    exports com.biblioteca.core;
    exports com.biblioteca.ui;
    exports com.biblioteca.ui.controller;
    exports com.biblioteca.ui.model;
    exports com.biblioteca.core.auth;
    exports com.biblioteca.core.employee;
}