package cn.renlm.springcloud.demo.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * 访问白名单
 * 
 * @author Renlm
 *
 */
@Getter
@Setter
@Component
@ConfigurationProperties("security.ignore")
public class SecurityIgnoreProperties {

	private List<String> whites;

}
