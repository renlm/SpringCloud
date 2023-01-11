FROM registry.cn-hangzhou.aliyuncs.com/rlm/graalvm-ce:ol7-java17-22.3.0-b2

ADD . /build
WORKDIR /build/eureka-server

ARG SPRING_PROFILES_ACTIVE
ENV ACTIVE_PROFILES_SPRING ${SPRING_PROFILES_ACTIVE}

# 开启BuildKit，缓存maven仓库
# https://docs.docker.com/build/buildkit/#getting-started
RUN --mount=type=cache,target=/root/.m2 \
    source "$HOME/$SDKMAN_INIT_SH" && mvn -Pnative -P ${SPRING_PROFILES_ACTIVE} native:compile

FROM oraclelinux:7-slim

MAINTAINER RenLiMing

COPY --from=0 "/build/eureka-server/target/EurekaServer" EurekaServer

CMD [ "sh", "-c", "./EurekaServer" ]