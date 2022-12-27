package sample;

import org.junit.jupiter.api.Test;

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

}
