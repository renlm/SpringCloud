package cn.renlm.springcloud.config;

import java.time.Instant;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Opaquetoken;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;

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
@ImportAutoConfiguration({ RestTemplateAutoConfiguration.class })
public class WebFluxSecurityConfig {

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http.authorizeExchange().anyExchange().authenticated();
		http.oauth2ResourceServer().opaqueToken();
		return http.build();
	}

	@Bean
	public ReactiveOpaqueTokenIntrospector introspector(ReactiveClientRegistrationRepository repository,
			OAuth2ResourceServerProperties properties) {
		Opaquetoken ot = properties.getOpaquetoken();
		NimbusReactiveOpaqueTokenIntrospector delegate = new NimbusReactiveOpaqueTokenIntrospector(
				ot.getIntrospectionUri(), ot.getClientId(), ot.getClientId());
		ReactiveOAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService = new DefaultReactiveOAuth2UserService();
		return token -> {
			return Mono.zip(delegate.introspect(token), repository.findByRegistrationId(ot.getClientId())).map(t -> {
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
