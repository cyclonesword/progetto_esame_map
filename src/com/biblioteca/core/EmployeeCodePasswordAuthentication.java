package com.biblioteca.core;

public class EmployeeCodePasswordAuthentication implements Authentication {

    private String code;
    private String password;

    public EmployeeCodePasswordAuthentication(String code, String password) {
        this.code = code;
        this.password = password;
    }

    @Override
    public boolean authenticate() throws InvalidCredentialsException {
        return true;
    }
}
