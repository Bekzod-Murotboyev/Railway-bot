# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-jammy as base
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve
COPY src ./src

FROM base as development
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=postgres"]

FROM base as build
RUN ./mvnw package

FROM eclipse-temurin:17-jre-jammy as production
EXPOSE 8082
COPY --from=build /app/target/e-ticket-railway_longPolling-*.jar /e-ticket-railway.jar
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/e-ticket-railway.jar"]
