package com.biblioteca.core;

import java.awt.print.Book;
import java.util.List;

public class Library {

    private String name;
    private String address;

    private List<Employee> employees;

    private static Library instance;

    public static Library getInstance() {
        if(instance == null)
            instance = new Library();
        return instance;
    }

    public void newLoan(Book book, Authorization authorization) {

    }

}
