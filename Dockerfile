FROM openjdk:17

COPY target/auction-app.jar /app.jar

EXPOSE 8080/tcp

ENTRYPOINT ["java", "-jar", "/app.jar"]