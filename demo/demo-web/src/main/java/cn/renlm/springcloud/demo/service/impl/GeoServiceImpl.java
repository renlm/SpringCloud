package cn.renlm.springcloud.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.benmanes.caffeine.cache.Cache;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.renlm.springcloud.demo.dto.GeoDto;
import cn.renlm.springcloud.demo.service.GeoService;
import jakarta.annotation.Resource;

/**
 * 行政区划
 * 
 * @author Renlm
 *
 */
@Service
public class GeoServiceImpl implements GeoService {

	@Resource
	private Cache<String, List<GeoDto>> caffeineCache;

	@Override
	public List<GeoDto> getChinese() {
		String uri = "https://geo.datav.aliyun.com/areas_v3/bound/";
		List<GeoDto> cached = caffeineCache.getIfPresent(uri);
		if (CollUtil.isNotEmpty(cached)) {
			return cached;
		}
		List<GeoDto> list = new ArrayList<>();
		HttpRequest httpRequest = HttpUtil.createGet(uri + "100000_full.json");
		HttpResponse httpResponse = httpRequest.execute();
		if (httpResponse.isOk()) {
			String body = httpResponse.body();
			if (JSONUtil.isTypeJSONObject(body)) {
				JSONObject obj = JSONUtil.parseObj(body);
				JSONArray arr = obj.getJSONArray("features");
				for (int i = 0; i < arr.size(); i++) {
					JSONObject province = arr.getJSONObject(i);
					String adcode = province.getByPath("properties.adcode", String.class);
					String name = province.getByPath("properties.name", String.class);
					String level = province.getByPath("properties.level", String.class);
					double[] center = province.getByPath("properties.center", double[].class);
					double[] centroid = province.getByPath("properties.centroid", double[].class);
					GeoDto level1 = new GeoDto();
					level1.setAdcode(adcode);
					level1.setName(name);
					level1.setLevel(level);
					level1.setCenter(center);
					level1.setCentroid(centroid);
					List<GeoDto> children = CollUtil.newArrayList();
					HttpRequest httpRequest2 = HttpUtil.createGet(uri + adcode + "_full.json");
					HttpResponse httpResponse2 = httpRequest2.execute();
					if (httpResponse2.isOk()) {
						String body2 = httpResponse2.body();
						if (JSONUtil.isTypeJSONObject(body2)) {
							JSONObject obj2 = JSONUtil.parseObj(body2);
							JSONArray arr2 = obj2.getJSONArray("features");
							for (int j = 0; j < arr2.size(); j++) {
								JSONObject city = arr2.getJSONObject(j);
								GeoDto level2 = new GeoDto();
								level2.setAdcode(city.getByPath("properties.adcode", String.class));
								level2.setName(city.getByPath("properties.name", String.class));
								level2.setLevel(city.getByPath("properties.level", String.class));
								level2.setCenter(city.getByPath("properties.center", double[].class));
								level2.setCentroid(city.getByPath("properties.centroid", double[].class));
								children.add(level2);
							}
						}
					}
					level1.setChildren(children);
					list.add(level1);
				}
			}
			caffeineCache.put(uri, list);
		}
		return list;
	}

}
