## 注册中心
## 生成 Native Image 反射配置
	上传eureka-server-0.0.1.jar
	$ mkdir native-image
	
```
$ docker pull registry.cn-hangzhou.aliyuncs.com/rlm/graalvm-ce:ol7-java17-22.3.0-b2
$ docker run -it --rm -v /root/native-image:/app registry.cn-hangzhou.aliyuncs.com/rlm/graalvm-ce:ol7-java17-22.3.0-b2 bash
bash-4.2# nohup java -agentlib:native-image-agent=config-merge-dir=./ -jar eureka-server-0.0.1.jar > log.file 2>&1 &
bash-4.2# curl http://localhost:7001
bash-4.2# curl http://localhost:7001/eureka/apps
bash-4.2# curl http://localhost:7001/actuator/prometheus
```

	拷贝到resources/META-INF/native-image
	自动加载

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