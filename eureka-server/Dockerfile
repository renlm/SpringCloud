FROM openjdk:17-jdk-alpine
ADD target/eureka-server-0.0.1.jar app.jar
VOLUME /tmp
RUN touch /app.jar
EXPOSE 7001
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]