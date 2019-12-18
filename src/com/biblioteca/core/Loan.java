package com.biblioteca.core;

import java.time.LocalDate;

public interface Loan {

    Customer getCustomer();
    int getLoanId();
    void setId(int id);

    LocalDate getLoanDate();
    LocalDate getExpectedReturnDate();
    LocalDate getReturnDate();

    void setReturnDate(LocalDate returnDate);
    void confirm();

    Book getBook();

    class Builder {

        private Customer customer;
        private LocalDate loanDate;
        private LocalDate expectedReturnDate;
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

        public Loan build() {

            if(customer == null || book == null)
                throw new IllegalArgumentException("Null customer or book given in Loan Builder.");

            if(loanDate == null || expectedReturnDate == null) {
                throw new IllegalArgumentException("Loan Date and Expected return date must not be null.");
            }

            return new StandardLoan(id,customer, book, loanDate, expectedReturnDate);

        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }
    }
}
