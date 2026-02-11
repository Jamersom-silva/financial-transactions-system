package com.jamersom.financetracker.ui;

import java.util.Scanner;

public final class ConsoleInput {
  private final Scanner scanner;

  public ConsoleInput(Scanner scanner) {
    this.scanner = scanner;
  }

  public String readLine(String prompt) {
    System.out.print(prompt);
    return scanner.nextLine();
  }

  public int readInt(String prompt) {
    while (true) {
      String s = readLine(prompt);
      try {
        return Integer.parseInt(s.trim());
      } catch (Exception e) {
        System.out.println("Please enter a valid integer.");
      }
    }
  }
}
