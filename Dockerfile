FROM eclipse-temurin:21-jdk

WORKDIR /app
COPY . .

# Set PORT environment variable (used by Spring Boot)
ENV PORT 8080

# Run Maven build
RUN ./mvnw clean install

# Expose port (Render listens on 8080)
EXPOSE 8080

# Tell Spring Boot to use the right port
CMD ["java", "-Dserver.port=8080", "-jar", "target/whatsapp-chatbot-0.0.1-SNAPSHOT.jar"]
