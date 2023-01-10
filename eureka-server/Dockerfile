FROM registry.cn-hangzhou.aliyuncs.com/rlm/graalvm-ce:ol7-java17-22.3.0-b2
ADD target/EurekaServer.jar app.jar
RUN touch /app.jar
EXPOSE 80
EXPOSE 9000
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]