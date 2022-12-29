package cn.renlm.springcloud.demo.feign;

import static cn.renlm.springcloud.constant.ServiceNameConstants.DEMO_SERVICE;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import cn.renlm.springcloud.demo.dto.GeoDto;
import cn.renlm.springcloud.response.Result;

/**
 * 行政区划
 * 
 * @author Renlm
 *
 */
@FeignClient(contextId = DEMO_SERVICE, path = "/geo")
public interface RemoteGeoService {

	/**
	 * 获取中国行政区划
	 * 
	 * @return
	 */
	@GetMapping(value = "/getChinese")
	Result<List<GeoDto>> getChinese();

}
