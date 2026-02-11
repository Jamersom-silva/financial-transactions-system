package com.jamersom.financetracker;

import com.jamersom.financetracker.domain.Summary;
import com.jamersom.financetracker.domain.Transaction;
import com.jamersom.financetracker.domain.TransactionType;
import com.jamersom.financetracker.repository.TransactionRepository;
import com.jamersom.financetracker.service.FilterCriteria;
import com.jamersom.financetracker.service.TransactionService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

final class TransactionServiceTest {

  @Test
  void summaryComputesIncomeExpenseAndBalance() {
    TransactionRepository fake = new InMemoryRepo();
    TransactionService service = new TransactionService(fake);

    service.add(new Transaction(null, LocalDate.of(2026, 2, 1), "Salário", new BigDecimal("1000.00"), TransactionType.INCOME, "Renda"));
    service.add(new Transaction(null, LocalDate.of(2026, 2, 2), "Aluguel", new BigDecimal("400.00"), TransactionType.EXPENSE, "Moradia"));
    service.add(new Transaction(null, LocalDate.of(2026, 2, 3), "Mercado", new BigDecimal("150.00"), TransactionType.EXPENSE, "Alimentação"));

    Summary s = service.summaryAll();
    assertEquals(new BigDecimal("1000.00"), s.getTotalIncome());
    assertEquals(new BigDecimal("550.00"), s.getTotalExpense());
    assertEquals(new BigDecimal("450.00"), s.getBalance());
  }

  private static final class InMemoryRepo implements TransactionRepository {
    private long seq = 1;
    private final List<Transaction> list = new ArrayList<>();

    @Override
    public Transaction create(Transaction t) {
      Transaction saved = t.withId(seq++);
      list.add(saved);
      return saved;
    }

    @Override
    public List<Transaction> findAll() {
      return List.copyOf(list);
    }

    @Override
    public List<Transaction> findByCriteria(FilterCriteria criteria) {
      return findAll();
    }
  }
}
