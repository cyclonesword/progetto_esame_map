package com.biblioteca.core.employee;

/**
 * Base interface for all Employee types.
 */
public interface Employee {

    /**
     *
     * @return The employee code (matricola)
     */
    int getEmployeeNumber();

    /**
     *
     * @return The employee's password
     */
    String getPassword();

    /**
     *
     * @return The employee's First name
     */
    String getFirstName();

    /**
     *
     * @return The employee's Last name
     */
    String getLastName();

    /**
     *
     * @return The employee's email address
     */
    String getEmail();

    /**
     * Check if the employee can do the  given <code>Action</code>. <br> All possible actions are defined by the {@link com.biblioteca.core.employee.Employee.Action} enumeration.
     * @param action The action that you want to check.
     * @return <code>true</code> if the employee can perform this Action, false otherwise.
     */
    boolean canPerform(Action action);

    /**
     * Sets the employee code
     * @param employeeNumber The code of the employee
     */
    void setEmployeeNumber(int employeeNumber);

    /**
     *
     * @param password The password of this employee
     */
    void setPassword(String password);

    /**
     *
     * @param name The first name of this employee
     */
    void setName(String name);

    /**
     *
     * @param lastName The last name of this employee
     */
    void setLastName(String lastName);

    /**
     *
     * @param email  A valid email address
     */
    void setEmail(String email);

    /**
     * Returns the full name (First name + Last name) of this employee
     * @return The full name
     */
    default String getFullName() {
        return String.format("%s %s", getFirstName(),getLastName());
    }

    /**
     *  Lists all the possible Actions that an {@link Employee} can do.
     */
    enum Action {
        LOAN_BOOK, MODIFY_BOOK, ADD_USER, DELETE_ADMIN, DELETE_BOOK
    }

    /**
     * Creates a new instance of the {@link Employee} interface using the default {@link Administrator} implementation.
     * @param number The employee's code number (matricola)
     * @param password The employee's password
     * @param firstName
     * @param lastName
     * @param email
     * @return
     */
    static Employee newInstance(int number, String password, String firstName, String lastName, String email) {
        return new Administrator(number, password, firstName, lastName, email);
    }

}
