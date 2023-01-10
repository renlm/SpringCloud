## 简介
微服务集成案例

## Spring Cloud

```
https://spring.io/projects/spring-cloud
```

## 配置中心密文

```
$ keytool -genkeypair -alias keyStore -keyalg RSA \
    -dname "CN=Web Server,OU=Unit,O=Organization,L=City,S=Province,C=CN" \
    -keypass 123654 -keystore keyStore.jks -storepass 123654
```

```
# 可能有转义字符的问题，可用ApiPost工具规避
$ curl -X POST springCloud:123654@localhost:7000/encrypt -s -d {明文}
$ curl -X POST springCloud:123654@localhost:7000/decrypt -s -d {密文}
```

```
# 本地开发时，添加启动参数
--KEY_STORE_PASSWORD=123654
--KEY_STORE_ALIAS=keyStore
--KEY_STORE_SECRET=123654
```