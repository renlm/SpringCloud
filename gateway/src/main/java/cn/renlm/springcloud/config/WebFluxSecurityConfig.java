package cn.renlm.springcloud.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.net.URI;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;

import cn.renlm.springcloud.properties.SecurityIgnoreProperties;
import reactor.core.publisher.Mono;

/**
 * Security 配置
 * 
 * @author Renlm
 *
 */
@EnableWebFluxSecurity
@Configuration(proxyBeanMethods = false)
@EnableRedisWebSession(redisNamespace = "gateway:session")
public class WebFluxSecurityConfig {

	private final AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
			SecurityIgnoreProperties securityIgnoreProperties) {
		http.authorizeExchange().matchers(new ServerWebExchangeMatcher() {

			@Override
			public Mono<MatchResult> matches(ServerWebExchange exchange) {
				List<String> whites = securityIgnoreProperties.getWhites();
				ServerHttpRequest request = exchange.getRequest();
				URI uri = request.getURI();
				if (whites == null) {
					return MatchResult.notMatch();
				}
				for (String path : whites) {
					if (antPathMatcher.match(path, uri.getPath())) {
						return MatchResult.match();
					}
				}
				return MatchResult.notMatch();
			}

		}).permitAll()
		.pathMatchers(HttpMethod.OPTIONS).permitAll()
		.anyExchange().authenticated();
		http.oauth2Login(withDefaults());
		http.csrf().disable();
		return http.build();
	}

}
