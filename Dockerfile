# Use official Java image
FROM eclipse-temurin:21-jdk

# Set working directory
WORKDIR /app

# Copy everything into container
COPY . .

# Give Maven wrapper permission
RUN chmod +x mvnw

# Build the Spring Boot app
RUN ./mvnw clean package -DskipTests

# Expose port 8080 (Render will use it)
EXPOSE 8080

# Run the app (explicitly set port)
CMD ["java", "-Dserver.port=8080", "-jar", "target/whatsapp-chatbot-0.0.1-SNAPSHOT.jar"]
