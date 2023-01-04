package cn.renlm.springcloud.seata;

import static io.seata.common.DefaultValues.DEFAULT_LOAD_BALANCE;

import java.io.IOException;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

import io.seata.common.util.StringUtils;
import io.seata.core.context.RootContext;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Seata 分布式事物传递
 * 
 * @author Renlm
 *
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(value = { Filter.class, RootContext.class })
public class ServletSeataFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String xid = httpServletRequest.getHeader(DEFAULT_LOAD_BALANCE);
		if (StringUtils.isNotBlank(xid)) {
			RootContext.bind(xid);
		}
		chain.doFilter(request, response);
	}

}
