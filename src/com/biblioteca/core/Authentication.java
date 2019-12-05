package com.biblioteca.core;

public interface Authentication {
    boolean authenticate() throws InvalidCredentialsException;

    class InvalidCredentialsException extends RuntimeException {
        public InvalidCredentialsException() {
        }

        public InvalidCredentialsException(String message) {
            super(message);
        }
    }
}
