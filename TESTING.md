# Testing Guide

This document describes the testing strategy and instructions for the Project Bridge service.

## Testing Strategy

The project follows a tiered testing approach:

1.  **Unit Tests**: Testing individual components (Models, Services, Repositories) in isolation.
2.  **Integration Tests**: Testing the interaction between components, specifically the REST controllers and the filesystem-based service layer.

## Running Tests

Tests are managed using Maven.

### Run All Tests
To run all tests in the project, use the following command:

```bash
mvn test
```

### Run Specific Tests
To run a specific test class:

```bash
mvn test -Dtest=ClassName
```

## Writing New Tests

### Unit Tests
- Use **JUnit 5** for test structure.
- Use **Mockito** to mock dependencies (e.g., mocking `ProjectService` when testing `ProjectController`).
- Focus on testing business logic and edge cases.

### Integration Tests
- Use **@SpringBootTest** to load the application context.
- Use **MockMvc** to perform requests against the REST controllers.
- Ensure that filesystem-based storage is correctly interacted with during tests.
- **Cleanup**: Ensure that any files created during integration tests are cleaned up to maintain a predictable test environment.

## Common Commands

- `mvn clean compile`: Compiles the project and checks for syntax errors.
- `mvn clean test`: Runs all tests from a clean state.
- `mvn clean install`: Builds the project and installs it to the local Maven repository.
