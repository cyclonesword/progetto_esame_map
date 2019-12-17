package com.biblioteca.core;

import com.biblioteca.core.employee.Employee;

import java.util.List;

/*
--module-path "JavaFX 13\lib" --add-modules javafx.controls,javafx.fxml
 */

public class Library {

    private String name;
    private String address;

    private Employee loggedEmployee;

    private static Library instance;

    public static Library getInstance() {
        if(instance == null)
            instance = new Library();
        return instance;
    }


    public void setLoggedEmployee(Employee loggedEmployee) {
        this.loggedEmployee = loggedEmployee;
    }
}
