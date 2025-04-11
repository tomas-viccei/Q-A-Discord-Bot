# Use Eclipse Temurin JDK 21 base image
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the already-built JAR from local machine
COPY target/discord-q-a-bot-0.0.1-SNAPSHOT.jar app.jar

# Expose a port (optional for bot, not needed unless serving HTTP)
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
