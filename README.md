# Skydive Forecast - User Service

User management and authentication microservice for the Skydive Forecast system, built with Spring Boot and hexagonal architecture.

## Overview

This service handles user authentication, authorization, role-based access control (RBAC), and permission management for the Skydive Forecast ecosystem. It provides JWT-based authentication with refresh token support and Redis caching.

## Features

- User registration and management
- JWT-based authentication with refresh tokens
- Role-Based Access Control (RBAC)
- Fine-grained permission system
- Password validation and security
- Redis caching for improved performance
- RESTful API with OpenAPI documentation

## Technology Stack

- **Java**: 21
- **Spring Boot**: 3.5.6
- **Spring Security**: JWT authentication
- **Spring Data JPA**: Database access
- **PostgreSQL**: Primary database
- **Redis**: Caching layer
- **Liquibase**: Database migrations
- **Lombok**: Code generation
- **MapStruct**: Object mapping
- **SpringDoc OpenAPI**: 2.8.13
- **Testcontainers**: Integration testing
- **Build Tool**: Maven

## Architecture

The service follows hexagonal (ports and adapters) architecture:

- **Domain Layer**: Core business logic, entities, and use cases
- **Application Layer**: Use case implementations
- **Infrastructure Layer**: Controllers, security, configuration, and external adapters

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.x
- PostgreSQL database
- Redis server

### Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd skydive-forecast-user-service
```

2. Configure database and Redis in `application-{profile}.yaml`

3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

The service will start on port **8081** by default.

## Configuration

The application supports multiple profiles:

- `dev` (default): Development environment
- `test`: Testing environment
- `prod`: Production environment

To run with a specific profile:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## API Endpoints

### Authentication

- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Authenticate user
- `POST /api/auth/refresh` - Refresh access token

### Users

- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}` - Update user
- `PUT /api/users/{id}/status` - Update user status
- `PUT /api/users/{id}/password` - Change password

### Roles

- `GET /api/roles` - Get all roles
- `POST /api/roles` - Create role
- `PUT /api/roles/{id}` - Update role
- `DELETE /api/roles/{id}` - Delete role

### Permissions

- `GET /api/permissions` - Get all permissions
- `POST /api/permissions` - Create permission
- `PUT /api/permissions/{id}` - Update permission
- `DELETE /api/permissions/{id}` - Delete permission

### Role-Permission Management

- `POST /api/role-permissions` - Assign permission to role
- `DELETE /api/role-permissions` - Remove permission from role
- `GET /api/role-permissions/role/{roleId}` - Get permissions by role

### User-Role Management

- `POST /api/user-roles` - Assign role to user
- `DELETE /api/user-roles` - Remove role from user
- `GET /api/user-roles/user/{userId}` - Get roles by user

## API Documentation

- **Swagger UI**: `http://localhost:8081/swagger-ui.html`
- **OpenAPI Docs**: `http://localhost:8081/v3/api-docs/users`

## Security

The service uses JWT Bearer token authentication. Include the token in the Authorization header:

```
Authorization: Bearer <your-jwt-token>
```

### Permission-Based Security

The service implements fine-grained permission checking using the `@PermissionSecurity` annotation:

```java
@PermissionSecurity(permissions = {"USER_READ"})
public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
    // ...
}
```

## Database

### Schema Management

Database migrations are managed by Liquibase. Changelog files are located in:
```
src/main/resources/db/changelog/
```

### Running with Docker Compose

A `docker-compose.yml` file is provided for local development:

```bash
docker-compose up -d
```

This starts PostgreSQL and Redis containers.

## Development

### Building

```bash
mvn clean package
```

### Running Tests

```bash
mvn test
```

Tests use Testcontainers for integration testing with PostgreSQL.

### Running the JAR

```bash
java -jar target/skydive-forecast-user-service-1.0.0-SNAPSHOT.jar
```

### Docker Build

```bash
docker build -t skydive-forecast-user-service .
```

## Integration with Gateway

This service is accessed through the API Gateway at:
```
http://localhost:8080/api/users/**
```

The gateway routes requests to this service running on port 8081.

## License

This project is part of the Skydive Forecast system.

## Contact

For questions or support, please contact me.
