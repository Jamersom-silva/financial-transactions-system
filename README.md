# 💰 Finance Tracker — Sistema de Transações Financeiras

## 📌 Sobre o Projeto

O **Finance Tracker** é uma aplicação de console desenvolvida em **Java 17 + Maven + SQLite**, criada com o objetivo de simular um sistema real de controle financeiro pessoal.

O projeto foi estruturado seguindo uma arquitetura em camadas (Domain, Service, Repository e UI), aplicando boas práticas de organização, separação de responsabilidades e validações de regra de negócio.

Este projeto representa a **Versão 1 (V1)** de um sistema evolutivo, pensado para crescer progressivamente em complexidade e recursos.

---

## 🎯 Proposta da Aplicação

A proposta do sistema é permitir que o usuário:

* Registre entradas e saídas financeiras
* Filtre transações por múltiplos critérios
* Visualize um resumo financeiro (entradas, saídas e saldo)
* Persista dados em banco SQLite

O foco da V1 é fornecer uma base sólida e organizada, pronta para evolução futura.

---

## 🧱 Arquitetura do Projeto

```
src/main/java/com/jamersom/financetracker
├── App.java
├── domain/        → Entidades e enums (Transaction, Summary, TransactionType)
├── repository/    → Persistência SQLite via JDBC
├── service/       → Regras de negócio e validações
├── ui/            → Interface de console (menu e interação)
└── util/          → Utilitários (validação, datas, dinheiro)
```

### 🔹 Camadas

* **Domain** → Modelos imutáveis do sistema
* **Repository** → Comunicação com banco via JDBC
* **Service** → Centralização das regras de negócio
* **UI** → Interface interativa via terminal
* **Util** → Formatação e validações auxiliares

---

## 🗄 Banco de Dados

* Banco: **SQLite**
* Arquivo gerado automaticamente em `/data/finance-tracker.db`
* Datas armazenadas em formato ISO (yyyy-MM-dd)
* Valores armazenados com `BigDecimal`

---

## 🚀 Funcionalidades — Versão 1

✔ Registrar transações (ENTRADA / SAÍDA)
✔ Listar todas as transações
✔ Filtrar por:

* Data inicial e final
* Valor mínimo e máximo
* Tipo (entrada/saída)
* Categoria
* Palavra-chave na descrição
  ✔ Excluir transação por ID
  ✔ Visualizar sumário financeiro (entradas, saídas e saldo)
  ✔ Formatação de data no padrão brasileiro (dd-MM-yyyy)
  ✔ Formatação de valores em Real (R$ 1.000,00)
  ✔ Testes unitários com JUnit 5

---

## ▶ Como Executar

### Rodar testes

```
mvn clean test
```

### Executar aplicação

```
mvn exec:java
```

---

## 🧠 Conceitos Aplicados

* Java 17
* Maven
* JDBC
* SQLite
* Arquitetura em camadas
* Builder Pattern (FilterCriteria)
* Imutabilidade
* BigDecimal para valores monetários
* Validação centralizada
* Testes com JUnit 5

---

## 🔮 Próximas Versões

### 🟡 Versão 1.1

* Atualizar (editar) transação por ID
* Exportação para CSV
* Relatório mensal agrupado por mês

### 🟠 Versão 2

* CRUD completo de categorias (entidade própria)
* Relatórios estatísticos
* Dashboard gráfico
* API REST com Spring Boot
* Autenticação de usuários

### 🔵 Versão 3

* Interface Web
* Deploy em ambiente cloud
* Controle multiusuário

---

## 📈 Objetivo Técnico

Este projeto foi desenvolvido como parte da evolução técnica em Java, com foco em:

* Aplicar arquitetura limpa em aplicações pequenas
* Trabalhar com banco relacional real
* Simular cenários de sistemas financeiros
* Evoluir um sistema gradualmente como ocorre em projetos reais

---

## 👨‍💻 Autor

Jamersom Silva
Desenvolvedor Java em formação
Projeto desenvolvido para portfólio e evolução técnica
