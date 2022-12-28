package cn.renlm.springcloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import cn.renlm.springcloud.filter.GiteeSignAuthenticationFilter;
import cn.renlm.springcloud.filter.GithubSignAuthenticationFilter;

/**
 * 
 * Security 配置
 * 
 * @author Renlm
 *
 */
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class WebSecurityConfig {

	@Autowired
	private GiteeSignAuthenticationFilter giteeSignAuthenticationFilter;

	@Autowired
	private GithubSignAuthenticationFilter githubSignAuthenticationFilter;

	@Bean
	public SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
		http.addFilterBefore(giteeSignAuthenticationFilter, BasicAuthenticationFilter.class);
		http.addFilterBefore(githubSignAuthenticationFilter, BasicAuthenticationFilter.class);
		http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
		http.csrf().disable();
		http.httpBasic();
		http.formLogin();
		return http.build();
	}

}
