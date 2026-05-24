# Contributing Guidelines

This document outlines the standards and workflow for contributing to the Project Bridge service.

## Development Workflow (Agentic)

When working on this project, follow these steps:

1.  **Analyze**: Read the `ARCHITECTURE.md` and existing code to understand the context.
2.  **Plan**: Create or update a task in `TASKS.md` before implementation.
3.  **Implement**: 
    - Follow existing code patterns.
    - Use Lombok for boilerplate reduction.
    - Ensure all new files/directories are created with correct permissions.
4.  **Verify**:
    - Run `mvn clean compile` to ensure no syntax errors.
    - If tests are available, run `mvn test`.
    - Manually verify the filesystem changes if the task involves I/O.

## Coding Standards

- **Language**: Java 17.
- **Style**: Follow standard Spring Boot conventions.
- **Error Handling**: Use appropriate HTTP status codes and meaningful error messages.
- **No Comments**: Do not add unnecessary comments unless specifically requested or for complex logic.
- **Immutability**: Prefer immutable objects where possible.

## Task Tracking

Always update `TASKS.md` to reflect the current progress. Mark tasks as `[x]` when completed.
