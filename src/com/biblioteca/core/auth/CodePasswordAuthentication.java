package com.biblioteca.core.auth;

import com.biblioteca.core.employee.Employee;
import com.biblioteca.datasource.DataSource;
import com.biblioteca.ui.utils.Utils;

/**
 * This implementation of the <code>Authentication</code> interface performs authentication for the code / password authentication strategy. <br>
 * See the {@link Authentication} interface for the complete javadoc
 */
public class CodePasswordAuthentication implements Authentication {

    private int code;
    private String password;

    /**
     *  @param code The employee code (matricola)
     * @param password The password
     */
    public CodePasswordAuthentication(int code, String password) {
        this.code = code;
        this.password = Utils.sha1Digest(password);
    }

    @Override
    public Employee authenticate() throws InvalidCredentialsException {
        var emps = DataSource.getInstance().getEmployees();

        return emps.stream()
                .filter(e -> e.getEmployeeNumber() == code && e.getPassword().equals(password))
                .findFirst()
                .orElseThrow(InvalidCredentialsException::new);
    }
}
