package cn.renlm.springcloud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;

/**
 * 共享会话
 * 
 * @author Renlm
 *
 */
@EnableRedisWebSession
@Configuration(proxyBeanMethods = false)
public class SessionConfig {

}