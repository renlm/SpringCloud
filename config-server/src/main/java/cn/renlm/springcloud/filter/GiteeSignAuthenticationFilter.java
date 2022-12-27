package cn.renlm.springcloud.filter;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
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
public class GiteeSignAuthenticationFilter extends OncePerRequestFilter {

	private static final String HEADERS_KEY = "x-git-oschina-event";

	private static final String HEADERS_VALUE = "Push Hook";

	private final WebHookProperties webHookProperties;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (HEADERS_VALUE.equals(request.getHeader(HEADERS_KEY))) {
			Authentication authentication = getContext().getAuthentication();
			boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
			String giteeToken = request.getHeader("X-Gitee-Token");
			String giteeTimestamp = request.getHeader("X-Gitee-Timestamp");
			if (!isAuthenticated && StringUtils.hasText(giteeToken) && StringUtils.hasText(giteeTimestamp)) {
				if (giteeToken.equals(this.sign(giteeTimestamp, webHookProperties.getSignSecret()))) {
					Authentication token = new UsernamePasswordAuthenticationToken(HEADERS_KEY, HEADERS_VALUE,
							Collections.emptySet());
					getContext().setAuthentication(token);
				}
			}
		}
		filterChain.doFilter(request, response);
	}

	/**
	 * 签名
	 * 
	 * @param timestamp
	 * @param secret
	 * @return
	 */
	private String sign(String timestamp, String secret) {
		String stringToSign = timestamp + "\n" + secret;
		HMac hMac = DigestUtil.hmac(HmacAlgorithm.HmacSHA256, secret.getBytes());
		return hMac.digestBase64(stringToSign, false);
	}

}
