# Use Eclipse Temurin JDK 21 base image
FROM eclipse-temurin:21-jdk-alpine as build

WORKDIR /build
COPY . .

RUN ./mvnw package -DskipTests

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app
COPY --from=build /build/target/discord-q-a-bot-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 10000
ENTRYPOINT ["java", "-jar", "app.jar"]