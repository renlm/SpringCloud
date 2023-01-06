FROM openjdk:17-jdk-alpine
ADD target/DemoWeb.jar app.jar
RUN touch /app.jar
EXPOSE 80
EXPOSE 9000
ARG SPRING_PROFILES_ACTIVE
ENV ACTIVE_PROFILES_SPRING ${SPRING_PROFILES_ACTIVE}
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.profiles.active=${ACTIVE_PROFILES_SPRING}"]