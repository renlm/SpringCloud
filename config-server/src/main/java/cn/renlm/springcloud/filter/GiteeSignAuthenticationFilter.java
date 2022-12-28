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

import cn.hutool.core.text.CharPool;
import cn.hutool.core.util.StrUtil;
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
 * WebHook 签名认证
 * 
 * @author Renlm
 *
 */
@Component
@AllArgsConstructor
public class GiteeSignAuthenticationFilter extends OncePerRequestFilter {

	private static final String HEADER_KEY = "X-Git-Oschina-Event";

	private static final String HEADER_VALUE = "Push Hook";

	private final WebHookProperties webHookProperties;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (HEADER_VALUE.equals(request.getHeader(HEADER_KEY))) {
			Authentication authentication = getContext().getAuthentication();
			boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
			String hmacSHA256 = request.getHeader("X-Gitee-Token");
			String giteeTimestamp = request.getHeader("X-Gitee-Timestamp");
			if (!isAuthenticated && StringUtils.hasText(hmacSHA256) && StringUtils.hasText(giteeTimestamp)) {
				String sign = this.sign(giteeTimestamp, webHookProperties.getSignSecret());
				if (StrUtil.equals(sign, hmacSHA256)) {
					Collection<? extends GrantedAuthority> authorities = Collections.emptySet();
					Authentication token = new UsernamePasswordAuthenticationToken(HEADER_KEY, HEADER_VALUE,
							authorities);
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
		String stringToSign = timestamp + CharPool.LF + secret;
		HMac hMac = DigestUtil.hmac(HmacAlgorithm.HmacSHA256, secret.getBytes());
		return hMac.digestBase64(stringToSign, false);
	}

}
