FROM openjdk:17-jdk-alpine
ADD target/config-server-0.0.1.jar app.jar
VOLUME /tmp
RUN touch /app.jar
EXPOSE 7000
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]