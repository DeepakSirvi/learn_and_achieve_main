# Stage 1: Build the Maven project
FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app

COPY . .

# Build the Maven project
RUN mvn clean package

# Stage 2: Create the final image
FROM openjdk:17-jdk-slim

EXPOSE 8080

WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=build /app/target/demo-1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
