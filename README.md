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

Example curl commands to test the API:

#### Get file attributes:
```
curl --header "Content-Type: application/json" \
--request GET \
http://localhost:8080/api/file/1234/attributes
```

#### Save file attributes:
```
curl --header "Content-Type: application/json" \
--request POST \
--data '{"name":"name","description":"description","fileType":"type","customAttributes":{"customAttribute2":"customAttribute2Value","customAttribute1":"customAttribute1Value","customAttribute3":"customAttribute3Value"}}'  \
http://localhost:8080/api/file/1234/attributes
```

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