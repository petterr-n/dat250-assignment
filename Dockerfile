FROM gradle:8.10.2-jdk21 AS builder

LABEL authors="petter"

WORKDIR /home/gradle

COPY settings.gradle.kts gradlew build.gradle.kts ./

COPY src src
COPY gradle gradle

RUN ./gradlew bootJar

RUN mv build/libs/demo-0.0.1-SNAPSHOT.jar app.jar

FROM eclipse-temurin:21-alpine

RUN addgroup -g 1000 app
RUN adduser -G app -D -u 1000 -h /app app

USER app
WORKDIR /app

COPY --from=builder --chown=1000:1000 /home/gradle/app.jar .

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
