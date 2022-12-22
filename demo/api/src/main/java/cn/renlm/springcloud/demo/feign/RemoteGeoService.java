package cn.renlm.springcloud.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(contextId = "remoteGeoService", value = "demo-api")
public class RemoteGeoService {
	
}
