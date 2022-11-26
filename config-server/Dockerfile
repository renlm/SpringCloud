FROM openjdk:8-jdk-alpine
ADD target/config-server-0.0.1.jar app.jar
VOLUME /tmp
VOLUME /target
RUN bash -c 'touch /app.jar'
EXPOSE 7000
ARG SPRING_PROFILES_ACTIVE
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]