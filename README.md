# Auth Service

User authentication for the e-commerce platform: registration, login (JWT access + refresh tokens), profile management, and user lifecycle events.

## API

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/auth/register` | Create account |
| POST | `/api/v1/auth/login` | Get access + refresh tokens |
| POST | `/api/v1/auth/refresh` | Rotate access token |
| GET | `/api/v1/auth/profile` | Current user |
| PUT | `/api/v1/auth/profile` | Update profile |
| POST | `/api/v1/auth/logout` | Revoke session |

## Events

Published: `auth.user.registered`, `auth.user.updated` (Kafka).

## Stack

Java 21, Spring Boot 3.4, Spring Security (OAuth2 Resource Server + BCrypt), Liquibase (schema `auth`), PostgreSQL, Kafka.

## Development

```bash
./mvnw spring-boot:run
# http://localhost:8081
```

Full local orchestration lives in the `sdlc` repo (`make dev`).