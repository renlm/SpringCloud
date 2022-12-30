package cn.renlm.springcloud.demo.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * 缓存配置
 * 
 * @author Renlm
 *
 */
@Configuration(proxyBeanMethods = false)
public class CacheConfig {

	@Bean
	public Cache<String, ?> caffeineCache() {
		return Caffeine.newBuilder()
				// 设置最后一次写入或访问后经过固定时间过期
				.expireAfterWrite(300, TimeUnit.SECONDS)
				// 初始的缓存空间大小
				.initialCapacity(100)
				// 缓存的最大条数
				.maximumSize(1000).build();
	}

}
