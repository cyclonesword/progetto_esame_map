package com.biblioteca.core;

import java.util.List;

public class Customer {

    private List<Loan> loans;
    private String name;
    private String email;
    private String phoneNumber;

    private boolean isPremiumUser = false;

    boolean isPremiumUser() {
        return isPremiumUser;
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }
}
