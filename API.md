# API Documentation

All API endpoints are prefixed with `/api`.

## Overview

The Project Bridge service acts as a specialized tool to bridge the gap between the **Architecture/Design phase** and the **Development phase**.

By exposing projects and their Architectural Decision Records (ADRs) via the Model Context Protocol (MCP), the service allows separate LLMs or agents to operate in different stages of the lifecycle:
1.  **Design Phase**: An architect agent creates and manages ADRs, defining the project's structure and decisions.
2.  **Development Phase**: A developer agent consumes these ADRs from the Project Bridge to ensure implementation aligns perfectly with the established design.

This separation of concerns ensures that design decisions are formally documented as the "source of truth" before implementation begins.

## Projects

### Create a New Project
`POST /api/projects`

**Query Parameters:**
- `name` (String, required): The name of the project.
- `description` (String, optional): A description of the project.

**Returns:**
- `Project` object.

---

### List All Projects
`GET /api/projects`

**Returns:**
- A list of `Project` objects.

---

### Get Project Details
`GET /api/projects/{id}`

**Path Parameters:**
- `id` (String, required): The unique identifier of the project.

**Returns:**
- A `Project` object.

---

## Architectural Decision Records (ADRs)

ADRs are the primary source of truth for project design decisions. They are stored in the project's directory under `adrs/` as `.md` files.

### Create a New ADR
`POST /api/projects/{projectId}/adrs`

**Path Parameters:**
- `projectId` (String, required): The unique identifier of the project.

**Query Parameters:**
- `title` (String, required): The title of the ADR.

**Request Body:**
- `content` (String, required): The Markdown content of the ADR.

**Returns:**
- `ADR` object.

---

### List ADRs for a Project
`GET /api/projects/{projectId}/adrs`

**Path Parameters:**
- `projectId` (String, required): The unique identifier of the project.

**Returns:**
- A list of `ADR` objects belonging to the project.

---

### Get ADR Details
`GET /api/adrs/{adrId}`

**Path Parameters:**
- `adrId` (String, required): The unique identifier of the ADR.

**Returns:**
- An `ADR` object.
