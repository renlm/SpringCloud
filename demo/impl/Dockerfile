FROM openjdk:17-jdk-alpine
ADD target/gateway-0.0.1.jar app.jar
VOLUME /tmp
RUN touch /app.jar
EXPOSE 7002
ARG SPRING_PROFILES_ACTIVE
ENV ACTIVE_PROFILES_SPRING ${SPRING_PROFILES_ACTIVE}
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.profiles.active=${ACTIVE_PROFILES_SPRING}"]