FROM registry.cn-hangzhou.aliyuncs.com/rlm/graalvm-ce:ol7-java17-22.3.0-b2

ADD . /build
WORKDIR /build

# For SDKMAN to work we need unzip & zip
RUN yum install -y unzip zip

RUN \
    # Install SDKMAN
    curl -s "https://get.sdkman.io" | bash; \
    source "$HOME/.sdkman/bin/sdkman-init.sh"; \
    sdk install maven; \
    # Install GraalVM Native Image
    gu install native-image;

RUN source "$HOME/.sdkman/bin/sdkman-init.sh" && mvn --version

RUN native-image --version

RUN source "$HOME/.sdkman/bin/sdkman-init.sh" && mvn -B clean package -P native --no-transfer-progress


# We use a Docker multi-stage build here in order that we only take the compiled native Spring Boot App from the first build container
FROM oraclelinux:7-slim

MAINTAINER Jonas Hecht

# Add Spring Boot Native app spring-boot-graal to Container
COPY --from=0 "/build/target/spring-boot-graal" spring-boot-graal

# Fire up our Spring Boot Native app by default
CMD [ "sh", "-c", "./spring-boot-graal -Dserver.port=$PORT" ]