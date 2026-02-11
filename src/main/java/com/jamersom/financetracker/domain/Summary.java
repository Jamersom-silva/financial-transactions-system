package com.jamersom.financetracker.domain;

import java.math.BigDecimal;

public final class Summary {

  private final BigDecimal totalIncome;
  private final BigDecimal totalExpense;
  private final BigDecimal balance;

  public Summary(BigDecimal totalIncome, BigDecimal totalExpense) {
    this.totalIncome = totalIncome;
    this.totalExpense = totalExpense;
    this.balance = totalIncome.subtract(totalExpense);
  }

  public BigDecimal getTotalIncome() { return totalIncome; }
  public BigDecimal getTotalExpense() { return totalExpense; }
  public BigDecimal getBalance() { return balance; }
}
