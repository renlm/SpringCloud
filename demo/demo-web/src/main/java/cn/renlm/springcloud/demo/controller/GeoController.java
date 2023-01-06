package cn.renlm.springcloud.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.renlm.plugins.MyResponse.Result;
import cn.renlm.springcloud.demo.dto.GeoDto;
import cn.renlm.springcloud.demo.feign.RemoteGeoService;
import cn.renlm.springcloud.demo.service.GeoService;
import lombok.RequiredArgsConstructor;

/**
 * 行政区划
 * 
 * @author Renlm
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/geo")
public class GeoController {

	private final GeoService geoService;

	private final RemoteGeoService remoteGeoService;

	/**
	 * 获取中国省市地图坐标数据
	 * 
	 * @return
	 */
	@GetMapping("/getChinese")
	public Result<List<GeoDto>> getChinese() {
		List<GeoDto> list = geoService.getChinese();
		return Result.success(list);
	}

	/**
	 * 获取中国省市地图坐标数据
	 * 
	 * @return
	 */
	@GetMapping("/getChineseByOpenfeign")
	public Result<List<GeoDto>> getChineseByOpenfeign() {
		return remoteGeoService.getChinese();
	}

}
