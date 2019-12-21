package com.biblioteca.core.employee;

/**
 * Implementation of the <code>Employee</code> interface  for an employee that have access to all the functionality of the software (i.e Administrator)
 */
public class Administrator implements Employee {

    private String employeeNumber;
    private String password;
    private String name;
    private String lastName;
    private String email;

    public Administrator(String employeeNumber, String password, String name, String lastName, String email) {
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
        return true; // Since the administrator can do anything, return always true.
    }


}
