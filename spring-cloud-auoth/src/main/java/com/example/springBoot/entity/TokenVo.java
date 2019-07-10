package com.example.springBoot.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TokenVo implements Serializable {
	private static final long serialVersionUID = -838398763066913986L;
	
	/** 访问令牌 **/
	private String access_token;

	/** 过期时间 **/
	private long expires_in;

}
