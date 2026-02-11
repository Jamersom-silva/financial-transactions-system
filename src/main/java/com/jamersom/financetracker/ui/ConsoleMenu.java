package com.jamersom.financetracker.ui;

import com.jamersom.financetracker.domain.Summary;
import com.jamersom.financetracker.domain.Transaction;
import com.jamersom.financetracker.domain.TransactionType;
import com.jamersom.financetracker.service.FilterCriteria;
import com.jamersom.financetracker.service.TransactionService;
import com.jamersom.financetracker.util.DateUtils;
import com.jamersom.financetracker.util.MoneyUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public final class ConsoleMenu {
  private final TransactionService service;

  public ConsoleMenu(TransactionService service) {
    this.service = service;
  }

  public void start() {
    Locale.setDefault(Locale.forLanguageTag("pt-BR"));
    try (Scanner scanner = new Scanner(System.in)) {
      ConsoleInput in = new ConsoleInput(scanner);
      boolean running = true;

      while (running) {
        printHeader();
        printOptions();
        int opt = in.readInt("Escolha uma opção: ");
        System.out.println();

        switch (opt) {
          case 1 -> createTransaction(in);
          case 2 -> listAll();
          case 3 -> filter(in);
          case 4 -> printSummary(service.listAll());
          case 0 -> running = false;
          default -> System.out.println("Opção inválida.");
        }

        if (running) {
          System.out.println();
          in.readLine("Enter para continuar...");
          System.out.println();
        }
      }
    }
    System.out.println("Até mais!");
  }

  private static void printHeader() {
    System.out.println("==============================================");
    System.out.println(" Finance Tracker — Transações Financeiras");
    System.out.println("==============================================");
  }

  private static void printOptions() {
    System.out.println("1) Registrar transação");
    System.out.println("2) Listar transações");
    System.out.println("3) Filtrar transações");
    System.out.println("4) Ver sumário (entradas/saídas/saldo)");
    System.out.println("0) Sair");
    System.out.println("----------------------------------------------");
  }

  private void createTransaction(ConsoleInput in) {
    try {
      LocalDate date = DateUtils.parseBrDate(in.readLine("Data (dd-MM-yyyy): "));
      String desc = in.readLine("Descrição: ").trim();
      BigDecimal amount = MoneyUtils.parseMoney(in.readLine("Valor (ex: 10.50): "));
      TransactionType type = readType(in);
      String category = in.readLine("Categoria: ").trim();

      Transaction created = service.add(new Transaction(null, date, desc, amount.abs(), type, category));
      System.out.println("✅ Transação criada com ID: " + created.getId());
    } catch (Exception e) {
      System.out.println("❌ Erro: " + e.getMessage());
    }
  }

  private static TransactionType readType(ConsoleInput in) {
    while (true) {
      String t = in.readLine("Tipo (1=ENTRADA, 2=SAÍDA): ").trim();
      if (t.equals("1")) return TransactionType.INCOME;
      if (t.equals("2")) return TransactionType.EXPENSE;
      System.out.println("Tipo inválido.");
    }
  }

  private void listAll() {
    List<Transaction> list = service.listAll();
    printTransactions(list);
  }

  private void filter(ConsoleInput in) {
    try {
      LocalDate start = readOptionalDate(in, "Data inicial (dd-MM-yyyy) [vazio = ignorar]: ");
      LocalDate end = readOptionalDate(in, "Data final   (dd-MM-yyyy) [vazio = ignorar]: ");
      BigDecimal min = readOptionalMoney(in, "Valor mínimo [vazio = ignorar]: ");
      BigDecimal max = readOptionalMoney(in, "Valor máximo [vazio = ignorar]: ");
      TransactionType type = readOptionalType(in);

      FilterCriteria criteria = FilterCriteria.builder()
              .startDate(start)
              .endDate(end)
              .minAmount(min)
              .maxAmount(max)
              .type(type)
              .build();

      List<Transaction> filtered = service.filter(criteria);
      printTransactions(filtered);
      System.out.println();
      printSummary(filtered);
    } catch (Exception e) {
      System.out.println("❌ Erro: " + e.getMessage());
    }
  }

  private static LocalDate readOptionalDate(ConsoleInput in, String prompt) {
    String s = in.readLine(prompt).trim();
    if (s.isEmpty()) return null;
    return DateUtils.parseBrDate(s);
  }

  private static BigDecimal readOptionalMoney(ConsoleInput in, String prompt) {
    String s = in.readLine(prompt).trim();
    if (s.isEmpty()) return null;
    return MoneyUtils.parseMoney(s).abs();
  }

  private static TransactionType readOptionalType(ConsoleInput in) {
    while (true) {
      String s = in.readLine("Tipo (1=ENTRADA, 2=SAÍDA, vazio=ignorar): ").trim();
      if (s.isEmpty()) return null;
      if (s.equals("1")) return TransactionType.INCOME;
      if (s.equals("2")) return TransactionType.EXPENSE;
      System.out.println("Tipo inválido.");
    }
  }

  private void printSummary(List<Transaction> list) {
    Summary s = service.summary(list);
    System.out.println("----------- SUMÁRIO -----------");
    System.out.println("Entradas: " + s.getTotalIncome());
    System.out.println("Saídas:   " + s.getTotalExpense());
    System.out.println("Saldo:    " + s.getBalance());
    System.out.println("-------------------------------");
  }

  private static void printTransactions(List<Transaction> list) {
    if (list.isEmpty()) {
      System.out.println("(nenhuma transação encontrada)");
      return;
    }
    System.out.println("ID | Data       | Tipo    | Valor   | Categoria | Descrição");
    System.out.println("-----------------------------------------------------------");
    for (Transaction t : list) {
      String tipo = (t.getType() == TransactionType.INCOME) ? "ENTR" : "SAÍD";
      System.out.printf("%-2s | %-10s | %-7s | %-7s | %-9s | %s%n",
              t.getId(),
              DateUtils.formatBr(t.getDate()),
              tipo,
              t.getAmount(),
              truncate(t.getCategory(), 9),
              t.getDescription());
    }
  }

  private static String truncate(String s, int max) {
    if (s == null) return "";
    if (s.length() <= max) return s;
    return s.substring(0, Math.max(0, max - 1)) + "…";
  }
}
