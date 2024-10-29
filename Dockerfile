# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file into the container
COPY target/demo-0.0.1-SNAPSHOT.war demo.war

# Expose the application port (adjust if different)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "demo.war"]
