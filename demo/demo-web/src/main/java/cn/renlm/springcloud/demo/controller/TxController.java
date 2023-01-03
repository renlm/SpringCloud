package cn.renlm.springcloud.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.renlm.springcloud.demo.service.ITx1Service;
import cn.renlm.springcloud.demo.service.ITx2Service;
import cn.renlm.springcloud.demo.service.ITxService;
import cn.renlm.springcloud.response.Result;
import lombok.RequiredArgsConstructor;

/**
 * 分布式事物测试
 * 
 * @author Renlm
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/tx")
public class TxController {

	private final ITxService iTxService;

	private final ITx1Service iTx1Service;

	private final ITx2Service iTx2Service;

	/**
	 * 本地事物测试
	 * 
	 * @param n
	 * @return
	 */
	@GetMapping("/addLocal")
	public Result<String> addLocal(int n) {
		try {
			iTxService.addLocal(n);
			return Result.success();
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}

	/**
	 * 远程事物测试
	 * 
	 * @param n
	 * @return
	 */
	@GetMapping("/addRemote")
	public Result<String> addRemote(int n) {
		try {
			iTxService.addRemote(n);
			return Result.success();
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}

	/**
	 * 分布式事物测试表1
	 * 
	 * @param n
	 * @return
	 */
	@GetMapping("/addTx1")
	public Result<String> addTx1(int n) {
		try {
			iTx1Service.addTx1(n);
			return Result.success();
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error();
		}
	}

	/**
	 * 分布式事物测试表2
	 * 
	 * @param n
	 * @return
	 */
	@GetMapping("/addTx2")
	public Result<String> addTx2(int n) {
		try {
			iTx2Service.addTx2(n);
			return Result.success();
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error();
		}
	}

}
