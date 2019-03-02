FROM openjdk:jre-slim

COPY build/libs/public-notification-1.0.0-SNAPSHOT.jar /app/app.jar
EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]