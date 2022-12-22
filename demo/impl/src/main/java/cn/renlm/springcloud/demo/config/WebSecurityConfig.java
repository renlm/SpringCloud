package cn.renlm.springcloud.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.AntPathMatcher;

import cn.renlm.springcloud.demo.properties.SecurityIgnoreProperties;

/**
 * 
 * Security 配置
 * 
 * @author Renlm
 *
 */
@EnableWebSecurity
public class WebSecurityConfig {

	final AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Bean
	public SecurityFilterChain securityWebFilterChain(HttpSecurity http,
			SecurityIgnoreProperties securityIgnoreProperties) throws Exception {
		http.authorizeHttpRequests().requestMatchers().permitAll();
		http.csrf().disable();
		return http.build();
	}

}
