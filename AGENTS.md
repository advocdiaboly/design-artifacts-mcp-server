# Agentic Development Guide

This document is designed to guide AI agents in effectively contributing to the Project Bridge service.

# Agent Execution Rules

## Context and Memory Limits
- Do not maintain a full log of unchanged file states in the prompt history.
- After successfully executing a terminal command, compress its output to a 1-line success summary before sending it back to the context.
- Keep the interaction history strictly focused on the current task objective. Clear intermediate conversational padding.

## Code Scope
- Do not scan the entire codebase on every iteration. Use specific file paths provided in the prompt.

## Core Principles

- **Context First**: Always read `ARCHITECTURE.md`, relevant source files, and **existing ADRs** in the filesystem to understand the design decisions and project context.
- **Plan Before Action**: For any non-trivial change, create or update a task in `TASKS.md` before writing code.
- **Follow Conventions**: Mimic existing code styles, use Lombok for boilerplate, and adhere to Spring Boot patterns.
- **Verify Implementation**: After making changes, verify them using the appropriate build or test commands.

## Development Workflow

1. **Analyze**: Explore the codebase using search tools to understand the current implementation and context.
2. **Plan**: 
    - Identify the necessary changes.
    - Update `TASKS.md` with a new `pending` task.
    - If the task is complex, use a `todowrite` session to manage sub-tasks.
3. **Implement**:
    - Create new files or edit existing ones following the project's coding standards.
    - Ensure all new dependencies are added to `pom.xml` if necessary.
4. **Verify**:
    - Run `mvn clean compile` to check for syntax and compilation errors.
    - Run existing tests using `mvn test`.
    - If you added new functionality, suggest or write corresponding tests.
5. **Document**:
    - Update `STATUS.md` to reflect progress.
    - Update `TASKS.md` by marking the task as `[x]` (completed).
    - Update `API.md` if any new endpoints were added.
    - **Update/Create ADRs**: When a significant design decision is made, document it in a new ADR file to ensure the context is preserved for future development.

## Tool Usage

- **Search**: Use `grep` and `glob` to locate relevant files and logic.
- **Read**: Use `read` to understand file contents.
- **Edit**: Use `edit` for precise changes.
- **Bash**: Use `bash` for building, testing, and checking the filesystem.
- **LSP**: Use LSP tools (e.g., `goToDefinition`, `findReferences`, `hover`) for semantic code navigation and understanding.

## Task Tracking

- **`STATUS.md`**: High-level overview of the project's current state and goals.
- **`TASKS.md`**: Granular list of actionable items. Always update this file.
