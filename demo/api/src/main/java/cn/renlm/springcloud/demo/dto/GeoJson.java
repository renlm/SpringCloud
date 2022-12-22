package cn.renlm.springcloud.demo.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 行政区划
 * 
 * @author Renlm
 *
 */
@Getter
@Setter
public class GeoJson implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 编码
	 */
	private String adcode;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 层级
	 */
	private String level;

	/**
	 * 中心
	 */
	private double[] center;

	/**
	 * 形心
	 */
	private double[] centroid;

}
