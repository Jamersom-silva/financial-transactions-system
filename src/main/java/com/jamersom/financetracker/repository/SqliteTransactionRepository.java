package com.jamersom.financetracker.repository;

import com.jamersom.financetracker.domain.Transaction;
import com.jamersom.financetracker.domain.TransactionType;
import com.jamersom.financetracker.service.FilterCriteria;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class SqliteTransactionRepository implements TransactionRepository {

  private final Database db;

  public SqliteTransactionRepository(Database db) {
    this.db = Objects.requireNonNull(db, "db");
  }

  @Override
  public Transaction create(Transaction t) {
    String sql = """
        INSERT INTO transactions(date, description, amount, type, category)
        VALUES(?,?,?,?,?)
        """;

    try (Connection c = db.openConnection();
         PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      ps.setString(1, t.getDate().toString());               // ISO no banco
      ps.setString(2, t.getDescription());
      ps.setString(3, t.getAmount().toPlainString());        // BigDecimal como string
      ps.setString(4, t.getType().name());
      ps.setString(5, t.getCategory());

      ps.executeUpdate();

      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (rs.next()) {
          return t.withId(rs.getLong(1));
        }
      }

      throw new IllegalStateException("ID não gerado");

    } catch (Exception e) {
      throw new IllegalStateException("Erro ao criar transação", e);
    }
  }

  @Override
  public List<Transaction> findAll() {
    String sql = """
        SELECT id, date, description, amount, type, category
        FROM transactions
        ORDER BY date DESC, id DESC
        """;

    try (Connection c = db.openConnection();
         PreparedStatement ps = c.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

      return readAll(rs);

    } catch (Exception e) {
      throw new IllegalStateException("Erro ao listar transações", e);
    }
  }

  @Override
  public List<Transaction> findByCriteria(FilterCriteria criteria) {
    Objects.requireNonNull(criteria, "criteria");

    StringBuilder sb = new StringBuilder("""
        SELECT id, date, description, amount, type, category
        FROM transactions
        WHERE 1=1
        """);

    List<Object> params = new ArrayList<>();

    if (criteria.getStartDate() != null) {
      sb.append(" AND date >= ?");
      params.add(criteria.getStartDate().toString());
    }
    if (criteria.getEndDate() != null) {
      sb.append(" AND date <= ?");
      params.add(criteria.getEndDate().toString());
    }
    if (criteria.getMinAmount() != null) {
      sb.append(" AND CAST(amount AS REAL) >= CAST(? AS REAL)");
      params.add(criteria.getMinAmount().toPlainString());
    }
    if (criteria.getMaxAmount() != null) {
      sb.append(" AND CAST(amount AS REAL) <= CAST(? AS REAL)");
      params.add(criteria.getMaxAmount().toPlainString());
    }
    if (criteria.getType() != null) {
      sb.append(" AND type = ?");
      params.add(criteria.getType().name());
    }

    // NOVOS FILTROS (V1 melhorada)
    if (criteria.getCategory() != null && !criteria.getCategory().isBlank()) {
      sb.append(" AND lower(category) LIKE lower(?)");
      params.add("%" + criteria.getCategory().trim() + "%");
    }
    if (criteria.getKeyword() != null && !criteria.getKeyword().isBlank()) {
      sb.append(" AND lower(description) LIKE lower(?)");
      params.add("%" + criteria.getKeyword().trim() + "%");
    }

    sb.append(" ORDER BY date DESC, id DESC");

    try (Connection c = db.openConnection();
         PreparedStatement ps = c.prepareStatement(sb.toString())) {

      for (int i = 0; i < params.size(); i++) {
        ps.setObject(i + 1, params.get(i));
      }

      try (ResultSet rs = ps.executeQuery()) {
        return readAll(rs);
      }

    } catch (Exception e) {
      throw new IllegalStateException("Erro ao filtrar transações", e);
    }
  }

  @Override
  public boolean deleteById(long id) {
    String sql = "DELETE FROM transactions WHERE id = ?";

    try (Connection c = db.openConnection();
         PreparedStatement ps = c.prepareStatement(sql)) {

      ps.setLong(1, id);
      int rows = ps.executeUpdate();
      return rows > 0;

    } catch (Exception e) {
      throw new IllegalStateException("Erro ao excluir transação id=" + id, e);
    }
  }

  private static List<Transaction> readAll(ResultSet rs) throws Exception {
    List<Transaction> list = new ArrayList<>();

    while (rs.next()) {
      list.add(
              new Transaction(
                      rs.getLong("id"),
                      LocalDate.parse(rs.getString("date")),              // ISO -> LocalDate
                      rs.getString("description"),
                      new BigDecimal(rs.getString("amount")),
                      TransactionType.valueOf(rs.getString("type")),
                      rs.getString("category")
              )
      );
    }

    return list;
  }
}
