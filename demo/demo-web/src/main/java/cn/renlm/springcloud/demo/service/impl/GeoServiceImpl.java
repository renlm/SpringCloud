package cn.renlm.springcloud.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.renlm.springcloud.demo.dto.GeoDto;
import cn.renlm.springcloud.demo.service.GeoService;

/**
 * 行政区划
 * 
 * @author Renlm
 *
 */
@Service
public class GeoServiceImpl implements GeoService {

	@Override
	public List<GeoDto> getChinese() {
		List<GeoDto> list = new ArrayList<>();
		return list;
	}

}
