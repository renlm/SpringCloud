FROM registry.cn-hangzhou.aliyuncs.com/rlm/graalvm-ce:ol7-java17-22.3.0-b2

ADD ../. /build
WORKDIR /build/eureka-server

RUN ls /build

RUN source "$HOME/.sdkman/bin/sdkman-init.sh" && mvn -B clean package -P prod --no-transfer-progress

FROM oraclelinux:7-slim

MAINTAINER Renlm

COPY --from=0 "/build/target/EurekaServer" EurekaServer

CMD [ "sh", "-c", "./EurekaServer" ]