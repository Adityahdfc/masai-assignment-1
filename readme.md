# HDFC Life Policy Claims Console

A plain Java console application demonstrating **Collections, SOLID principles, Singleton, Factory, Builder, Strategy, Observer, and custom exception handling** through a small HDFC Life policy and claims management system.

## Features

* Stores and manages insurance policies
* Calculates premiums using the **Strategy Pattern**
* Creates policies using the **Factory Pattern**
* Creates immutable claims using a fluent **Builder**
* Tracks claim status changes using the **Observer Pattern**
* Uses a thread-safe enum **Singleton** for application configuration
* Demonstrates Java Collections:

  * `ArrayList` — all policies
  * `HashSet` — unique customer names
  * `HashMap` — policy lookup by policy number
  * `TreeMap` — policies sorted by policy number
  * `PriorityQueue` — claims ordered by urgency
* Uses `Iterator` to print policies
* Handles custom runtime exceptions
* Writes claim activity to `audit.log` using try-with-resources
* Uses plain Java only — no Spring and no Java Streams

## Project Structure

```text
hdfc-life-policy-system/
├── src/
│   └── com/
│       └── hdfclife/
│           ├── Main.java
│           │
│           ├── config/
│           │   └── AppConfig.java
│           │
│           ├── model/
│           │   ├── Policy.java
│           │   ├── TermLifePolicy.java
│           │   ├── UlipPolicy.java
│           │   ├── EndowmentPolicy.java
│           │   ├── Claim.java
│           │   └── Urgency.java
│           │
│           ├── store/
│           │   └── PolicyStore.java
│           │
│           ├── factory/
│           │   └── PolicyFactory.java
│           │
│           ├── strategy/
│           │   ├── PremiumStrategy.java
│           │   ├── TermPremiumStrategy.java
│           │   ├── UlipPremiumStrategy.java
│           │   ├── EndowmentPremiumStrategy.java
│           │   └── PremiumCalculator.java
│           │
│           ├── observer/
│           │   ├── ClaimObserver.java
│           │   ├── ClaimEventPublisher.java
│           │   ├── InAppNotifier.java
│           │   └── BranchLetterNotifier.java
│           │
│           ├── service/
│           │   ├── ClaimService.java
│           │   └── AuditLogger.java
│           │
│           └── exception/
│               ├── PolicyServiceException.java
│               ├── PolicyNotFoundException.java
│               ├── InvalidClaimException.java
│               └── UnknownPolicyTypeException.java
│
├── .gitignore
└── README.md
```

## Seed Data

The application initializes the following six policies through `PolicyFactory`:

| Policy Number  | Customer     | Type      | Base Premium | Status  |
| -------------- | ------------ | --------- | -----------: | ------- |
| HDFC-LIFE-1001 | Anita Sharma | TERM      |       18,500 | Active  |
| HDFC-LIFE-1002 | Rahul Mehta  | ULIP      |       42,000 | Active  |
| HDFC-LIFE-1003 | Priya Nair   | ENDOWMENT |       27,000 | Lapsed  |
| HDFC-LIFE-1004 | Vikram Singh | TERM      |       15,200 | Active  |
| HDFC-LIFE-1005 | Sneha Patel  | ULIP      |       36,000 | Active  |
| HDFC-LIFE-1006 | Anita Sharma | ENDOWMENT |       22,000 | Pending |

## Design Patterns

### 1. Singleton

`AppConfig` is implemented as a thread-safe enum singleton:

```java
AppConfig.INSTANCE
```

It provides:

* Company name: `HDFC Life`
* Maximum claim amount: `500000`

No direct object creation is required.

### 2. Factory

`PolicyFactory` centralizes policy creation:

```java
PolicyFactory.create(type, policyNo, customer, premium, status)
```

Supported types:

* `TERM` → `TermLifePolicy`
* `ULIP` → `UlipPolicy`
* `ENDOWMENT` → `EndowmentPolicy`

An unknown type throws `UnknownPolicyTypeException`.

### 3. Builder

Claims are created using a fluent static inner `Builder`:

```java
Claim claim = new Claim.Builder(
        "HDFC-LIFE-1001",
        25000,
        Urgency.HIGH
    )
    .hospitalName("Apollo Hospital")
    .remarks("Hospitalisation")
    .build();
```

Required fields:

* `policyNo`
* `claimAmount`
* `urgency`

Optional fields:

* `hospitalName`
* `remarks`

A claim starts with `SUBMITTED` status. After construction, only `updateStatus(...)` can modify its state.

### 4. Strategy

Premium calculation is separated from `PremiumCalculator` using `PremiumStrategy`.

Premium rules:

| Policy Type | Calculation               |
| ----------- | ------------------------- |
| TERM        | `basePremium * 100 / 100` |
| ULIP        | `basePremium * 112 / 100` |
| ENDOWMENT   | `basePremium * 108 / 100` |

For example:

```text
42000 * 112 / 100 = 47040
```

Therefore:

```text
ULIP premium for HDFC-LIFE-1002 = 47040
```

A different `PremiumStrategy` can be supplied to `PremiumCalculator` at runtime without modifying the calculator itself.

### 5. Observer

`ClaimEventPublisher` notifies every registered `ClaimObserver` when a claim status changes.

Observers:

* `InAppNotifier`
* `BranchLetterNotifier`

Both implement the small interface:

```java
ClaimObserver
```

with one method:

```java
onClaimUpdate(Claim claim)
```

When the HIGH-priority claim changes from `SUBMITTED` to `APPROVED`, both observers receive the update.

## SOLID Principles

### Single Responsibility Principle

Responsibilities are separated into focused classes:

* `PolicyStore` — policy storage and collection management
* `PremiumCalculator` — premium calculation
* `ClaimService` — claim-related operations
* `AuditLogger` — audit logging

### Open/Closed Principle

New premium calculation strategies can be added by implementing `PremiumStrategy` without modifying `PremiumCalculator`.

### Liskov Substitution Principle

Any implementation of `PremiumStrategy` can be supplied to `PremiumCalculator`.

### Interface Segregation Principle

Observers depend only on the small `ClaimObserver` interface containing a single method.

### Dependency Inversion Principle

`ClaimService` works with abstractions such as `PremiumStrategy` and `ClaimObserver` rather than depending directly on concrete implementations.

## Collections Used

The application intentionally uses all required collection types:

```text
ArrayList
    ↓
All policies

HashSet
    ↓
Unique customer names

HashMap
    ↓
Policy number → Policy lookup

TreeMap
    ↓
Policy number → Policy sorted by policy number

PriorityQueue
    ↓
Claims ordered by:
HIGH → MEDIUM → LOW
```

Policies are printed using an `Iterator` rather than relying only on an enhanced `for` loop.

## Exception Handling

The custom exception hierarchy is:

```text
RuntimeException
    └── PolicyServiceException
        ├── PolicyNotFoundException
        ├── InvalidClaimException
        └── UnknownPolicyTypeException
```

### Policy Not Found

Looking up:

```text
HDFC-LIFE-9999
```

throws:

```text
PolicyNotFoundException
```

### Invalid Claim

A claim amount is invalid when:

```text
amount <= 0
OR
amount > 500000
```

For example:

```text
600000
```

throws:

```text
InvalidClaimException
```

### Unknown Policy Type

Creating a policy with:

```text
INVALID
```

throws:

```text
UnknownPolicyTypeException
```

Exceptions are caught in `main`, and their messages are printed rather than being silently ignored.

## Audit Logging

`AuditLogger` implements:

```java
AutoCloseable
```

and writes claim activity to:

```text
audit.log
```

The logger is used with try-with-resources so that the file resource is closed automatically.

If writing the file fails, the underlying `IOException` is wrapped in `PolicyServiceException` while preserving the original exception as the cause.

`audit.log` is intentionally excluded from Git.

## Application Flow

The `main` method demonstrates the complete required flow:

1. Print company name from `AppConfig`
2. Print all six policies using an `Iterator`
3. Print unique customer count
4. Look up `HDFC-LIFE-1004`
5. Print `TreeMap` keys in sorted order
6. Calculate ULIP premium for `HDFC-LIFE-1002`
7. Register both claim observers
8. File HIGH, MEDIUM, and LOW claims
9. Update the HIGH claim to `APPROVED`
10. Demonstrate notifications from both observers
11. Poll claims from the `PriorityQueue`
12. Demonstrate missing-policy exception
13. Demonstrate invalid-claim exception
14. Demonstrate unknown-policy-type exception
15. Write a claim entry to `audit.log`

## Expected Important Output

The exact formatting may vary, but the application demonstrates these required results:

```text
HDFC Life

Unique customer count: 5

HDFC-LIFE-1004 -> Vikram Singh

TreeMap keys:
HDFC-LIFE-1001
HDFC-LIFE-1002
HDFC-LIFE-1003
HDFC-LIFE-1004
HDFC-LIFE-1005
HDFC-LIFE-1006

ULIP premium for HDFC-LIFE-1002: 47040

InAppNotifier: Claim HDFC-LIFE-1001 status changed to APPROVED
BranchLetterNotifier: Claim HDFC-LIFE-1001 status changed to APPROVED

PriorityQueue order:
HIGH
MEDIUM
LOW

Policy not found: HDFC-LIFE-9999
Invalid claim amount: 600000
Unknown policy type: INVALID
```

An entry is also written to:

```text
audit.log
```

## How to Compile

From the project root:

```bash
javac -d out $(find src -name "*.java")
```

On Windows PowerShell:

```powershell
javac -d out (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName })
```

## How to Run

```bash
java -cp out com.hdfclife.Main
```

## Requirements

* Java 8+ recommended
* No Spring Framework
* No external dependencies
* No Java Streams
* Standard Java Collections and APIs only

## Git Hygiene

The repository should contain source code and documentation, but generated files should not be committed.

Recommended `.gitignore`:

```gitignore
# Compiled Java files
*.class

# Build/output directories
out/
bin/
target/

# Application-generated audit file
audit.log

# IDE files
.idea/
.vscode/
*.iml

# OS files
.DS_Store
Thumbs.db
```

## Learning Goals

This project demonstrates how common Java design principles and patterns can work together in a practical console application:

```text
PolicyFactory
      ↓
PolicyStore
      ↓
PremiumCalculator ← PremiumStrategy
      ↓
ClaimService
      ↓
ClaimEventPublisher
      ↓
 ┌───────────────┐
 ↓               ↓
InAppNotifier   BranchLetterNotifier
```

The implementation keeps business responsibilities separated while still providing a simple executable Java application.

