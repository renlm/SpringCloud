package cn.renlm.springcloud.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Opaquetoken;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.server.resource.introspection.NimbusReactiveOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;

/**
 * Security 配置
 * 
 * @author Renlm
 *
 */
@EnableWebFluxSecurity
@Configuration(proxyBeanMethods = false)
@EnableRedisWebSession(redisNamespace = "gateway:session")
@ImportAutoConfiguration({ RestTemplateAutoConfiguration.class })
public class WebFluxSecurityConfig {

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http.authorizeExchange().anyExchange().authenticated();
		http.oauth2ResourceServer().opaqueToken();
		http.oauth2Login(withDefaults());
		return http.build();
	}

	@Bean
	public ReactiveOpaqueTokenIntrospector introspector(ReactiveClientRegistrationRepository repository,
			OAuth2ResourceServerProperties properties) {
		Opaquetoken opaquetoken = properties.getOpaquetoken();
		String uri = opaquetoken.getIntrospectionUri();
		String id = opaquetoken.getClientId();
		String secret = opaquetoken.getClientSecret();
		NimbusReactiveOpaqueTokenIntrospector delegate = new NimbusReactiveOpaqueTokenIntrospector(uri, id, secret);
		return token -> delegate.introspect(token);
	}

}
