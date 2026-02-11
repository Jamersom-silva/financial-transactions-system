package com.jamersom.financetracker.service;

import com.jamersom.financetracker.domain.Summary;
import com.jamersom.financetracker.domain.Transaction;
import com.jamersom.financetracker.domain.TransactionType;
import com.jamersom.financetracker.repository.TransactionRepository;
import com.jamersom.financetracker.util.Validation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public final class TransactionService {

  private final TransactionRepository repo;

  public TransactionService(TransactionRepository repo) {
    this.repo = Objects.requireNonNull(repo, "repo");
  }

  public Transaction add(Transaction t) {
    Validation.requireNonNull(t, "transaction");
    Validation.requireNonBlank(t.getDescription(), "description");
    Validation.requireNonNull(t.getDate(), "date");
    Validation.requireNonNull(t.getType(), "type");
    Validation.requireNonNull(t.getAmount(), "amount");
    Validation.requireNonBlank(t.getCategory(), "category");

    if (t.getAmount().signum() <= 0) {
      throw new IllegalArgumentException("amount must be positive");
    }

    return repo.create(t);
  }

  public List<Transaction> listAll() {
    return repo.findAll();
  }

  public List<Transaction> filter(FilterCriteria criteria) {
    Validation.requireNonNull(criteria, "criteria");

    if (criteria.getMinAmount() != null && criteria.getMinAmount().signum() < 0) {
      throw new IllegalArgumentException("minAmount cannot be negative");
    }
    if (criteria.getMaxAmount() != null && criteria.getMaxAmount().signum() < 0) {
      throw new IllegalArgumentException("maxAmount cannot be negative");
    }
    if (criteria.getMinAmount() != null && criteria.getMaxAmount() != null
            && criteria.getMinAmount().compareTo(criteria.getMaxAmount()) > 0) {
      throw new IllegalArgumentException("minAmount cannot be greater than maxAmount");
    }
    if (criteria.getStartDate() != null && criteria.getEndDate() != null
            && criteria.getStartDate().isAfter(criteria.getEndDate())) {
      throw new IllegalArgumentException("startDate cannot be after endDate");
    }

    return repo.findByCriteria(criteria);
  }

  public boolean deleteById(long id) {
    if (id <= 0) {
      throw new IllegalArgumentException("id inválido");
    }
    return repo.deleteById(id);
  }

  public Summary summary(List<Transaction> transactions) {
    Validation.requireNonNull(transactions, "transactions");

    BigDecimal income = BigDecimal.ZERO;
    BigDecimal expense = BigDecimal.ZERO;

    for (Transaction t : transactions) {
      if (t.getType() == TransactionType.INCOME) {
        income = income.add(t.getAmount());
      } else {
        expense = expense.add(t.getAmount());
      }
    }

    return new Summary(income, expense);
  }

  public Summary summaryAll() {
    return summary(listAll());
  }
}
