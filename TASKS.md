# Project Roadmap & Tasks

## Phase 1: Foundation (Completed)
- [x] Initialize Maven project structure
- [x] Define Project and ADR models
- [x] Implement File-based Repository/Service logic
- [x] Implement REST Controllers
- [x] Implement Dockerfile and Docker Compose integration

## Phase 2: Robustness & Testing (Pending)
- [x] Make ADR directory configurable (default: docs/adr)
- [ ] Add unit tests for `ProjectService` (specifically filesystem I/O)
- [ ] Add integration tests for the REST API
- [ ] Implement error handling for edge cases (e.g., duplicate ADR titles, invalid project IDs)
- [ ] Add validation for input (e.g., name length, title characters)
- [ ] Verify Docker container integration and LibreChat connection.

## Phase 3: Advanced Features (Pending)
- [ ] Add capability to list all ADRs for a specific project (already partially implemented, need to verify)
- [ ] Add capability to search for ADRs by keyword in content
- [ ] Implement a "Project Summary" endpoint that aggregates all ADR titles

## Phase 4: Documentation & Refinement (Pending)
- [ ] Complete `README.md` with full API documentation
- [ ] Finalize `ARCHITECTURE.md`
