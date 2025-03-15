# Use official Java 23 runtime as base image
FROM openjdk:23-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built JAR file
COPY target/airline-management-system-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app uses (default is 8080)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]