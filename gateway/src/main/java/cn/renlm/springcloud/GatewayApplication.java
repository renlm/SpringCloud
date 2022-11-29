package cn.renlm.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;

/**
 * 启动类
 * 
 * @author Renlm
 *
 */
@SpringBootApplication(exclude = { ReactiveUserDetailsServiceAutoConfiguration.class })
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(GatewayApplication.class);
		springApplication.run(args);
	}

}