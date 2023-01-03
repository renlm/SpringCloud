package cn.renlm.springcloud.demo.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.renlm.springcloud.demo.entity.Tx2;
import cn.renlm.springcloud.demo.mapper.Tx2Mapper;
import cn.renlm.springcloud.demo.service.ITx2Service;

/**
 * <p>
 * 分布式事物测试表2 服务实现类
 * </p>
 *
 * @author Renlm
 * @since 2023-01-03
 */
@Service
@DS("postgres")
public class Tx2ServiceImpl extends ServiceImpl<Tx2Mapper, Tx2> implements ITx2Service {

	@Override
	@Transactional
	public void addTx2(int n) {
		Tx2 tx2 = this.getById(1);
		tx2.setM(tx2.getM() + n);
		tx2.setUpdatedAt(new Date());
		this.updateById(tx2);
	}

}
