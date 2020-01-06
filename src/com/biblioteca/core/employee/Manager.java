package com.biblioteca.core.employee;

/**
 * Implementation of the <code>Employee</code> interface  for an employee that have access to a limited functionality of the software.
 * See the {@link Employee} interface for the complete javadoc
 */
public class Manager implements Employee {

    private int employeeNumber;
    private String password;
    private String name;
    private String lastName;
    private String email;

    public Manager(int employeeNumber, String password, String name, String lastName, String email) {
        this.employeeNumber = employeeNumber;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }

    public Manager() {

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
        return action.equals(Action.LOAN_BOOK) || action.equals(Action.ADD_USER);
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
