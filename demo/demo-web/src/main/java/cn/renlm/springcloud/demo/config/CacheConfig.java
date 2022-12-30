package cn.renlm.springcloud.demo.config;

import java.time.Duration;
import java.util.ArrayList;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * 缓存配置
 * 
 * @author Renlm
 *
 */
@Configuration(proxyBeanMethods = false)
public class CacheConfig {

	public enum CacheEnum {

		DEFAULT_CACHE(300, 10000, 200);

		private int second;
		private long maxSize;
		private int initSize;

		CacheEnum(int second, long maxSize, int initSize) {
			this.second = second;
			this.maxSize = maxSize;
			this.initSize = initSize;
		}

	}

	@Bean("caffeineCacheManager")
	public CacheManager cacheManager() {
		SimpleCacheManager cacheManager = new SimpleCacheManager();
		ArrayList<CaffeineCache> caffeineCaches = new ArrayList<>();
		for (CacheEnum cacheEnum : CacheEnum.values()) {
			caffeineCaches.add(new CaffeineCache(cacheEnum.name(),
					Caffeine.newBuilder()
						.expireAfterWrite(Duration.ofSeconds(cacheEnum.second))
						.initialCapacity(cacheEnum.initSize)
						.maximumSize(cacheEnum.maxSize)
						.build()
				));
		}
		cacheManager.setCaches(caffeineCaches);
		return cacheManager;
	}

}
