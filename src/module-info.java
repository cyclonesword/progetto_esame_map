module Progetto {

    requires javafx.base;
    requires javafx.fxml;
    requires javafx.controls;
    requires itextpdf;
    requires java.desktop;

    opens com.biblioteca.core;
    opens com.biblioteca.core.auth;
    opens com.biblioteca.core.facade;
    opens com.biblioteca.core.employee;
    opens com.biblioteca.core.builder;

    opens com.biblioteca.ui.controller;
    opens com.biblioteca.ui.start;
    opens com.biblioteca.ui.utils;
    opens com.biblioteca.ui.items;

    opens com.biblioteca.datasource;

    exports com.biblioteca.core;
    exports com.biblioteca.datasource;

    exports com.biblioteca.core.auth;
    exports com.biblioteca.core.employee;
    exports com.biblioteca.core.facade;
    exports com.biblioteca.core.builder;

    exports com.biblioteca.ui.controller;
    exports com.biblioteca.ui.items;
    exports com.biblioteca.ui.start;
    exports com.biblioteca.ui.utils;
}