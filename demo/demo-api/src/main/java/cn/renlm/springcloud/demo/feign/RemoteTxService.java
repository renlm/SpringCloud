package cn.renlm.springcloud.demo.feign;

import static cn.renlm.springcloud.constant.ServiceNameConstants.DEMO_SERVICE;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.renlm.springcloud.response.Result;

/**
 * 分布式事物测试
 * 
 * @author Renlm
 *
 */
@FeignClient(contextId = "remoteTxService", name = DEMO_SERVICE)
public interface RemoteTxService {

	/**
	 * 分布式事物测试表1
	 * 
	 * @param n
	 */
	@PostMapping(value = "/tx/addTx1")
	Result<String> addTx1(@RequestParam("n") int n);

	/**
	 * 分布式事物测试表2
	 * 
	 * @param n
	 */
	@PostMapping(value = "/tx/addTx2")
	Result<String> addTx2(@RequestParam("n") int n);

}
