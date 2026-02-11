package com.jamersom.financetracker;

import com.jamersom.financetracker.domain.TransactionType;
import com.jamersom.financetracker.service.FilterCriteria;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

final class FilterCriteriaTest {

  @Test
  void builderStoresFields() {
    FilterCriteria c = FilterCriteria.builder()
        .startDate(LocalDate.of(2026, 1, 1))
        .endDate(LocalDate.of(2026, 12, 31))
        .minAmount(new BigDecimal("10.00"))
        .maxAmount(new BigDecimal("50.00"))
        .type(TransactionType.EXPENSE)
        .build();

    assertEquals(LocalDate.of(2026, 1, 1), c.getStartDate());
    assertEquals(LocalDate.of(2026, 12, 31), c.getEndDate());
    assertEquals(new BigDecimal("10.00"), c.getMinAmount());
    assertEquals(new BigDecimal("50.00"), c.getMaxAmount());
    assertEquals(TransactionType.EXPENSE, c.getType());
  }
}
