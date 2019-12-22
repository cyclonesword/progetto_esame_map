package com.biblioteca.ui.items;

import com.biblioteca.core.Book;
import com.biblioteca.core.Customer;
import com.biblioteca.core.Loan;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.StringJoiner;


/**
 * This class is used to represent a row in the Table of lent books.
 * Please refer to the {@link Loan} class for the complete documentation
 *
 * =====> Adapter pattern used Here!! <=====
 */
// === !! Adapter pattern used here. === //
public class TableViewLoanRow implements Loan {

    // ====  Nota per il prof. Fici ==== //

    // Il design pattern  Adapter viene qui usato per incapsulare
    // un oggetto di tipo Loan in modo da essere correttamente interpretato dalla TableView di JavaFX.
    // In particolare, per abilitare il dynamic binding dei dati contenuti nella TableView è necessario creare
    // una classe wrapper (o adapter) e incapsulare ogni proprietà della classe di partenza in un oggetto del tipo
    // SimpleStringProperty e simili, in modo che quando una di queste proprietà cambia il suo valore, la TableView
    // aggiorna automaticamente i valori mostrati nella tabella.
    // Infatti, queste classi internamente utilizzano il design pattern Observer per notificare ogni qualvolta la proprietà cambia valore.
    // Per una spiegazione più approfondita sul funzionamento delle TableView di JavaFX, si veda la guida ufficiale al seguente link:
    // https://docs.oracle.com/javafx/2/ui_controls/table-view.htm

    // ====================================

    private Loan loan;

    private SimpleIntegerProperty loanId = new SimpleIntegerProperty();
    private SimpleStringProperty user = new SimpleStringProperty();
    private SimpleStringProperty reservedBook = new SimpleStringProperty();

    private SimpleStringProperty startDate = new SimpleStringProperty();
    private SimpleStringProperty expectedReturnDate = new SimpleStringProperty();
    private SimpleStringProperty status = new SimpleStringProperty();

    public TableViewLoanRow(Loan loan) {
        this.loan = loan;
        loanId.set(getLoanId());
        user.set(getCustomer().getId() + " - " +getCustomer().getFullName());
        reservedBook.set(getBook().getTitle());
        startDate.set(getLoanDate().toString());
        expectedReturnDate.set(getExpectedReturnDate().toString());
        status.set(loan.getStatus());
     //   endDate.set(getReturnDate() != null ? getReturnDate().toString() : "Not Returned");
    }

    public Loan getLoan() {
        return loan;
    }

    @Override
    public Book getBook() {
        return loan.getBook();
    }

    @Override
    public Customer getCustomer() {
        return loan.getCustomer();
    }

    @Override
    public LocalDate getLoanDate() {
        return loan.getLoanDate();
    }

    @Override
    public LocalDate getExpectedReturnDate() {
        return loan.getExpectedReturnDate();
    }

    @Override
    public void setReturnDate(LocalDate returnDate) {
        this.expectedReturnDate.set(returnDate.toString());
        loan.setReturnDate(returnDate);
    }

    @Override
    public void confirm() {
        loan.confirm();
    }

    @Override
    public int getLoanId() {
        return loan.getLoanId();
    }

    @Override
    public void setId(int id) {
        loan.setId(id);
    }

    public String getUser() {
        return user.get();
    }

    public String getReservedBook() {
        return reservedBook.get();
    }

    public String getStartDate() {
        return startDate.get();
    }


    public String getStatus() {
        return status.get();
    }

    @Override
    public void setStatus(String status) {
        loan.setStatus(status);
        this.status.set(status);
    }

//    @Override
//    public void confirm() {
//        loan.confirm();
//    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TableViewLoanRow.class.getSimpleName() + "[", "]")
                .add("loan=" + loan)
                .add("loanId=" + loanId)
                .add("user=" + user)
                .add("reservedBook=" + reservedBook)
                .add("startDate=" + startDate)
                .add("expectedReturnDate=" + expectedReturnDate)
                .add("endDate=" + status)
                .toString();
    }
}
