# Use a Gradle image to build the application
FROM gradle:8.10.2-jdk17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle build files and source code
COPY build.gradle settings.gradle ./
COPY src ./src

# Build the project and create the JAR file
RUN gradle clean build -x test

# Use a smaller JDK image to run the JAR file
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/build/libs/yuvro.jar yuvro.jar

# Expose the port your application will run on
#EXPOSE 8080

# Command to run the JAR file
#ENTRYPOINT ["java", "-jar", "yuvro.jar"]


# Install curl
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copy wait script
COPY wait-for-keycloak.sh /app/wait-for-keycloak.sh
RUN chmod +x /app/wait-for-keycloak.sh

# Verify the file exists in /app/
RUN ls -l /app/

# Use wait script as entrypoint
ENTRYPOINT ["/app/wait-for-keycloak.sh"]