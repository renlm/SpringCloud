## 注册中心
## 生成 Native Image 反射配置
	上传eureka-server-0.0.1.jar到Docker所在服务器
	$ mkdir native-image
	
```
$ docker pull registry.cn-hangzhou.aliyuncs.com/rlm/graalvm-ce:ol7-java17-22.3.0-b2
$ docker run -it --rm -v /root/native-image:/app registry.cn-hangzhou.aliyuncs.com/rlm/graalvm-ce:ol7-java17-22.3.0-b2 bash
bash-4.2# java -agentlib:native-image-agent=config-merge-dir=./ -jar eureka-server-0.0.1.jar
```

	将生成配置拷贝到项目src/main/resources/META-INF/native-image目录下

```	
.
|-- META-INF
|   `-- native-image
|       |-- jni-config.json
|       |-- predefined-classes-config.json
|       |-- proxy-config.json
|       |-- reflect-config.json
|       |-- resource-config.json
|       `-- serialization-config.json
```