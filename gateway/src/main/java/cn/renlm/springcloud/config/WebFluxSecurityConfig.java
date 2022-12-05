package cn.renlm.springcloud.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;

/**
 * Security 配置
 * 
 * @author Renlm
 *
 */
@EnableRedisWebSession
@EnableWebFluxSecurity
@Configuration(proxyBeanMethods = false)
public class WebFluxSecurityConfig {

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http.authorizeExchange().anyExchange().authenticated();
		http.oauth2ResourceServer().jwt();
		http.oauth2Login(withDefaults());
		return http.build();
	}

}
