package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.common.exception.ParameterException;
import com.demo.common.log.SystemControllerLog;
import com.demo.entity.TokenVo;
import com.demo.entity.User;
import com.demo.feign.ServiceAuoth;
import com.demo.response.Response;
import com.demo.utils.UserUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "用户controller", tags = { "用户登录相关Api" })
@RestController
@RequestMapping(value = "/user")
public class HelloController {

	@Autowired
	private ServiceAuoth serviceAuoth;

	@ApiOperation(value = "用户登录", notes = "用户登录接口")
	@PostMapping("/login/{code}")
	@SystemControllerLog
	public Response<User> login(@PathVariable("code") String code) throws Exception {
		String accessToken = null;
		// code换取accessToken
		Response<TokenVo> response = serviceAuoth.accessToken(code);
		if (null != response && null != response.getResponseEntity()) {
			accessToken = response.getResponseEntity().getAccess_token();
		} else if (null != response && null != response.getServerResult()
				&& response.getServerResult().getResultCode() != "200") {
			throw new ParameterException(response.getServerResult().getResultCode(),
					response.getServerResult().getResultMsg());
		} else {
			throw new ParameterException("109", "调用基础平台认证服务失败");
		}
		// accessToken换取用户信息
		Response<User> user = serviceAuoth.getUserInfo(accessToken);
		User userVo = user.getResponseEntity();
		userVo.setAccessToken(accessToken);
		UserUtil.setUserSession(userVo);
		return user;
    }

	@ApiOperation(value = "验证", notes = "务必提交json格式")
	@PostMapping("/check")
	public Response<TokenVo> check(@RequestBody TokenVo tokenVo) {

		return serviceAuoth.checkAccessToken(tokenVo.getAccess_token());
	}
}