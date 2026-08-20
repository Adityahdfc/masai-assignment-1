# HDFC Life Policy Claims Console

A small Java console application that demonstrates a policy and claims workflow for HDFC Life. The project is built around common object-oriented design patterns and Java collections rather than an external framework or database.

## What It Demonstrates

- Creating different policy types through a factory
- Calculating premiums with interchangeable strategy classes
- Storing and looking up policies with Java collections
- Building claims with optional fields using the Builder pattern
- Validating and filing claims through a service layer
- Publishing claim-status updates to multiple observers
- Writing claim activity to `audit.log` with try-with-resources
- Handling invalid claims, unknown policy types, and missing policies

## Policy Types and Premiums

The demo starts with a base premium and applies the following strategy:

| Policy type | Premium calculation |
| --- | --- |
| `TERM` | Base premium |
| `ENDOWMENT` | Base premium + 8% |
| `ULIP` | Base premium + 12% |

For example, a ULIP with a base premium of `42,000` has a calculated premium of `47,040`.

## Project Structure

```text
src/
├── App.java                         # Console demo entry point
├── config/                          # Application-level configuration
├── constants/                       # Policy and claim enums
├── exception/                       # Domain-specific runtime exceptions
├── factory/                         # PolicyFactory
├── model/                           # Policy and Claim domain models
├── observer/                        # Claim event publisher and notifiers
├── service/                         # Claim processing and audit logging
├── store/                           # In-memory policy collections
├── strategy/                        # Premium calculation strategies
└── resources/app.config             # Sample application configuration
```

## Design Patterns

| Pattern | Where it is used | Purpose |
| --- | --- | --- |
| Factory | `factory.PolicyFactory` | Selects the correct policy implementation from a type name |
| Strategy | `strategy.*PremiumStrategy` | Keeps premium rules separate and replaceable |
| Builder | `model.Claim.Builder` | Creates claims with optional hospital and remarks fields |
| Observer | `observer.ClaimEventPublisher` | Notifies registered channels when a claim status changes |
| Singleton | `config.AppConfig` enum | Provides shared application settings |

## Requirements

- Java 14 or newer. The source uses the modern `switch` expression syntax.
- A JDK, including the `javac` compiler.
- No third-party dependencies.

## Run From the Command Line

From the project root:

```bash
rm -rf out
mkdir out
javac -d out src/App.java src/config/*.java src/constants/*.java src/exception/*.java src/factory/*.java src/model/*.java src/observer/*.java src/service/*.java src/store/*.java src/strategy/*.java
java -cp out App
```

The application prints a console walkthrough that includes:

1. The configured company name
2. Seeded policies and their insertion order
3. Unique customer count and policy lookup
4. Policies sorted by policy ID with a `TreeMap`
5. Calculated ULIP premium
6. Observer notifications after a claim is accepted
7. Claims removed from the processing queue
8. Invalid claim and unknown policy type handling

Filing a claim also appends an entry to `audit.log` in the directory where the application is started.

## Run in IntelliJ IDEA

1. Open the project directory in IntelliJ IDEA.
2. Ensure a JDK 14+ is selected for the project.
3. Mark `src` as the Sources Root if IntelliJ has not detected it automatically.
4. Run `App.java`.

## Validation Rules

- Claim amounts must be greater than `0` and no more than `500000`.
- A claim must reference a policy present in `PolicyStore`.
- Supported policy types are `TERM`, `ENDOWMENT`, and `ULIP`.
- New policies start with `PolicyStatus.Pending`.
- New claims start with `ClaimStatus.SUBMITTED`.

## Current Implementation Notes

- Data is seeded in `App.main`; there is no database or interactive input yet.
- `PolicyStore` keeps policies in an `ArrayList`, `HashMap`, `HashSet`, and case-insensitive `TreeMap` to demonstrate different collection use cases.
- `ClaimEventPublisher` currently has two console observers: an in-app notifier and a branch-letter notifier.
- `AppConfig` currently returns the company name and maximum claim amount in code. The `src/resources/app.config` file is included as a configuration example but is not loaded by the current `AppConfig` implementation.
- The claim queue is ordered by `ClaimStatus` as defined by the enum, while `ClaimUrgency` is retained as claim data. The queue output in the demo should therefore be read as queue behavior, not as a general urgency scheduler.

## Learning Focus

This assignment is useful for practicing inheritance, enums, collections, custom exceptions, file I/O, try-with-resources, and several classic design patterns in a compact Java application.
