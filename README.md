# Skydive Forecast - User Service

[![CI Pipeline](https://github.com/MichalSarniewicz/skydive-forecast-user-service/actions/workflows/ci.yml/badge.svg)](https://github.com/MichalSarniewicz/skydive-forecast-user-service/actions/workflows/ci.yml)
[![codecov](https://codecov.io/gh/MichalSarniewicz/skydive-forecast-user-service/branch/master/graph/badge.svg)](https://codecov.io/gh/MichalSarniewicz/skydive-forecast-user-service)
[![Java](https://img.shields.io/badge/Java-21-green?logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue?logo=docker)](https://www.docker.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)


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
- **Spring Cloud Config Client**: Centralized configuration
- **Spring Cloud Consul Discovery**: Service discovery and registration
- **Spring Security**: JWT authentication
- **Spring Data JPA**: Database access
- **PostgreSQL**: Primary database
- **Redis**: Caching layer
- **Apache Kafka**: Event streaming
- **Liquibase**: Database migrations
- **Lombok**: Code generation
- **MapStruct**: Object mapping
- **SpringDoc OpenAPI**: 2.8.13
- **Testcontainers**: Integration testing
- **Monitoring**: Actuator, Prometheus, Grafana, Loki, Tempo
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

2. Ensure required services are running:
   - **Consul** (Port 8500) - Service discovery
   - **Config Server** (Port 8888) - Configuration
   - **PostgreSQL** (Port 5432) - Database
   - **Redis** (Port 6379) - Cache
   - **Kafka** (Port 9092) - Messaging

3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

The service will:
- Start on port **8081**
- Register itself in Consul
- Load configuration from Config Server

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

- `POST /api/v1/users/auth/token` - Generate JWT token (login)
- `POST /api/v1/users/auth/refresh` - Refresh JWT token using refresh token

### Users

- `GET /api/v1/users` - Get all users (requires `USER_VIEW` permission)
- `POST /api/v1/users` - Create new user (requires `USER_CREATE` permission)
- `PUT /api/v1/users/{user-id}` - Update user information (requires `USER_EDIT` permission)
- `PATCH /api/v1/users/{user-id}/status` - Activate/deactivate user (requires `USER_STATUS_UPDATE` permission)
- `PATCH /api/v1/users/me/password` - Change current user's password (requires `USER_PASSWORD_CHANGE` permission)

### Roles

- `GET /api/v1/users/roles` - Get all roles
- `POST /api/v1/users/roles?role-name={name}` - Create new role
- `DELETE /api/v1/users/roles/{role-id}` - Delete role (ADMIN role cannot be deleted)

### Permissions

- `GET /api/v1/users/permissions` - Get all permissions (requires `PERMISSION_VIEW` permission)
- `POST /api/v1/users/permissions` - Create permission (requires `PERMISSION_CREATE` permission)
- `PUT /api/v1/users/permissions/{id}` - Update permission (requires `PERMISSION_UPDATE` permission)
- `DELETE /api/v1/users/permissions/{id}` - Delete permission (requires `PERMISSION_DELETE` permission)

### Role-Permission Management

- `GET /api/v1/users/role-permissions` - Get all role-permission relationships (requires `ROLE_PERMISSION_VIEW` permission)
- `GET /api/v1/users/role-permissions/role/{role-id}` - Get permissions by role ID (requires `ROLE_PERMISSION_VIEW` permission)
- `GET /api/v1/users/role-permissions/permission/{permission-id}` - Get roles by permission ID (requires `ROLE_PERMISSION_VIEW` permission)
- `GET /api/v1/users/role-permissions/role/{role-id}/permission-codes` - Get permission codes by role ID (requires `ROLE_PERMISSION_VIEW` permission)
- `POST /api/v1/users/role-permissions` - Create role-permission relationship (requires `ROLE_PERMISSION_CREATE` permission)
- `PUT /api/v1/users/role-permissions/assign` - Assign multiple permissions to role (requires `ROLE_PERMISSION_ASSIGN` permission)
- `DELETE /api/v1/users/role-permissions/{id}` - Delete specific role-permission relationship (requires `ROLE_PERMISSION_DELETE` permission)
- `DELETE /api/v1/users/role-permissions/role/{role-id}` - Delete all permissions for a role (requires `ROLE_PERMISSION_DELETE` permission)
- `DELETE /api/v1/users/role-permissions/permission/{permission-id}` - Delete all role assignments for a permission (requires `ROLE_PERMISSION_DELETE` permission)

### User-Role Management

- `GET /api/v1/users/user-roles` - Get all user-role assignments (requires `USER_ROLE_VIEW_ALL` permission)
- `GET /api/v1/users/user-roles/user/{user-id}` - Get roles for specific user (requires `USER_ROLE_VIEW` permission)
- `POST /api/v1/users/user-roles` - Assign role to user (requires `USER_ROLE_ASSIGN` permission)
- `DELETE /api/v1/users/user-roles/user/{user-id}/role/{role-id}` - Remove role from user (requires `USER_ROLE_REMOVE` permission)

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
@PermissionSecurity(permission = "USER_VIEW")
public ResponseEntity<UsersDto> getAllUsers() {
    // ...
}
```

### Available Permissions

The system supports the following permission categories:

- **User Management**: `USER_VIEW`, `USER_CREATE`, `USER_EDIT`, `USER_STATUS_UPDATE`, `USER_PASSWORD_CHANGE`
- **Role Management**: Role operations (view, create, delete)
- **Permission Management**: `PERMISSION_VIEW`, `PERMISSION_CREATE`, `PERMISSION_UPDATE`, `PERMISSION_DELETE`
- **Role-Permission Management**: `ROLE_PERMISSION_VIEW`, `ROLE_PERMISSION_CREATE`, `ROLE_PERMISSION_ASSIGN`, `ROLE_PERMISSION_DELETE`
- **User-Role Management**: `USER_ROLE_VIEW_ALL`, `USER_ROLE_VIEW`, `USER_ROLE_ASSIGN`, `USER_ROLE_REMOVE`

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

The gateway discovers this service via Consul using `lb://user-service` and automatically routes requests.

## Monitoring

The service includes comprehensive monitoring capabilities:

### Metrics (Prometheus)

- **Endpoint**: `http://localhost:8081/actuator/prometheus`
- **Metrics**: JVM, HTTP requests, database connections, Kafka consumers, Redis cache

### Health Checks

- **Endpoint**: `http://localhost:8081/actuator/health`

### Logs (Loki)

Application logs are automatically sent to Loki for centralized log aggregation.

### Distributed Tracing (Tempo)

- **Endpoint**: `http://localhost:4318`
- **Traces**: Request flows across services with timing information
- **Sampling**: 100% of requests traced (configurable)

### Grafana Dashboards

Access Grafana at `http://localhost:3000` (admin/admin)

Recommended dashboard: Import ID **11378** (JVM Micrometer)

## License

This project is part of the Skydive Forecast system.

## Contact

For questions or support, please contact me.
