package com.biblioteca.core;

public interface Employee {

    String getName();
    Authorization getAuthorization();
    boolean canPerform(Command command);

}
