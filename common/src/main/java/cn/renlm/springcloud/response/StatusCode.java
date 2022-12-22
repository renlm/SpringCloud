package cn.renlm.springcloud.response;

import lombok.Getter;

/**
 * 响应状态码
 * 
 * @author Renlm
 *
 */
public enum StatusCode {

	OK(200, "OK"),
	BAD_REQUEST(400, "Bad Request"),
	UNAUTHORIZED(401, "Unauthorized"),
	INTERNAL_SERVER_ERROR(500, "Internal Server Error");
	
	@Getter
	private final int value;

	@Getter
	private final String reasonPhrase;

	StatusCode(int value, String reasonPhrase) {
		this.value = value;
		this.reasonPhrase = reasonPhrase;
	}
	
}
