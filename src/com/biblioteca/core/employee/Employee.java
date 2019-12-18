package com.biblioteca.core.employee;

public interface Employee {

    String getEmployeeNumber();

    String getPassword();

    String getFirstName();

    String getLastName();

    String getEmail();

    boolean canPerform(Action action);

    enum Action {
        LOAN_BOOK, MODIFY_BOOK, ADD_USER, DELETE_ADMIN, DELETE_BOOK
    }

    static Employee newInstance(String number, String password, String firstName, String lastName, String email) {
        return new Administrator(number, password, firstName, lastName, email);
    }

    public class Builder {
        private String number;
        private String password;
        private String firstName;
        private String lastName;
        private String email;

        public Builder setNumber(String number) {
            this.number = number;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Employee build() {
            return Employee.newInstance(number, password, firstName, lastName, email);
        }
    }
}
