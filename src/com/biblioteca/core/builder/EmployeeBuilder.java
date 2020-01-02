package com.biblioteca.core.builder;

import com.biblioteca.core.employee.Employee;

/**
 * A simplified version of the Builder pattern to create new instances of classes conforming to the {@link Employee} interface .
 */
public interface EmployeeBuilder {

    /**
     * Sets an id to the Employee. Clients are responsible for providing unique ids.
     * @param id The unique identifier for this Employee
     * @return The book builder instance to allow chaining.
     */
    EmployeeBuilder setId(int id);

    /**
     * Sets the employee's passswowrd
     * @param password
     * @return The employee builder instance to allow chaining.
     */
    EmployeeBuilder setPassword(String password);

    /**
     * Sets the employee's first name
     * @param firstName
     * @return The employee builder instance to allow chaining.
     */
    EmployeeBuilder setFirstName(String firstName);

    /**
     * Sets the employee's last name
     * @param lastName
     * @return The employee builder instance to allow chaining.
     */
    EmployeeBuilder setLastName(String lastName);

    /**
     * Sets the email of the employee
     * @param email
     * @return The employee builder instance to allow chaining.
     */
    EmployeeBuilder setEmail(String email);

    /**
     * The type of the employee. It can be admin or manager.
     * @param employeeType either admin or manager
     * @return The employee builder instance to allow chaining.
     */
    EmployeeBuilder setEmployeeType(String employeeType);

    /**
     * Constructs a new instance with the assigned values.
     *
     * @throws IllegalArgumentException If some of the provided values are invalid.
     */
    Employee build() throws IllegalArgumentException;

    static EmployeeBuilder getDefault() {
        return new AdminEmployeeBuilder();
    }
}