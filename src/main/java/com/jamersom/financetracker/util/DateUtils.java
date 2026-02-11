package com.jamersom.financetracker.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateUtils {

  private DateUtils() {}

  // Formato tradicional brasileiro
  private static final DateTimeFormatter BR =
          DateTimeFormatter.ofPattern("dd-MM-yyyy");

  // Usado para armazenar no banco (ISO padrão)
  private static final DateTimeFormatter ISO =
          DateTimeFormatter.ISO_LOCAL_DATE;

  public static LocalDate parseBrDate(String input) {
    try {
      return LocalDate.parse(input.trim(), BR);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException(
              "Data inválida. Use o formato dd-MM-yyyy (ex: 11-02-2026)"
      );
    }
  }

  public static String formatBr(LocalDate date) {
    return date.format(BR);
  }

  public static String formatIso(LocalDate date) {
    return date.format(ISO);
  }
}
