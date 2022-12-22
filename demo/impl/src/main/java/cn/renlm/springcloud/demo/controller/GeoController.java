package cn.renlm.springcloud.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.renlm.springcloud.demo.dto.GeoDto;
import cn.renlm.springcloud.demo.service.GeoService;
import cn.renlm.springcloud.response.Result;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/geo")
public class GeoController {
	
	private final GeoService geoService;
	
	/**
	 * 获取中国行政区划
	 * 
	 * @return
	 */
	@GetMapping("/getChinese")
	public Result<List<GeoDto>> token() {
		List<GeoDto> list = geoService.getChinese();
		return Result.success(list);
	}

}
