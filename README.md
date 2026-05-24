# Project Bridge

A lightweight Java/Spring Boot service that acts as a bridge between **LibreChat** and **Opencode**. 

It manages project metadata and Architectural Decision Records (ADRs) using a file-based approach, ensuring that specifications discussed in the chat interface are persisted as Markdown files for later use in development.

## Features
    
- **Project Management**: Create and manage project directories.
- **ADR Management**: Create, retrieve, and persist ADRs as `.md` files with YAML frontmatter.
- **File-System as Source of Truth**: No database required; all data is stored in the filesystem for easy access by other tools.
- **REST API**: Provides a simple interface for LibreChat tools to interact with.
- **MCP (Model Context Protocol)**: Provides a standardized toolset for LLMs to interact with the project bridge.

## Architecture

The service follows a simple RESTful architecture:
- **Storage**: Filesystem-based.
    - `/app/projects/{project_id}/metadata.json` for project details.
    - `/app/projects/{project_id}/adrs/{title}.md` for ADRs.
- **Tech Stack**:
    - Java 17
    - Spring Boot 3.2.2
    - Maven
    - Jackson (JSON & YAML parsing)
    - Lombok
    - Spring AI MCP (for Model Context Protocol support)

## Getting Started

### Prerequisites

- Java 17
- Maven 3.x
- Docker & Docker Compose

### Running with Docker

The service is designed to be run as part of the `verter_infra` stack.

1. Ensure you are in the `verter_infra` directory.
2. Run the following command:
   ```bash
   docker compose up -d project-bridge
   ```

### Local Development

1. Navigate to the project directory.
2. Build the project:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## API Endpoints

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/projects` | Create a new project |
| `GET` | `/api/projects` | List all projects |
| `GET` | `/api/projects/{id}` | Get project details |
| `POST` | `/api/projects/{projectId}/adrs` | Create a new ADR |
| `GET` | `/api/projects/{projectId}/adrs` | List ADRs for a project |
| `GET` | `/api/adrs/{adrId}` | Get a specific ADR |
