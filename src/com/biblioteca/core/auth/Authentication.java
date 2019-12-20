package com.biblioteca.core.auth;

import com.biblioteca.core.employee.Employee;

public interface Authentication {
    Employee authenticate() throws InvalidCredentialsException;

    class InvalidCredentialsException extends RuntimeException {
        public InvalidCredentialsException() {
            super("Invalid Credentials given");
        }

        public InvalidCredentialsException(String message) {
            super(message);
        }
    }

    static Authentication from(String empCode, String password) {
        return new CodePasswordAuthentication(empCode,password);
    }
}
