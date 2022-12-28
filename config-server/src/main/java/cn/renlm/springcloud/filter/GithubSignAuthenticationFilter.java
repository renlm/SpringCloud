package cn.renlm.springcloud.filter;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import cn.renlm.springcloud.properties.WebHookProperties;
import cn.renlm.springcloud.wrapper.RepeatableHttpServletRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

/**
 * WebHook 签名认证
 * 
 * @author Renlm
 *
 */
@Component
@AllArgsConstructor
public class GithubSignAuthenticationFilter extends OncePerRequestFilter {

	private static final String HEADER_KEY = "X-Github-Event";

	private static final String HEADER_VALUE = "push";

	private final WebHookProperties webHookProperties;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (HEADER_VALUE.equals(request.getHeader(HEADER_KEY))) {
			Authentication authentication = getContext().getAuthentication();
			boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
			String hmacSHA256 = request.getHeader("X-Hub-Signature-256");
			if (!isAuthenticated && StringUtils.hasText(hmacSHA256)) {
				HttpServletRequest requestWrapper = new RepeatableHttpServletRequest(request);
				byte[] bytes = IoUtil.readBytes(requestWrapper.getInputStream());
				String body = new String(bytes, request.getCharacterEncoding());
				String sign = this.sign(body, webHookProperties.getSignSecret());
				if (StrUtil.equals(sign, hmacSHA256)) {
					Collection<? extends GrantedAuthority> authorities = Collections.emptySet();
					Authentication token = new UsernamePasswordAuthenticationToken(HEADER_KEY, HEADER_VALUE,
							authorities);
					getContext().setAuthentication(token);
				}
				filterChain.doFilter(requestWrapper, response);
			} else {
				filterChain.doFilter(request, response);
			}
		} else {
			filterChain.doFilter(request, response);
		}
	}

	/**
	 * 签名
	 * 
	 * @param payload
	 * @param secret
	 * @return
	 */
	private String sign(String payload, String secret) {
		HMac hMac = DigestUtil.hmac(HmacAlgorithm.HmacSHA256, secret.getBytes());
		return "sha256=" + hMac.digestHex(payload);
	}

}
