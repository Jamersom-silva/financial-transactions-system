package com.jamersom.financetracker.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public final class MoneyUtils {

  private MoneyUtils() {}

  public static BigDecimal parseMoney(String input) {
    try {
      String raw = input.trim();

      // remove espaços
      raw = raw.replace(" ", "");

      // remove separador de milhar (.)
      raw = raw.replace(".", "");

      // troca decimal , por .
      raw = raw.replace(",", ".");

      BigDecimal v = new BigDecimal(raw);

      // força 2 casas (financeiro)
      return v.setScale(2, RoundingMode.HALF_UP);
    } catch (Exception e) {
      throw new IllegalArgumentException("Valor inválido. Exemplos: 10,50  |  1000  |  1.000,50");
    }
  }

  public static String formatBRL(BigDecimal value) {
    NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    return nf.format(value);
  }
}
