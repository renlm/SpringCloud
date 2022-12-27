package cn.renlm.springcloud.filter;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import cn.renlm.springcloud.properties.WebHookProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

/**
 * WebHook 认证
 * 
 * @author Renlm
 *
 */
@Component
@AllArgsConstructor
public class WebHookAuthenticationFilter extends OncePerRequestFilter {

	private final WebHookProperties webHookProperties;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Authentication authentication = getContext().getAuthentication();
		String name = webHookProperties.getName();
		String password = webHookProperties.getPassword();
		String secret = request.getHeader(name);
		boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
		if (!isAuthenticated && StringUtils.hasText(secret) && secret.equals(password)) {
			Authentication token = new UsernamePasswordAuthenticationToken(name, password, Collections.emptySet());
			getContext().setAuthentication(token);
		}
		filterChain.doFilter(request, response);
	}
}
