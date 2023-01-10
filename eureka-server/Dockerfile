FROM registry.cn-hangzhou.aliyuncs.com/rlm/graalvm-ce:ol7-java17-22.3.0-b2

ADD . /build
WORKDIR /build/eureka-server

RUN --mount=type=cache,target=/root/.m2 \
	source "$HOME/.sdkman/bin/sdkman-init.sh" && mvn clean package -P prod native:compile

FROM oraclelinux:7-slim

MAINTAINER RenLiMing

COPY --from=0 "/build/eureka-server/target/EurekaServer" EurekaServer

CMD [ "sh", "-c", "./EurekaServer" ]