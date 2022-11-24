package cn.renlm.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 启动类
 * 
 * @author Renlm
 *
 */
@EnableEurekaServer
@SpringBootApplication
public class NetflixEurekaServerApplication {
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(NetflixEurekaServerApplication.class);
		springApplication.run(args);
	}
}