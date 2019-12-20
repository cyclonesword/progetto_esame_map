package com.biblioteca.core.auth;

import com.biblioteca.core.employee.Employee;
import com.biblioteca.datasource.DataSource;

public class CodePasswordAuthentication implements Authentication {

    private String code;
    private String password;

    public CodePasswordAuthentication(String code, String password) {
        this.code = code;
        this.password = password;
    }

    @Override
    public Employee authenticate() throws InvalidCredentialsException {
        var emps = DataSource.getDefault().getEmployees();

        return emps.stream()
                .filter(e -> e.getEmployeeNumber().equals(code) && e.getPassword().equals(password))
                .findFirst()
                .orElseThrow(InvalidCredentialsException::new);
    }
}
