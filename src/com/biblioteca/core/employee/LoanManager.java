package com.biblioteca.core.employee;

import java.util.EnumSet;

import static com.biblioteca.core.employee.Employee.Action.*;

public class LoanManager implements Employee {

    private String employeeNumber;
    private String name;
    private String lastName;
    private String email;
    private String password;

    private EnumSet<Action> permissions = EnumSet.of(LOAN_BOOK, ADD_USER);

    public LoanManager(String employeeNumber, String password, String name, String lastName, String email) {
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
        return permissions.contains(action);
    }
}
