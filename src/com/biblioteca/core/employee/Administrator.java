package com.biblioteca.core.employee;

/**
 * Implementation of the <code>Employee</code> interface  for an employee that have access to all the functionality of the software (i.e Administrator)
 * See the {@link Employee} interface for the complete javadoc
 */
public class Administrator implements Employee {

    private int employeeNumber;
    private String password;
    private String name;
    private String lastName;
    private String email;
    private String id;

    public Administrator(int employeeNumber, String password, String name, String lastName, String email) {
        this.employeeNumber = employeeNumber;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }

    public Administrator() {

    }

    @Override
    public int getEmployeeNumber() {
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

    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
