# Task Manager REST API

A beginner-friendly Spring Boot CRUD API using Java 21, Maven, Spring Web, Spring Data JPA, Validation, and an H2 in-memory database.

## Requirements

- JDK 21
- Maven 3.6+ (or use the Maven support in IntelliJ IDEA)

## Run the application

From the project root:

```bash
mvn spring-boot:run
```

Or open the project in IntelliJ IDEA and run `TaskmanagerApplication.java`.

The API starts at `http://localhost:8080`.

## API endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/tasks` | Create a task |
| GET | `/api/tasks` | Get all tasks |
| GET | `/api/tasks/{id}` | Get one task |
| PUT | `/api/tasks/{id}` | Update a task |
| DELETE | `/api/tasks/{id}` | Delete a task |
| GET | `/api/tasks?status=PENDING` | Filter tasks by status |

Allowed statuses: `PENDING`, `IN_PROGRESS`, `COMPLETED`.

## Example requests

### 1. Create a task

POST `http://localhost:8080/api/tasks`

Header: `Content-Type: application/json`

```json
{
  "title": "Learn Spring Boot",
  "description": "Understand controllers, services and repositories",
  "status": "PENDING"
}
```

You can omit `status`; it defaults to `PENDING`.

### 2. Get all tasks

GET `http://localhost:8080/api/tasks`

### 3. Get task by ID

GET `http://localhost:8080/api/tasks/1`

### 4. Update a task

PUT `http://localhost:8080/api/tasks/1`

```json
{
  "title": "Complete Spring Boot basics",
  "description": "Build the Task Manager API",
  "status": "IN_PROGRESS"
}
```

### 5. Delete a task

DELETE `http://localhost:8080/api/tasks/1`

A successful delete returns HTTP 204 (No Content).

### 6. Filter by status

GET `http://localhost:8080/api/tasks?status=COMPLETED`

## H2 database console

Open `http://localhost:8080/h2-console`

Use these values:

- JDBC URL: `jdbc:h2:mem:taskdb`
- User Name: `sa`
- Password: leave blank

The H2 database is in-memory. Its data is cleared when the application stops.

## Project structure

```text
src/main/java/com/example/taskmanager/
├── TaskmanagerApplication.java
├── controller/
│   └── TaskController.java
├── entity/
│   ├── Task.java
│   └── TaskStatus.java
├── exception/
│   ├── GlobalExceptionHandler.java
│   └── TaskNotFoundException.java
├── repository/
│   └── TaskRepository.java
└── service/
    └── TaskService.java

src/main/resources/
└── application.properties
```

## Request flow

Client -> TaskController -> TaskService -> TaskRepository -> H2 Database

- Controller receives HTTP requests and returns responses.
- Service contains application/business logic.
- Repository performs database operations using Spring Data JPA.
- Entity represents the `tasks` database table.
