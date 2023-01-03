package cn.renlm.springcloud.demo.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.renlm.springcloud.demo.entity.Tx1;
import cn.renlm.springcloud.demo.mapper.Tx1Mapper;
import cn.renlm.springcloud.demo.service.ITx1Service;

/**
 * <p>
 * 分布式事物测试表1 服务实现类
 * </p>
 *
 * @author Renlm
 * @since 2023-01-03
 */
@Service
public class Tx1ServiceImpl extends ServiceImpl<Tx1Mapper, Tx1> implements ITx1Service {

	@Override
	@Transactional
	public void addTx1(int n) {
		Tx1 tx1 = this.getById(1);
		tx1.setM(tx1.getM() - n);
		tx1.setUpdatedAt(new Date());
		this.updateById(tx1);
	}

}
