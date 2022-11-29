package cn.renlm.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 共享会话
 * 
 * @author Renlm
 *
 */
@EnableRedisWebSession
@Configuration
public class SessionConfig {

	 @Bean
	    public RedisSerializationContext<String, Object> redisSerializationContext() {
	        RedisSerializationContext.RedisSerializationContextBuilder<String, Object> builder =
	                RedisSerializationContext.newSerializationContext();
	        Jackson2JsonRedisSerializer<Object> redisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
	        ObjectMapper om = new ObjectMapper();
	        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
	        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
	        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
	        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
	        redisSerializer.setObjectMapper(om);

	        builder.key(new StringRedisSerializer());
	        builder.value(redisSerializer);
	        builder.hashKey(new StringRedisSerializer());
	        builder.hashValue(redisSerializer);

	        return builder.build();
	    }

	    @Bean
	    public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(ReactiveRedisConnectionFactory connectionFactory) {
	        return new ReactiveRedisTemplate<>(connectionFactory,redisSerializationContext());
	    }
	    
	    @Bean
	    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
	        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();

	        redisTemplate.setConnectionFactory(redisConnectionFactory);

	        // 设置key和value的序列化规则
	        redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
	        redisTemplate.setValueSerializer(RedisSerializer.json());

	        // 设置hashKey和hashValue的序列化规则
	        redisTemplate.setHashKeySerializer(StringRedisSerializer.UTF_8);

	        redisTemplate.setHashValueSerializer(RedisSerializer.json());
	        // 设置支持事物
	        redisTemplate.setEnableTransactionSupport(true);
	        redisTemplate.afterPropertiesSet();
	        return redisTemplate;
	    }

}