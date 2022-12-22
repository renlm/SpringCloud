package cn.renlm.springcloud.demo.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import cn.renlm.springcloud.demo.dto.GeoJson;
import cn.renlm.springcloud.response.Result;

/**
 * 行政区划
 * 
 * @author Renlm
 *
 */
@FeignClient(contextId = "remoteGeoService", path = "/geo", value = "demo-api")
public interface RemoteGeoService {

	/**
	 * 获取中国行政区划
	 * 
	 * @return
	 */
	@GetMapping(value = "/getAllChinese", headers = "from=Y")
	Result<List<GeoJson>> getAllChinese();

}
