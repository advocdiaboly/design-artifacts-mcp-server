# Architecture Design

## Overview

The Project Bridge service is designed to provide a persistent, file-based storage mechanism for project-related information, specifically intended to bridge the gap between high-level chat discussions and low-level code implementation.

## Data Model & Storage Strategy

To avoid synchronization issues and keep the system lightweight, the service uses the **Filesystem as the Single Source of Truth**.

### 1. Project Storage
Each project is represented by a directory within the base `/app/projects` directory.
- **Directory Structure**:
  ```text
  /app/projects/{project_id}/
  ├── metadata.json      # Contains project name, description, and creation date.
  └── {adrs_dir}/        # Sub-directory for ADR files (default: docs/adr).
  ```

### 2. ADR Storage
Architectural Decision Records (ADRs) are stored as Markdown files. To allow for programmatic retrieval of metadata, each file uses **YAML Frontmatter**.
- **File Structure**:
  ```markdown
  ---
  id: <uuid>
  projectId: <project_id>
  title: <title>
  createdAt: <ISO-8601-timestamp>
  ---

  <ADR Content in Markdown>
  ```

## Component Breakdown

### Controller Layer
Exposes a RESTful API. It handles incoming requests from LibreChat tools and translates them into service calls.

### Service Layer
Contains the core business logic:
- Managing directory structures.
- Parsing and writing JSON/YAML/Markdown.
- Searching for specific ADRs within the filesystem.

### Model Layer
Simple POJOs (Plain Old Java Objects) representing `Project` and `ADR` entities.

## Communication Flow

1. **LibreChat (User)**: "Create a new project named 'Alpha'."
2. **LibreChat (Agent)**: Calls `POST /api/projects?name=Alpha`.
3. **Project Bridge**: Creates directory, writes `metadata.json`, returns Project ID.
4. **LibreChat (User)**: "Let's write an ADR for the database choice."
5. **LibreChat (Agent)**: Calls `POST /api/projects/{id}/adrs?title=DatabaseChoice` with content.
6. **Project Bridge**: Writes `.md` file to `/app/projects/{id}/{adrs_dir}/DatabaseChoice.md`.
7. **Opencode (Agent)**: Reads the files from the shared volume to implement the project according to the ADRs.

## MCP (Model Context Protocol) Integration

The service implements the Model Context Protocol (MCP) using the Spring AI MCP starter. This allows LLMs to interact with the service's capabilities directly through standardized tools.

### MCP Tools
- `list_projects`: Lists all available projects.
- `get_project`: Returns details of a specific project.
- `create_project`: Creates a new project.
- `list_adrs`: Lists ADRs for a project.
- `create_adr`: Creates a new ADR.
- `get_adr`: Returns details of a specific ADR.
- `list_files`: Lists files in a directory for discovery.
- `read_file`: Reads the content of a file.

### Integration Details
- **Transport**: Uses Streamable HTTP transport.
- **Implementation**: Spring AI MCP starter manages the server lifecycle. Tools are implemented declaratively using `@McpTool` annotations on service methods, which the auto-configuration automatically detects and registers.

