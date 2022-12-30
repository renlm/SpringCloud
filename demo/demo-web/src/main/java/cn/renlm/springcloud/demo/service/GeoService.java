package cn.renlm.springcloud.demo.service;

import java.util.List;

import cn.renlm.springcloud.demo.dto.GeoDto;

/**
 * 行政区划
 * 
 * @author Renlm
 *
 */
public interface GeoService {

	/**
	 * 获取中国省市地图坐标数据
	 * 
	 * @return
	 */
	List<GeoDto> getChinese();

}
