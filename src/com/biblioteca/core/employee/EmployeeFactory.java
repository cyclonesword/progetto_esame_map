package com.biblioteca.core.employee;

public class EmployeeFactory {

    private String employeeNumber;
    private String name;
    private String lastName;
    private String email;
    private String password;

    public Employee getEmployee(String level) {

        switch(level) {
            case "admin" :
                return new Administrator(employeeNumber, password, name, lastName, email);
            case "manager":
                return new LibraryManager(employeeNumber, password, name, lastName, email);
            case "assistant" :
                return new LoanManager(employeeNumber, password, name, lastName, email);
        }

        throw new IllegalArgumentException(level+ " does not represent a valid Employee level type.");
    }

    public Employee getEmployee(Employee.Level level) {
        return getEmployee(level.name().toLowerCase());
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
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

    public void setPassword(String password) {
        this.password = password;
    }

}
