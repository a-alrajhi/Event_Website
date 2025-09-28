# Use Java 21 base image
FROM openjdk:21-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled WAR file into the image
COPY target/Event_Website-0.0.1-SNAPSHOT.war app.war

# Expose port (Spring Boot default)
EXPOSE 8080

# Run the WAR file when the container starts
ENTRYPOINT ["java", "-jar", "app.war"]