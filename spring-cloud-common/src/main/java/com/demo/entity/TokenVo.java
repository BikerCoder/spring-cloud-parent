package com.demo.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel(description = "访问令牌")
public class TokenVo implements Serializable {
	private static final long serialVersionUID = -838398763066913986L;
	
	/** 访问令牌 **/
	@ApiModelProperty(value = "访问令牌", required = true)
	private String access_token;

	/** 过期时间 **/
	@ApiModelProperty(value = "过期时间", required = false)
	private long expires_in;

}
