package cn.renlm.springcloud.seata;

import static io.seata.common.DefaultValues.DEFAULT_LOAD_BALANCE;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.seata.core.context.RootContext;

/**
 * Seata 分布式事物传递
 * 
 * @author Renlm
 *
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(value = { RequestInterceptor.class, RootContext.class })
public class FeignSeataInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate requestTemplate) {
		requestTemplate.header(DEFAULT_LOAD_BALANCE, RootContext.getXID());
	}

}
