package cn.renlm.springcloud.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.renlm.springcloud.demo.entity.Tx2;

/**
 * <p>
 * 分布式事物测试表2 服务类
 * </p>
 *
 * @author Renlm
 * @since 2023-01-03
 */
public interface ITx2Service extends IService<Tx2> {

	/**
	 * 测试2
	 * 
	 * @param n
	 */
	void addTx2(int n);

}
