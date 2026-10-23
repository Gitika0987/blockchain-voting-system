# Blockchain Voting System

A Java Swing desktop application prototype that demonstrates voter registration, voter sign-in, candidate selection, vote recording, and a basic hash-linked block model. SQLite is used for local persistence through JDBC.

> **Academic/demo project only.** This is not suitable for real elections or storing real voter identity information. The current implementation has security and integrity limitations that should be addressed before any real-world use.

## Features

- Desktop interface built with Java Swing
- Voter registration and sign-in flow
- SQLite database initialized automatically on launch
- Candidate list and vote-count dashboard
- SHA-256 hashes and previous-hash links for a basic blockchain demonstration
- JDBC data access classes separated from UI and model classes

## Tech stack

- Java
- Java Swing / AWT
- SQLite
- JDBC
- SHA-256 (`java.security.MessageDigest`)
- Maven for dependency management

## Prerequisites

- JDK 17 or newer
- Maven 3.8 or newer

## Run locally

From the repository root, run:

```bash
mvn clean compile
mvn exec:java
```

The application creates `blockchain_votes.db` in the working directory when it starts. The database file is intentionally excluded from version control; each developer should use a fresh local database.

## Project structure

```text
src/main/java/
├── Main.java
├── dao/       # Database access classes
├── db/        # SQLite connection and schema initialization
├── model/     # Candidate and blockchain model classes
└── ui/        # Swing screens and UI utilities
```

## Important security and integrity limitations

This project is a learning prototype, not a production voting system. In particular:

- Do not enter real Aadhaar numbers, passwords, PINs, or other personal data.
- The current PIN handling and administrative access flow need security improvements.
- The blockchain is held in application memory and is not a distributed, independently verified ledger.
- A hash chain alone does not guarantee voter anonymity, election integrity, or tamper-proof results.
- Add automated tests, stronger authentication/authorization, safer credential handling, and transaction/integrity checks before extending the project.

## Future improvements

- Add unit and integration tests
- Add explicit admin authentication and role-based authorization
- Store PINs securely and validate all input
- Persist and verify the chain consistently across application restarts
- Add database migration/schema management
- Add screenshots or a short demo GIF after testing the app locally

## Author

Gitika Barigela
