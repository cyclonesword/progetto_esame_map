package com.biblioteca.core;

import java.util.List;
import java.util.Objects;

public class Customer {

    private List<Loan> loans;
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String fiscalCode;
    private String phoneNumber;

    private boolean isPremiumUser = false;

    public Customer(int id, String firstName, String lastName, String email, String fiscalCode, String phoneNumber, boolean isPremiumUser) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.fiscalCode = fiscalCode;
        this.phoneNumber = phoneNumber;
        this.isPremiumUser = isPremiumUser;
    }

    public Customer(String firstName, String lastName, String email, String fiscalCode, String phoneNumber, boolean isPremiumUser) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.fiscalCode = fiscalCode;
        this.phoneNumber = phoneNumber;
        this.isPremiumUser = isPremiumUser;
    }

    boolean isPremiumUser() {
        return isPremiumUser;
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return id == customer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", firstName, lastName, fiscalCode);
    }
}
