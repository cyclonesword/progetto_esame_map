package com.biblioteca.core.auth;

import com.biblioteca.core.employee.Employee;
import com.biblioteca.ui.utils.Utils;

import java.net.PasswordAuthentication;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     *
     * @return A new <code>Employee</code> instance if case of successful authentication.
     * @throws InvalidCredentialsException If wrong credentials are provided.
     */
    Employee authenticate() throws InvalidCredentialsException;

    /**
     * Utility method to provide a valid Authentication instance for the given employee code or email and password.
     *
     * @param emailOrCode The employeeCode (matricola)
     * @param password    The password
     * @return A valid Authentication instance for the given parameters.
     */
    static Authentication from(String emailOrCode, String password) throws InvalidCredentialsException {
        try {
            var code = Integer.parseInt(emailOrCode);
            return new CodePasswordAuthentication(code, password);
        } catch (NumberFormatException e) { // If it is not a numeric value, it could be an email
            if(Utils.isValidEmailAddress(emailOrCode))
                return new EmailPasswordAuthentication(emailOrCode, password);
            else
                throw new InvalidCredentialsException("The given email '"+emailOrCode+ "' is not valid");
        }
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
