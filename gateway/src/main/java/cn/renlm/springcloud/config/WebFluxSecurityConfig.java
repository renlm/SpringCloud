package cn.renlm.springcloud.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.net.URI;
import java.time.Instant;
import java.util.List;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Opaquetoken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.ReactiveOAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.resource.introspection.NimbusReactiveOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector;
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
		http.oauth2ResourceServer().opaqueToken();
		http.oauth2Login(withDefaults());
		http.csrf().disable();
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
		ReactiveOAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService = new DefaultReactiveOAuth2UserService();
		return token -> {
			return Mono.zip(delegate.introspect(token), repository.findByRegistrationId(id)).map(t -> {
				OAuth2AuthenticatedPrincipal authorized = t.getT1();
				ClientRegistration clientRegistration = t.getT2();
				Instant issuedAt = authorized.getAttribute(OAuth2TokenIntrospectionClaimNames.IAT);
				Instant expiresAt = authorized.getAttribute(OAuth2TokenIntrospectionClaimNames.EXP);
				OAuth2AccessToken accessToken = new OAuth2AccessToken(TokenType.BEARER, token, issuedAt, expiresAt);
				return new OAuth2UserRequest(clientRegistration, accessToken);
			}).flatMap(oauth2UserService::loadUser);
		};
	}

}
