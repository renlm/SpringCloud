package cn.renlm.springcloud.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cn.renlm.springcloud.filter.WebHookAuthenticationFilter;

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
	public SecurityFilterChain securityWebFilterChain(HttpSecurity http,
			WebHookAuthenticationFilter webHookAuthenticationFilter) throws Exception {
		http.addFilterBefore(webHookAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
		http.httpBasic(withDefaults());
		http.csrf().disable();
		return http.build();
	}

}
