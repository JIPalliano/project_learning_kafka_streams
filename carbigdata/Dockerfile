
FROM gradle:8.5-jdk17-alpine AS builder

WORKDIR /app
COPY build.gradle settings.gradle ./
COPY src ./src

RUN gradle build -x test --no-daemon

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

ENV SERVER_PORT=8080

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]