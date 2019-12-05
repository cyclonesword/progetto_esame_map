package com.biblioteca.core;

import java.time.LocalDateTime;

public interface Loan {

    Customer getCustomer();
    int getId();

    LocalDateTime getLoanDate();
    LocalDateTime getExpectedReturnDate();
    LocalDateTime getReturnDate();

    void setReturnDate(LocalDateTime returnDate);
    void confirm();

    class Builder {

        private Customer customer;
        private LocalDateTime loanDate;
        private LocalDateTime expectedReturnDate;

        Builder setCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder setLoanDate(LocalDateTime loanDate) {
            this.loanDate = loanDate;
            return this;
        }

        public Builder setExpectedReturnDate(LocalDateTime expectedReturnDate) {
            this.expectedReturnDate = expectedReturnDate;
            return this;
        }

        public Loan build() {

            if(customer == null)
                throw new IllegalArgumentException("Null customer given in Loan Builder.");

            if(loanDate == null || expectedReturnDate == null) {
                throw new IllegalArgumentException("Loan Date and Expected return date must not be null.");
            }

            return new StandardLoan(customer, loanDate, expectedReturnDate);

        }
    }
}
