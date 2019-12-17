package com.biblioteca.core.employee;

public interface Employee {

    enum Level {
        ADMIN, MANAGER, ASSISTANT
    }

    String getEmployeeNumber();
    String getPassword();
    String getFirstName();
    String getLastName();
    String getEmail();

    boolean canPerform(Action action);

    enum Action {
        LOAN_BOOK, MODIFY_BOOK, ADD_USER, DELETE_ADMIN, DELETE_BOOK
    }


}
