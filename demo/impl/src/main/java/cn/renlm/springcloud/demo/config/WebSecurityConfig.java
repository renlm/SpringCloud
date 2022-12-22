package cn.renlm.springcloud.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;

import cn.renlm.springcloud.demo.properties.SecurityIgnoreProperties;
import jakarta.servlet.http.HttpServletRequest;

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
		http.authorizeHttpRequests().requestMatchers(new RequestMatcher() {
			
			@Override
			public boolean matches(HttpServletRequest request) {
				return false;
			}
			
		}).permitAll();
		http.csrf().disable();
		return http.build();
	}

}
