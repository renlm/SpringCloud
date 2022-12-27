package sample;

import org.junit.jupiter.api.Test;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;

/**
 * WebHook 签名认证
 * 
 * @author Renlm
 *
 */
public class WebHookSignTest {

	@Test
	public void gitee() {
		Long timestamp = 1672135206524L;
		String secret = "123654";
		String stringToSign = timestamp + "\n" + secret;
		HMac hMac = DigestUtil.hmac(HmacAlgorithm.HmacSHA256, secret.getBytes());
		String digestBase64 = hMac.digestBase64(stringToSign, false);
		System.out.println(digestBase64);
		System.out.println("AbEvusCKxqRkBtTr4fD47SGVP/mRwlt48iuUYMFRloI=".equals(digestBase64));
	}

	@Test
	public void github() {
		String secret = "123654";
		String stringToSign = ResourceUtil.readUtf8Str("WebHook.Json");
		HMac hMac = DigestUtil.hmac(HmacAlgorithm.HmacSHA256, secret.getBytes());
		String digestHex = hMac.digestHex(stringToSign);
		System.out.println(digestHex);
		System.out.println("5e4bddb1eb0748e425001202e183b211cb74a095c4982b3950e5e6632b121b6a".equals(digestHex));
	}
	
}
