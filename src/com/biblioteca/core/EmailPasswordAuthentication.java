package com.biblioteca.core;

public class EmailPasswordAuthentication implements Authentication {

    private String email;
    private String password;

    public EmailPasswordAuthentication(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean authenticate() throws InvalidCredentialsException {
        return false;
    }
}
