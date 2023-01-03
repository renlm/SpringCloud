package cn.renlm.springcloud.demo.service;

/**
 * 分布式事物测试
 * 
 * @author Renlm
 *
 */
public interface ITxService {

	/**
	 * 本地事物测试
	 * 
	 * @param n
	 */
	void addLocal(int n);

	/**
	 * 远程事物测试
	 * 
	 * @param n
	 */
	void addRemote(int n);

}
