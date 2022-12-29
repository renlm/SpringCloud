package cn.renlm.springcloud.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.renlm.springcloud.demo.dto.GeoDto;
import cn.renlm.springcloud.demo.service.GeoService;

/**
 * 行政区划
 * 
 * @author Renlm
 *
 */
public class GeoServiceImpl implements GeoService {

	@Override
	public List<GeoDto> getChinese() {
		List<GeoDto> list = new ArrayList<>();
		return list;
	}

}
