package com.biblioteca.core;

import java.time.LocalDate;

/**
 * The <code>Loan</code> interface is responsible for managing the loan lifecycle of a book.<br>
 */
public interface Loan {

    /**
     *
     * @return The customer that requested the loan
     */
    Customer getCustomer();

    /**
     *
     * @return The unique identifier for this loan
     */
    int getLoanId();

    /**
     * Sets an id for this loan
     * @param id
     */
    void setId(int id);

    /**
     *
     * @return The date at which the loan started.
     */
    LocalDate getLoanDate();

    /**
     *
     * @return The expected return date of the loan
     */
    LocalDate getExpectedReturnDate();

    /**
     *
     * @param returnDate The return date for this loan
     */
    void setReturnDate(LocalDate returnDate);

    /**
     * Assigns a new id to this loan, add it to the loans list of the Customer.
     */
    void confirm();

    /**
     *
     * @return The book associated with this loan
     */
    Book getBook();

    /**
     *
     * @return The loan status. It can be "returned" or "not-returned"
     */
    String getStatus();

    /**
     * Sets the status of the Book
     * @param status The book status. Can be "returned" or "not-returned"
     */
    void setStatus(String status);

    /**
     * The Loan Builder using the standard, well-known design pattern "Builder"<br>
     * See the {@link Loan} interface for a description of the getters and setters.
     */
    class Builder {

        private Customer customer;
        private LocalDate loanDate;
        private LocalDate expectedReturnDate;
        private String status;
        private Book book;
        private int id;

        public Builder setCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder setLoanDate(LocalDate loanDate) {
            this.loanDate = loanDate;
            return this;
        }

        public Builder setExpectedReturnDate(LocalDate expectedReturnDate) {
            this.expectedReturnDate = expectedReturnDate;
            return this;
        }

        public Builder setBook(Book book) {
            this.book = book;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Loan build() {

            if(customer == null || book == null)
                throw new IllegalArgumentException("Null customer or book given in Loan Builder.");

            if(loanDate == null || expectedReturnDate == null) {
                throw new IllegalArgumentException("Loan Date and Expected return date must not be null.");
            }

            StandardLoan standardLoan = new StandardLoan(id, customer, book, loanDate, expectedReturnDate);
            standardLoan.setStatus(status);
            return standardLoan;

        }
    }
}
