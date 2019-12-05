package com.biblioteca.core;

import java.math.BigDecimal;

public interface PaidLoan extends Loan {
    BigDecimal getPrice();
}
