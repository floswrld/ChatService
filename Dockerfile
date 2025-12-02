FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY ./target/ChatService-1.0-SNAPSHOT.jar app.jar

EXPOSE 5001

ENTRYPOINT ["java", "-jar", "app.jar"]