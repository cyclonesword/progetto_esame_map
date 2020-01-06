package com.biblioteca.core.employee;

/**
 * Factory class for creating Employee instances of a valid given type.
 */
public  class EmployeeFactory {

    /**
     * This is an implementation of the <b>Factory method</b> design pattern.
     * It creates new instances based on the provided Employee type.
     *
     * @param fromType The employee type. The default implementation type is 'admin'
     * @return A newly created instance for the given type.
     * @throws IllegalArgumentException if the specified type does not correspond to any implementation.
     */
    public static Employee create(String fromType) throws IllegalArgumentException {

        if (fromType.equals("admin")) {
            return new Administrator();
        } else if (fromType.equalsIgnoreCase("manager")) {
            return new Manager();
        }

        throw new IllegalArgumentException("The " + fromType + " employee type does not exists");
    }
}
