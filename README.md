# 💰 Finance Tracker — Financial Transaction Management System

## 📌 About the Project

**Finance Tracker** is a console-based application built with **Java 17 + Maven + SQLite**, designed to simulate a real-world personal financial management system.

The project follows a layered architecture (Domain, Service, Repository, and UI), applying best practices such as separation of concerns, centralized business rules, and validation logic.

This project represents **Version 1 (V1)** of an evolving system, structured to grow progressively in complexity and features.

---

## 🎯 Application Proposal

The system allows users to:

* Register income and expense transactions
* Filter transactions using multiple criteria
* View financial summaries (income, expenses, and balance)
* Persist data using SQLite database

The focus of V1 is to provide a solid and well-organized foundation, ready for future evolution.

---

## 🧱 Project Architecture

```
src/main/java/com/jamersom/financetracker
├── App.java
├── domain/        → Entities and enums (Transaction, Summary, TransactionType)
├── repository/    → SQLite persistence via JDBC
├── service/       → Business rules and validations
├── ui/            → Console interface (menu and interaction)
└── util/          → Utilities (validation, dates, money)
```

### 🔹 Layers

* **Domain** → Immutable system models
* **Repository** → Database communication via JDBC
* **Service** → Centralized business rules
* **UI** → Interactive terminal interface
* **Util** → Formatting and helper validations

---

## 🗄 Database

* Database: **SQLite**
* File automatically generated at `/data/finance-tracker.db`
* Dates stored in ISO format (yyyy-MM-dd)
* Monetary values stored using `BigDecimal`

---

## 🚀 Features — Version 1

✔ Register transactions (INCOME / EXPENSE)
✔ List all transactions
✔ Filter by:

* Start and end date
* Minimum and maximum amount
* Type (income/expense)
* Category
* Keyword in description
  ✔ Delete transaction by ID
  ✔ View financial summary (income, expenses, balance)
  ✔ Brazilian date format display (dd-MM-yyyy)
  ✔ Brazilian currency formatting (R$ 1,000.00 equivalent formatting)
  ✔ Unit tests with JUnit 5

---

## ▶ How to Run

### Run tests

```
mvn clean test
```

### Run application

```
mvn exec:java
```

---

## 🧠 Applied Concepts

* Java 17
* Maven
* JDBC
* SQLite
* Layered architecture
* Builder Pattern (FilterCriteria)
* Immutability
* BigDecimal for monetary precision
* Centralized validation
* Unit testing with JUnit 5

---

## 🔮 Upcoming Versions

### 🟡 Version 1.1

* Update (edit) transaction by ID
* CSV export
* Monthly report grouped by month

### 🟠 Version 2

* Full CRUD for categories (separate entity)
* Statistical reports
* Graphical dashboard
* REST API with Spring Boot
* User authentication

### 🔵 Version 3

* Web interface
* Cloud deployment
* Multi-user support

---

## 📈 Technical Objective

This project was developed as part of a Java technical growth journey, focusing on:

* Applying clean architecture principles in small applications
* Working with a real relational database
* Simulating real-world financial system scenarios
* Evolving a system progressively as happens in real projects

---

##
