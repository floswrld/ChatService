FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY chat-app.jar app.jar

EXPOSE 5001

ENTRYPOINT ["java", "-jar", "app.jar"]