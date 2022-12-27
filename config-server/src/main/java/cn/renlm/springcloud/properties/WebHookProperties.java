package cn.renlm.springcloud.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * WebHook 认证
 * 
 * @author Renlm
 *
 */
@Getter
@Setter
@Component
@ConfigurationProperties("webhook.secret")
public class WebHookProperties {

	private String name;

	private String password;

}
