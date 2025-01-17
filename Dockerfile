FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/example-ambar-destination-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8099

ENTRYPOINT ["java", "-jar", "app.jar"]
