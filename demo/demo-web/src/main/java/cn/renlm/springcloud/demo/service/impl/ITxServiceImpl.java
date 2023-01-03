package cn.renlm.springcloud.demo.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import cn.renlm.springcloud.demo.entity.Tx1;
import cn.renlm.springcloud.demo.entity.Tx2;
import cn.renlm.springcloud.demo.feign.RemoteTxService;
import cn.renlm.springcloud.demo.service.ITx1Service;
import cn.renlm.springcloud.demo.service.ITx2Service;
import cn.renlm.springcloud.demo.service.ITxService;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;

/**
 * 分布式事物测试
 * 
 * @author Renlm
 *
 */
@Service
public class ITxServiceImpl implements ITxService {

	@Resource
	private ITx1Service iTx1Service;

	@Resource
	private ITx2Service iTx2Service;

	@Resource
	private RemoteTxService remoteTxService;

	@Override
	@GlobalTransactional
	public void addLocal(int n) {
		// Tx1
		Tx1 tx1 = iTx1Service.getById(1);
		tx1.setM(tx1.getM() - n);
		tx1.setUpdatedAt(new Date());
		iTx1Service.updateById(tx1);
		// Tx2
		Tx2 tx2 = iTx2Service.getById(1);
		tx2.setM(tx2.getM() - n);
		tx2.setUpdatedAt(new Date());
		iTx2Service.updateById(tx2);
		// 异常
		if (n % 2 == 0) {
			throw new RuntimeException("n 不能为偶数");
		}
	}

	@Override
	@GlobalTransactional
	public void addRemote(int n) {
		// Tx1
		remoteTxService.addTx1(n);
		// Tx2
		remoteTxService.addTx2(n);
		// 异常
		if (n % 2 == 0) {
			throw new RuntimeException("n 不能为偶数");
		}
	}

}
