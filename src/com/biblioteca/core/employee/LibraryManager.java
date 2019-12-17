package com.biblioteca.core.employee;

import java.util.EnumSet;

public class LibraryManager implements Employee {

    private String employeeNumber;
    private String password;
    private String name;
    private String lastName;
    private String email;

    public LibraryManager(String employeeNumber, String password, String name, String lastName, String email) {
        this.employeeNumber = employeeNumber;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getFirstName() {
        return name;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public boolean canPerform(Action action) {
        var set = EnumSet.allOf(Action.class);
        set.remove(Action.DELETE_ADMIN);
        return set.contains(action);
    }


}
