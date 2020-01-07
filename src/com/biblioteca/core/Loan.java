package com.biblioteca.core;

import com.biblioteca.ui.utils.LoanPDFGenerator;
import com.biblioteca.ui.utils.PDFGenerator;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

/**
 * The <code>Loan</code> interface is responsible for managing the loan lifecycle of a book.<br>
 */
public interface Loan {

    String STATUS_NOT_RETURNED = "not-returned";
    String STATUS_RETURNED = "returned";

    /**
     * The Customer associated with this loan
     * @return The customer that requested the loan
     */
    Customer getCustomer();

    /**
     * The identifier of this loan
     * @return The unique identifier for this loan
     */
    int getLoanId();

    /**
     * Sets an id for this loan
     * @param id
     */
    void setId(int id);

    /**
     * The loan start date
     * @return The date at which the loan started.
     */
    LocalDate getLoanDate();

    /**
     * The loan expected return date
     * @return The expected return date of the loan
     */
    LocalDate getExpectedReturnDate();

    /**
     * Sets the return date of this book
     * @param returnDate The return date for this loan
     */
    void setReturnDate(LocalDate returnDate);

    /**
     * The book associated with this loan
     * @return The book associated with this loan
     */
    Book getBook();

    /**
     * Gets the status of this loan
     * @return The loan status. It can be {@link Loan#STATUS_RETURNED} or {@link Loan#STATUS_NOT_RETURNED}
     */
    String getStatus();

    /**
     * Sets the status of the Book
     * @param status The book status. It can be {@link Loan#STATUS_RETURNED} or {@link Loan#STATUS_NOT_RETURNED}
     */
    void setStatus(String status);


  //  File generatePdf() throws IOException;

    /**
     * Set this loan as completed, updating the return date and incrementing the book quantity by 1.
     */
    void setAsReturned();

    /**
     * Creates a new PDF File that describes the loan details and saves it in the default pdf files directory.
     * @return The newly created PDF file
     * @throws IOException If some error occurred during the file creation.
     */
    File generatePdfFile() throws IOException;

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
            PDFGenerator pdfGenerator = new LoanPDFGenerator(standardLoan);
            standardLoan.setPdfGenerator(pdfGenerator);
            return standardLoan;

        }
    }
}
