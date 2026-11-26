
# Task Manager REST API

A RESTful Task Management application built with **Java 21, Spring Boot 3.5.7, Spring Data JPA and PostgreSQL**.

The application supports CRUD operations, pagination, dynamic search, validation, exception handling, role-based security and interactive API documentation.

## Features

- Create, retrieve, update and delete tasks.
- PostgreSQL integration using Spring Data JPA.
- Pagination with configurable page number and size.
- Case-insensitive search by task title.
- Filter tasks by status.
- Combined search and status filtering.
- DTO-based request and response handling using Java Records.
- Request validation using Jakarta Bean Validation.
- Centralized exception handling using `@RestControllerAdvice`.
- HTTP Basic Authentication with BCrypt password encoding.
- Role-based authorization for USER and ADMIN.
- OpenAPI documentation with Swagger UI.
- Application monitoring using Spring Boot Actuator.
- Automated testing using JUnit 5 and Mockito.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot 3.5.7 | Application framework |
| Spring Web | REST API development |
| Spring Data JPA | Database access |
| PostgreSQL | Relational database |
| Maven | Dependency management |
| Spring Security | Authentication and authorization |
| Springdoc OpenAPI | API documentation |
| JUnit 5 | Unit testing |
| Mockito | Mocking dependencies |
| Spring Boot Actuator | Application monitoring |

## Prerequisites

- JDK 21
- Maven 3.6+
- PostgreSQL
- IntelliJ IDEA (recommended)
- Postman (optional)

## Database Setup

Create a PostgreSQL database:

```sql
CREATE DATABASE taskmanager_db;
```

Development database configuration:

- Host: `localhost`
- Port: `5432`
- Database: `taskmanager_db`
- Username: `hariharan` (default)
- Password: Empty by default

Environment variables can override the database credentials:

```text
DB_USERNAME=your_username
DB_PASSWORD=your_password
```

Do not commit real database passwords to GitHub.

## Run the Application

Clone the repository:

```bash
git clone https://github.com/Hariharan3478/SpringBoot-TaskManager.git
```

Navigate to the project:

```bash
cd SpringBoot-TaskManager
```

Run the application:

```bash
mvn spring-boot:run
```

Or open the project in IntelliJ IDEA and run the main Spring Boot application class.

Application URL:

```text
http://localhost:8080
```

Default active profile: `dev`

## API Endpoints

Base URL: `http://localhost:8080/api/tasks`

| Method | Endpoint | Description | Access |
|---|---|---|---|
| POST | `/api/tasks` | Create a task | USER, ADMIN |
| GET | `/api/tasks` | Get all tasks | USER, ADMIN |
| GET | `/api/tasks/{id}` | Get task by ID | USER, ADMIN |
| PUT | `/api/tasks/{id}` | Update a task | USER, ADMIN |
| DELETE | `/api/tasks/{id}` | Delete a task | ADMIN |

### Pagination, Search and Filtering

The GET all tasks endpoint supports:

| Parameter | Description | Default |
|---|---|---|
| `page` | Page number (zero-based) | 0 |
| `size` | Number of records per page | 5 |
| `search` | Search by task title | None |
| `status` | Filter by task status | None |

Example requests:

```http
GET /api/tasks?page=0&size=5
GET /api/tasks?search=Java
GET /api/tasks?status=PENDING
GET /api/tasks?search=Java&status=COMPLETED
```

Available task statuses:

- `PENDING`
- `IN_PROGRESS`
- `COMPLETED`

## Example API Requests

### 1. Create a Task

POST `/api/tasks`

Request body:

```json
{
  "title": "Learn Spring Boot",
  "description": "Build REST APIs using Spring Boot",
  "status": "PENDING"
}
```

### 2. Get All Tasks

GET `/api/tasks`

### 3. Get Task by ID

GET `/api/tasks/1`

### 4. Update a Task

PUT `/api/tasks/1`

Request body:

```json
{
  "title": "Complete Spring Boot basics",
  "description": "Build and test the Task Manager API",
  "status": "IN_PROGRESS"
}
```

### 5. Delete a Task

DELETE `/api/tasks/1`

Successful deletion returns `204 No Content`.

## Validation and Exception Handling

Request validation is implemented using:

- `@NotBlank`
- `@NotNull`
- `@Size`
- `@Valid`

Centralized exception handling uses `@RestControllerAdvice`.

Common HTTP response codes:

| Status | Meaning |
|---|---|
| 200 | OK |
| 201 | Created |
| 204 | No Content |
| 400 | Bad Request |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Not Found |

## Spring Security

The application currently uses HTTP Basic Authentication with BCrypt password encoding and in-memory users.

| Username | Password | Role |
|---|---|---|
| `hari` | `user123` | USER |
| `admin` | `admin123` | ADMIN |

Authorization rules:

- USER and ADMIN can create tasks.
- USER and ADMIN can retrieve tasks.
- USER and ADMIN can update tasks.
- Only ADMIN can delete tasks.
- Unauthenticated requests to protected endpoints are rejected.

**Security note:** These are development-only demo credentials. Do not use them in production. Use HTTPS and securely managed credentials in production.

## OpenAPI and Swagger UI

Swagger UI documentation:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI specification:

```text
http://localhost:8080/v3/api-docs
```

Swagger UI allows developers to:

- View all available API endpoints.
- Understand request and response structures.
- Test REST APIs directly from the browser.
- Authenticate using Basic Auth.

## Actuator Monitoring

The following monitoring endpoints are exposed:

```text
/actuator/health
/actuator/info
/actuator/metrics
```

## Testing

Run all tests using:

```bash
mvn test
```

Testing technologies:

- JUnit 5
- Mockito
- `@WebMvcTest` for controller testing
- `@DataJpaTest` for repository testing

Review the actual JaCoCo report before adding a coverage percentage.

## Configuration Profiles

The application uses separate configuration files:

- `application.yml` – Common configuration.
- `application-dev.yml` – Local PostgreSQL development configuration.
- `application-prod.yml` – Production database configuration using environment variables.

Development uses Hibernate schema updates, while production uses schema validation.

## Project Architecture

```text
Client / Postman / Swagger UI
             |
             v
       Spring Security
             |
             v
       TaskController
             |
             v
        TaskService
             |
             v
      TaskRepository
             |
             v
         PostgreSQL
```

### Main Layers

- **Controller:** Handles HTTP requests and responses.
- **Service:** Contains business logic.
- **Repository:** Handles database operations.
- **Entity:** Represents database records.
- **DTO:** Defines API request and response structures.
- **Exception:** Handles application errors.
- **Config:** Contains security and OpenAPI configurations.

## Future Improvements

- JWT-based authentication.
- Database-backed user management.
- Docker and Docker Compose.
- CI/CD pipeline integration.
- Additional integration tests.
- Improved API response documentation.

## GitHub Repository

https://github.com/Hariharan3478/SpringBoot-TaskManager
