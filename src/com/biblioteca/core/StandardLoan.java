package com.biblioteca.core;

import com.biblioteca.ui.utils.LoanPDFGenerator;
import com.biblioteca.ui.utils.PDFGenerator;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;


/**
 * The default implementation for the {@link Loan} interface.
 */
public class StandardLoan implements Loan {

    private int id;
    private Customer customer;
    private Book book;

    private LocalDate loanDate;
    private LocalDate expectedReturnDate;
    private String status;

    // Use the default PDFGenerator implementation if no other generator will be assigned (with the setter method).
    private PDFGenerator pdfGenerator = new LoanPDFGenerator(this);

    public StandardLoan(int id, Customer customer, Book book, LocalDate loanDate, LocalDate expectedReturnDate) {
        this.id = id;
        this.customer = customer;
        this.book = book;
        this.loanDate = loanDate;
        this.expectedReturnDate = expectedReturnDate;
    }


    @Override
    public Book getBook() {
        return book;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public int getLoanId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public LocalDate getLoanDate() {
        return loanDate;
    }

    @Override
    public LocalDate getExpectedReturnDate() {
        return expectedReturnDate;
    }

    @Override
    public void setReturnDate(LocalDate returnDate) {
        this.expectedReturnDate = returnDate;
    }

    @Override
    public String toString() {
        return id + " - " + book.toString() + " - " + customer;
    }

    public void setStatus(String status) {
        if (!status.equals(Loan.STATUS_NOT_RETURNED) && !status.equals(Loan.STATUS_RETURNED))
            throw new IllegalArgumentException("Status can be only Loan.STATUS_NOT_RETURNED or Loan.STATUS_RETURNED .");

        this.status = status;
    }

    @Override
    public void setAsReturned() {
        if (status.equals(STATUS_NOT_RETURNED)) {
            setStatus(Loan.STATUS_RETURNED);
            setReturnDate(LocalDate.now());
            getBook().incrementQuantityBy(1);
            getCustomer().removeLoan(this);
        }
    }

    @Override
    public File generatePdfFile() throws IOException {
        return pdfGenerator.generatePdfFile();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Loan)) return false;
        Loan that = (Loan) o;
        return id == that.getLoanId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setPdfGenerator(PDFGenerator pdfGenerator) {
        this.pdfGenerator = pdfGenerator;
    }
}
