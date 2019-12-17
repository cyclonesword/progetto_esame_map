package com.biblioteca.core.auth;

import com.biblioteca.core.employee.Employee;
import com.biblioteca.datasource.DataSource;

public class EmailPasswordAuthentication implements Authentication {

    private String code;
    private String password;

    public EmailPasswordAuthentication(String code, String password) {
        this.code = code;
        this.password = password;
    }

    @Override
    public Employee authenticate() throws InvalidCredentialsException {
        var emps = DataSource.getDefault().getEmployees();

        return emps.stream()
                .filter(e -> e.getEmail().equals(code) && e.getPassword().equals(password))
                .findFirst()
                .orElseThrow(InvalidCredentialsException::new);
    }
}
