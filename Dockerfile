# Use Eclipse Temurin JDK 21 base image
FROM eclipse-temurin:21-jdk-alpine

# Instala Maven
RUN apk add --no-cache maven

# Crea un directorio de trabajo
WORKDIR /app

# Copia el proyecto completo
COPY . .

# Compila el proyecto
RUN mvn clean package -DskipTests

# Expone el puerto si hace falta
EXPOSE 8080

# Corre la app
CMD ["java", "-jar", "target/discord-q-a-bot-0.0.1-SNAPSHOT.jar"]
