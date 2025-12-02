FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY ./target/ChatService.jar app.jar

EXPOSE 5001

ENTRYPOINT ["java", "-jar", "app.jar"]