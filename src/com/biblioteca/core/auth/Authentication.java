package com.biblioteca.core.auth;

import com.biblioteca.core.employee.Employee;

import java.net.PasswordAuthentication;

/**
 * Base interface to perform authentication with various strategies. <br><br>
 * The <b>Strategy Design pattern</b> is applied here: <br><br>
 * Two implementation of this interface <code>CodePasswordAuthentication</code> and <code>EmailPasswordAuthentication</code>
 * performs authentication with two different strategies.
 */
public interface Authentication {

    /**
     * Performs authentication of Employees.<br><br>
     * The implementors of this interface are responsible for providing the credentials needed for the authentication
     * @return A new <code>Employee</code> instance if case of successful authentication.
     * @throws InvalidCredentialsException If wrong credentials are provided.
     */
    Employee authenticate() throws InvalidCredentialsException;

    /**
     * Utility method to provide a valid Authentication instance for the given employeeCode and password.
     * @param empCode The employeeCode (matricola)
     * @param password The password
     * @return A valid Authentication instance for the given parameters.
     */
    static Authentication from(String empCode, String password) {
        return new EmailPasswordAuthentication(empCode,password);
    }

    /**
     * Exception used to signal wrong credentials error.
     */
    class InvalidCredentialsException extends RuntimeException {
        public InvalidCredentialsException() {
            super("Invalid Credentials given");
        }

        public InvalidCredentialsException(String message) {
            super(message);
        }
    }
}
