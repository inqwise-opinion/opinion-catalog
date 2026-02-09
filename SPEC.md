# Opinion Catalog Architecture

This document is an abstract, concrete overview of architecture and dataflow for Opinion Catalog modules.

## Architecture Overview

- **Modules**
  - `opinion-catalog/`: HTTP service entry point (OpenAPI router, service implementation, DAO mappers).
  - `opinion-catalog-common/`: DTOs, interfaces, and shared helpers.

- **Layering**
  - **OpenAPI Contract** defines paths, operationIds, request/response schemas for catalog resources.
  - **Router** binds operationIds to handlers for read-only operations.
  - **Service** implements catalog queries and input validation.
  - **DAO** handles storage access and row/parameter mapping.
  - **DTOs** live in `opinion-catalog-common` and drive JSON I/O.

## Dataflow (Request → Response)

1. **OpenAPI validation**
   - The router validates requests and binds to `operationId` handlers.
2. **Router handler**
   - Parses path params and request bodies into DTOs.
   - Builds catalog identity objects from path parameters.
3. **Service**
   - Validates input, applies business constraints, and orchestrates data access.
4. **DAO**
   - Reads catalog data using SQL templates with named parameters.
5. **Response**
   - DTOs are serialized to JSON and returned with read-appropriate status codes.
