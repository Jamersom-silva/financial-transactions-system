package com.jamersom.financetracker;

import com.jamersom.financetracker.repository.Database;
import com.jamersom.financetracker.repository.SqliteTransactionRepository;
import com.jamersom.financetracker.service.TransactionService;
import com.jamersom.financetracker.ui.ConsoleMenu;

public final class App {

  public static void main(String[] args) {

    Database db = Database.defaultFileDb();
    db.initialize();

    TransactionService service =
            new TransactionService(new SqliteTransactionRepository(db));

    ConsoleMenu menu = new ConsoleMenu(service);
    menu.start();
  }
}
