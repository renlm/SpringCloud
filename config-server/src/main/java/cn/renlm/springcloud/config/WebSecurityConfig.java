package cn.renlm.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 
 * Security 配置
 * 
 * @author Renlm
 *
 */
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
		http.csrf().disable();
		return http.build();
	}

}
