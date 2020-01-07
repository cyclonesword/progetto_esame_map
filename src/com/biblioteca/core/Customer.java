package com.biblioteca.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * The Customer class encapsulates information and business logic for managing the customers of the library.
 */
public class Customer implements Comparable<Customer> {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String fiscalCode;
    private String phoneNumber;

    private List<Loan> loans = new ArrayList<>();

    public Customer(int id, String firstName, String lastName, String email, String fiscalCode, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.fiscalCode = fiscalCode;
        this.phoneNumber = phoneNumber;
    }

    public Customer(String firstName, String lastName, String email, String fiscalCode, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.fiscalCode = fiscalCode;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Add a loan to the list of loans of this customer.
      * @param loan The loan to be associated to this customer
     */
    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    /**
     * Returns an unmodifiable collection with the loans of this customer
     * @return The list of loans of this customer
     */
    public List<Loan> getLoans() {
        return Collections.unmodifiableList(loans);
    }

    /**
     * Returns the Id of this customer
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * The first name of the customer
     * @return The first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * The last name of the Customer
     * @return The last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * The email of the customer
     * @return A valid email address of this customer
     */
    public String getEmail() {
        return email;
    }

    /**
     * The fiscal code of the customer. It is 16 characters long.
     * @return The Fiscal code of the customer (16 chars)
     */
    public String getFiscalCode() {
        return fiscalCode;
    }

    /**
     * The telephone number (if any) of this customer
     * @return The telephone number of the customer
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * The First name and last name of the customer
     * @return
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Sets the id for this customer. It must be unique.
     * @param lastId A unique identifier for this customer.
     */
    public void setId(int lastId) {
        this.id = lastId;
    }

    /**
     * Removes a loan from the list of loans of this customer
     * @param loan The loan to be removed
     */
    public void removeLoan(Loan loan) {
        loans.remove(loan);
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
    public int compareTo(Customer o) {
        return Integer.compare(id, o.getId());
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", firstName, lastName, fiscalCode);
    }
}
