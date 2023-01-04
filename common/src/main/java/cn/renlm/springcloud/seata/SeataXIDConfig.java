package cn.renlm.springcloud.seata;

import static io.seata.common.DefaultValues.DEFAULT_LOAD_BALANCE;

import java.io.IOException;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
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
@ConditionalOnClass(value = { RootContext.class })
public class SeataXIDConfig {

	@Bean
	@ConditionalOnClass(value = { RequestInterceptor.class })
	public RequestInterceptor feignSeataInterceptor() {
		return new RequestInterceptor() {

			@Override
			public void apply(RequestTemplate requestTemplate) {
				String xid = RootContext.getXID();
				if (StringUtils.isNotBlank(xid)) {
					requestTemplate.header(DEFAULT_LOAD_BALANCE, xid);
				}
			}

		};
	}

	@Bean
	@ConditionalOnClass(value = { Filter.class })
	public Filter servletSeataFilter() {
		return new Filter() {

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

		};
	}

}
