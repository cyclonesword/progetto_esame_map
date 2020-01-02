package com.biblioteca.core.builder;

import com.biblioteca.core.employee.Employee;
import com.biblioteca.core.employee.EmployeeFactory;

/**
 * The Builder for an Admin Employee type. See {@link Employee} class for the setters javadoc.
 */
public class AdminEmployeeBuilder implements EmployeeBuilder {

    private int id;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String employeeType;

    public EmployeeBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public EmployeeBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public EmployeeBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public EmployeeBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public EmployeeBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public EmployeeBuilder setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
        return this;
    }

    /**
     * Constructs a new Employee instance of type Administrator with the given values.
     *
     * @throws IllegalArgumentException If some of the provided values are invalid.
     */
    public Employee build() throws IllegalArgumentException {
        var employee = EmployeeFactory.create("admin");

        employee.setEmployeeNumber(id);
        employee.setEmail(email);
        employee.setName(firstName);
        employee.setLastName(lastName);
        employee.setPassword(password);

        return employee;
    }

}
