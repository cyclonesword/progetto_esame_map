package com.biblioteca.core.facade;

import com.biblioteca.core.employee.Employee;
import com.biblioteca.datasource.DataSource;

/*
--module-path "JavaFX 13\lib" --add-modules javafx.controls,javafx.fxml
 */

public class Library {

    private String name;
    private String address;

    private Employee loggedEmployee;

    private static Library instance;

    private static final DataSource ds = DataSource.getDefault();

    public static Library getInstance() {
        if(instance == null)
            instance = new Library();
        return instance;
    }


    public void setLoggedEmployee(Employee loggedEmployee) {
        this.loggedEmployee = loggedEmployee;
    }

    public void applicationWillClose() {
        ds.saveAll();
    }
}
