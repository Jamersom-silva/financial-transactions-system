package com.jamersom.financetracker.util;

import java.util.Objects;

public final class Validation {

  private Validation() {}

  public static void requireNonBlank(String value, String fieldName) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(fieldName + " cannot be blank");
    }
  }

  public static <T> T requireNonNull(T value, String fieldName) {
    return Objects.requireNonNull(value, fieldName + " cannot be null");
  }
}
