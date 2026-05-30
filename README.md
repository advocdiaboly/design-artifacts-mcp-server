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
    - Java 21
    - Spring Boot 3.2.2
    - Maven
    - Jackson (JSON & YAML parsing)
    - Lombok
    - Spring AI MCP (for Model Context Protocol support)

## Getting Started

### Prerequisites

- Java 21
- Maven 3.x
- Docker & Docker Compose

### Running with Docker

1. Build the image:
   ```bash
   docker build -t design-artifacts-mcp-server .
   ```

2. Run the container with a volume mount for local projects:
   ```bash
   docker run -p 8080:8080 -v /home/dima/projects:/app/projects design-artifacts-mcp-server
   ```

### MCP (Model Context Protocol) Integration

The service implements MCP using **Streamable HTTP** transport. 

- **Endpoint**: `/mcp`
- **Connection URL**: `http://<WSL2_IP>:8080/mcp` (Use your WSL2 IP address to connect from the host).
