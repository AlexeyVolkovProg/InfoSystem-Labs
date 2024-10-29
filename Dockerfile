FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY target/firstLabIS-1.0.0.jar /app/firstLabIS-1.0.0.jar
ENTRYPOINT ["java", "-jar", "firstLabIS-1.0.0.jar"]
EXPOSE 8080