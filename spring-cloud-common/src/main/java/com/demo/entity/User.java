package com.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel(value = "用户基础信息")
public class User {
	/**
	 * 用户id(主键 自增)
	 */
	@ApiModelProperty(value = "用户id(主键 自增)")
	private Integer uid;

	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	private String userName;

	/**
	 * 登录密码
	 */
	@ApiModelProperty(value = "登录密码")
    private String password;

	/**
	 * 用户真实姓名
	 */
	@ApiModelProperty(value = "用户真实姓名")
	private String name;

	/**
	 * 身份证号
	 */
	@ApiModelProperty(value = "身份证号")
	private String idCardNum;

	/**
	 * 用户状态：0:正常状态,1：用户被锁定
	 */
	@ApiModelProperty(value = "用户状态：0:正常状态,1：用户被锁定")
	private String state;

	@ApiModelProperty(value = "令牌")
	private String accessToken;

}
