package com.biblioteca.core.auth;

import com.biblioteca.core.employee.Employee;
import com.biblioteca.datasource.DataSource;
import com.biblioteca.ui.utils.Utils;

/**
 * This implementation of the <code>Authentication</code> interface performs authentication for the email / password authentication methodology. <br>
 * See the {@link Authentication} interface for the complete javadoc
 */
public class EmailPasswordAuthentication implements Authentication {

    private String email;
    private String password;

    public EmailPasswordAuthentication(String email, String password) {
        this.email = email;
        this.password = Utils.sha1Digest(password);
    }

    @Override
    public Employee authenticate() throws InvalidCredentialsException {
        var emps = DataSource.getInstance().getEmployees();

        return emps.stream()
                .filter(e -> e.getEmail().equals(email) && e.getPassword().equals(password))
                .findFirst()
                .orElseThrow(InvalidCredentialsException::new);
    }
}
