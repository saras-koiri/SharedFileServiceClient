# Shared File Upload Client Microservice

A Spring Boot microservice that calls a Shared File Upload Service

## Prerequisites

- JDK 21 or later
- Maven 3.9.x or later

## Quick Start 

3. Build the application:
```bash
./mvnw clean package
```

4. Run locally:
```bash
./mvnw spring-boot:run
```

The service will be available at `http://localhost:8080`

## Development

### Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/yourorg/userservice/
│   │       ├── controller/             # REST controllers
│   │       ├── model/                  # Domain models
│   │       ├── service/                # Business logic
│   │       └── sharedfileservice/      # Shared File Service Component
│   └── resources/
│       └── application.properties   # Application configuration
└── test/                     # Test files
```

### Running Tests
```bash
# Run unit tests
./mvnw test

# Run integration tests
./mvnw verify

## Deployment

### Docker Deployment

1. Build the Docker image:
```bash
docker build -t demo-app:latest .
```

2. Run the container:
```bash
docker run -p 8080:8080 \
  demo-app:latest
```