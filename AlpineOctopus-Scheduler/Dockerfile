# syntax=docker/dockerfile:1

FROM eclipse-temurin:11-alpine

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve

COPY src ./src

Run ./mvnw clean install

CMD ["./mvnw", "cargo:run"]
