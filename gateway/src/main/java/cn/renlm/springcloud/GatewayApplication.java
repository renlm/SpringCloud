package cn.renlm.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 启动类
 * 
 * @author Renlm
 *
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = { ReactiveUserDetailsServiceAutoConfiguration.class })
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(GatewayApplication.class);
		springApplication.run(args);
	}

}
