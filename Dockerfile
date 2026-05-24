FROM maven:3.8-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/project-bridge-1.0-SNAPSHOT.jar app.jar
# Create the projects directory
RUN mkdir -p /app/projects
ENTRYPOINT ["java", "-jar", "app.jar"]
