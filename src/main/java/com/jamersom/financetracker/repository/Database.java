package com.jamersom.financetracker.repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public final class Database {

  private final String jdbcUrl;

  private Database(String jdbcUrl) {
    this.jdbcUrl = jdbcUrl;
  }

  public static Database defaultFileDb() {
    return new Database("jdbc:sqlite:data/finance-tracker.db");
  }

  public Connection openConnection() {
    try {
      return DriverManager.getConnection(jdbcUrl);
    } catch (Exception e) {
      throw new RuntimeException("Erro ao conectar no banco", e);
    }
  }

  public void initialize() {

    try {
      java.nio.file.Files.createDirectories(
              java.nio.file.Paths.get("data")
      );
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    String sql = readResource("/schema.sql");

    try (Connection c = openConnection();
         Statement st = c.createStatement()) {

      for (String piece : sql.split(";")) {
        if (!piece.trim().isEmpty()) {
          st.executeUpdate(piece);
        }
      }

    } catch (Exception e) {
      throw new RuntimeException("Erro ao criar schema", e);
    }
  }

  private String readResource(String path) {
    try (BufferedReader br = new BufferedReader(
            new InputStreamReader(
                    Database.class.getResourceAsStream(path),
                    StandardCharsets.UTF_8))) {

      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line).append("\n");
      }
      return sb.toString();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
