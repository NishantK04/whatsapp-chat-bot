# Use official Java 21 JDK
FROM eclipse-temurin:21-jdk

# Set working directory inside container
WORKDIR /app

# Copy your code
COPY . .

# Build the project with Maven Wrapper
RUN ./mvnw clean install

# Expose default port for Spring Boot
EXPOSE 8080

# Run the built jar file
CMD ["java", "-jar", "target/whatsapp-chatbot-0.0.1-SNAPSHOT.jar"]
