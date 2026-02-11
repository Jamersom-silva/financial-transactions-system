package com.jamersom.financetracker.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public final class Transaction {

  private final Long id;
  private final LocalDate date;
  private final String description;
  private final BigDecimal amount;
  private final TransactionType type;
  private final String category;

  public Transaction(
          Long id,
          LocalDate date,
          String description,
          BigDecimal amount,
          TransactionType type,
          String category
  ) {
    this.id = id;
    this.date = Objects.requireNonNull(date);
    this.description = Objects.requireNonNull(description);
    this.amount = Objects.requireNonNull(amount);
    this.type = Objects.requireNonNull(type);
    this.category = Objects.requireNonNull(category);
  }

  public Long getId() { return id; }
  public LocalDate getDate() { return date; }
  public String getDescription() { return description; }
  public BigDecimal getAmount() { return amount; }
  public TransactionType getType() { return type; }
  public String getCategory() { return category; }

  public Transaction withId(Long newId) {
    return new Transaction(newId, date, description, amount, type, category);
  }
}
