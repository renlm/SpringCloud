package cn.renlm.springcloud.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * 默认安全配置
 * 
 * @author Renlm
 *
 */
@EnableWebFluxSecurity
@Configuration(proxyBeanMethods = false)
public class OAuth2SecurityConfig {

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http
			.authorizeExchange(authorize -> 
				authorize
					.anyExchange()
					.authenticated()
			)
			.oauth2Login(withDefaults())
			.cors().disable();
		return http.build();
	}

}