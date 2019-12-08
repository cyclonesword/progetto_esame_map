package com.biblioteca.core;

import java.util.List;

/*
--module-path "JavaFX 13\lib" --add-modules javafx.controls,javafx.fxml
 */

public class Library {

    private String name;
    private String address;

    private List<Employee> employees;

    private static Library instance;

    public static Library getInstance() {
        if(instance == null)
            instance = new Library();
        return instance;
    }

    public void newLoan(Book book, Authorization authorization) {

    }

}
