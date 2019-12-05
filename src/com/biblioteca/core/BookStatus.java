package com.biblioteca.core;

import java.util.Calendar;

public class BookStatus {

    private Calendar loanDate;
    private Calendar returnDate;

    public static final BookStatus defaultStatus = new BookStatus(null,null);

    public BookStatus(Calendar loanDate, Calendar returnDate) {
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    boolean isAvailable() {
        return loanDate == null;
    }

    boolean hasBeenLent() {
        return loanDate != null;
    }

    public Calendar getLoanDate() {
        return loanDate;
    }

    public Calendar getReturnDate() {
        return returnDate;
    }

}
