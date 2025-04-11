# Use Eclipse Temurin JDK 21 base image
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/discord-q-a-bot-0.0.1-SNAPSHOT.jar app.jar

# Agregá esta línea para que Render detecte que el contenedor está "escuchando"
EXPOSE 10000

ENTRYPOINT ["java", "-jar", "app.jar"]
