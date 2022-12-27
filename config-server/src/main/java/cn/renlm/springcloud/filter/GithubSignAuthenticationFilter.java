package cn.renlm.springcloud.filter;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
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
import lombok.extern.slf4j.Slf4j;

/**
 * WebHook 认证
 * 
 * @author Renlm
 *
 */
@Slf4j
@Component
@AllArgsConstructor
public class GithubSignAuthenticationFilter extends OncePerRequestFilter {

	private static final String HEADERS_KEY = "X-Github-Event";

	private static final String HEADERS_VALUE = "push";

	private final WebHookProperties webHookProperties;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		HttpServletRequest requestWrapper = new RepeatableHttpServletRequest(request);
		if (HEADERS_VALUE.equals(request.getHeader(HEADERS_KEY))) {
			byte[] bytes = IoUtil.readBytes(requestWrapper.getInputStream());
			String body = new String(bytes, request.getCharacterEncoding());
			String sign = this.sign(body, webHookProperties.getSignSecret());
			String signature = request.getHeader("X-Hub-Signature-256");
			log.info("sign = {}", sign);
			log.info("signature = {}", signature);
			log.info("payload = {}", body);
			if (StrUtil.equals(sign, signature)) {
				Authentication token = new UsernamePasswordAuthenticationToken(HEADERS_KEY, HEADERS_VALUE,
						Collections.emptySet());
				getContext().setAuthentication(token);
			}
		}
		filterChain.doFilter(requestWrapper, response);
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
