FROM eclipse-temurin:22-jdk
WORKDIR /app

# Copy your already built JAR
COPY target/test_task_exirom-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
