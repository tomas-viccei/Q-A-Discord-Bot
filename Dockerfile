FROM maven:3.9.6-eclipse-temurin-21-alpine as build

WORKDIR /build
COPY . .

RUN mvn package -DskipTests

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app
COPY --from=build /build/target/discord-q-a-bot-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 10000
ENTRYPOINT ["java", "-jar", "app.jar"]