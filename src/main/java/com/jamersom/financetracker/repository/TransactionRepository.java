package com.jamersom.financetracker.repository;

import com.jamersom.financetracker.domain.Transaction;
import com.jamersom.financetracker.service.FilterCriteria;

import java.util.List;

public interface TransactionRepository {
  Transaction create(Transaction t);
  List<Transaction> findAll();
  List<Transaction> findByCriteria(FilterCriteria criteria);

  boolean deleteById(long id);
}
