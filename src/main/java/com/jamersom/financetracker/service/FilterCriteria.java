package com.jamersom.financetracker.service;

import com.jamersom.financetracker.domain.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class FilterCriteria {
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final BigDecimal minAmount;
  private final BigDecimal maxAmount;
  private final TransactionType type;

  private final String category; // novo
  private final String keyword;  // novo (busca em descrição)

  private FilterCriteria(Builder b) {
    this.startDate = b.startDate;
    this.endDate = b.endDate;
    this.minAmount = b.minAmount;
    this.maxAmount = b.maxAmount;
    this.type = b.type;
    this.category = b.category;
    this.keyword = b.keyword;
  }

  public LocalDate getStartDate() { return startDate; }
  public LocalDate getEndDate() { return endDate; }
  public BigDecimal getMinAmount() { return minAmount; }
  public BigDecimal getMaxAmount() { return maxAmount; }
  public TransactionType getType() { return type; }

  public String getCategory() { return category; }
  public String getKeyword() { return keyword; }

  public static Builder builder() { return new Builder(); }

  public static final class Builder {
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private TransactionType type;

    private String category;
    private String keyword;

    public Builder startDate(LocalDate v) { this.startDate = v; return this; }
    public Builder endDate(LocalDate v) { this.endDate = v; return this; }
    public Builder minAmount(BigDecimal v) { this.minAmount = v; return this; }
    public Builder maxAmount(BigDecimal v) { this.maxAmount = v; return this; }
    public Builder type(TransactionType v) { this.type = v; return this; }

    public Builder category(String v) { this.category = v; return this; }
    public Builder keyword(String v) { this.keyword = v; return this; }

    public FilterCriteria build() { return new FilterCriteria(this); }
  }
}
