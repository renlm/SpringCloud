FROM openjdk:17-jdk-alpine
ADD target/EurekaServer.jar app.jar
RUN touch /app.jar
EXPOSE 80
EXPOSE 9000
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]