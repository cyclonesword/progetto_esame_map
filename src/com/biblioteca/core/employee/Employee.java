package com.biblioteca.core.employee;

/**
 * Base interface that all employees must conform.
 */
public interface Employee {

    /**
     *
     * @return The employee code (matricola)
     */
    String getEmployeeNumber();

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
    static Employee newInstance(String number, String password, String firstName, String lastName, String email) {
        return new Administrator(number, password, firstName, lastName, email);
    }


    /**
     * A Builder to create new instances of classes conforming to the {@link Employee} interface .
     */
    class Builder {
        private String number;
        private String password;
        private String firstName;
        private String lastName;
        private String email;
        private String employeeType;

        public Builder setNumber(String number) {
            this.number = number;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setEmployeeType(String employeeType) {
            this.employeeType = employeeType;
            return this;
        }

        /**
         * Constructs a new instance with the assigned values.
         * @throws IllegalArgumentException If some of the provided values are invalid.
         */
        public Employee build() throws IllegalArgumentException {
            return create(employeeType);
        }

        /** This is a custom implementation of the <b>Factory method</b> design pattern.
         * It creates new instances based on the provided Employee type.
         * @param fromType The employee type. The default implementation type is 'admin'
         * @return A newly created instance for the given type.
         * @throws IllegalArgumentException if the specified type does not correspond to any implementation.
         */
        private Employee create(String fromType) throws IllegalArgumentException {

            if(fromType.equals("admin")) {
                return new Administrator(number, password, firstName, lastName, email);
            }

            throw new IllegalArgumentException("The "+fromType+ " employee type does not exists");
        }
    }
}
