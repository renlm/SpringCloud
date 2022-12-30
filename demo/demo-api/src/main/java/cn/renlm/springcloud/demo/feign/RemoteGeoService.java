package cn.renlm.springcloud.demo.feign;

import static cn.renlm.springcloud.constant.ServiceNameConstants.DEMO_SERVICE;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import cn.renlm.springcloud.demo.dto.GeoDto;
import cn.renlm.springcloud.response.Result;

/**
 * 地图坐标
 * 
 * @author Renlm
 *
 */
@FeignClient(contextId = "remoteGeoService", name = DEMO_SERVICE)
public interface RemoteGeoService {

	/**
	 * 获取中国省市地图坐标数据
	 * 
	 * @return
	 */
	@GetMapping(value = "/geo/getChinese")
	Result<List<GeoDto>> getChinese();

}
