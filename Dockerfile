# 使用阿里云镜像仓库海外机器构建
# https://cr.console.aliyun.com/cn-hangzhou/instances/mirrors
# https://github.com/graalvm/container/pkgs/container/graalvm-ce
FROM ghcr.io/graalvm/graalvm-ce:ol7-java17-22.3.0-b2
    
ENV SDKMAN_INIT_SH ".sdkman/bin/sdkman-init.sh"

RUN \
    # For SDKMAN to work we need
    yum install -y unzip zip; \
    # Install SDKMAN
    curl -s "https://get.sdkman.io" | bash; \
    source "$HOME/$SDKMAN_INIT_SH"; \
    sdk install maven; \
    # Install GraalVM Native Image
    gu install native-image;

RUN source "$HOME/$SDKMAN_INIT_SH" && mvn --version

RUN native-image --version