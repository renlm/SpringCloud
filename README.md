## 简介
微服务集成案例

## Spring Cloud

```
https://spring.io/projects/spring-cloud
```

## 加密解密

```
$ keytool -genkeypair -alias keyStore -keyalg RSA \
    -dname "CN=Web Server,OU=Unit,O=Organization,L=City,S=Province,C=CN" \
    -keypass 123654 -keystore keyStore.jks -storepass 123654
```

```
# 此种方式可能遭遇转义字符问题，可用ApiPost工具规避
$ curl -X POST springCloud:123654@localhost:7000/encrypt -s -d {明文}
$ curl -X POST springCloud:123654@localhost:7000/decrypt -s -d {密文}
```