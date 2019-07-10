package com.example.springBoot.controller;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.demo.response.MessageResult;
import com.demo.response.Response;
import com.example.springBoot.entity.TokenVo;
import com.example.springBoot.entity.User;
import com.example.springBoot.service.AuthorizeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "认证服务接口", tags = { "认证服务接口" })
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthorizeService authorizeService;

	@ApiOperation(value = "code换取accessToken", notes = "code换取accessToken")
	@GetMapping(value = "/accessToken")
	public Response<TokenVo> accessToken(@RequestParam("code") String code) throws OAuthSystemException {
		Response<TokenVo> response = new Response<TokenVo>();
		// 验证code
		if (!authorizeService.checkCode(code)) {
			MessageResult msg = new MessageResult();
			msg.setResultCode("109");
			msg.setResultMsg("code验证失败");
			response.setServerResult(msg);
			return response;
		}
		// 生成accessToken
		OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
		final String accessToken = oauthIssuerImpl.accessToken();
		String userInfoJson = authorizeService.getUserInfoByAuthCode(code);

		// 添加accessToken
		authorizeService.addAccessToken(accessToken, userInfoJson);

		// 删除code
		authorizeService.deleteAuthCode(code);

		TokenVo tokenVo = new TokenVo();
		tokenVo.setAccess_token(accessToken);
		tokenVo.setExpires_in(28800);

		return new Response<TokenVo>(tokenVo);
	}

	@ApiOperation(value = "accessToken获取用户信息", notes = "accessToken获取用户信息")
	@RequestMapping(value = "/getUserInfo/{accessToken}", method = { RequestMethod.GET, RequestMethod.POST })
	public Response<User> getUserInfo(@PathVariable("accessToken") String accessToken) {
		Response<User> response = new Response<User>();
		// 验证code
		if (!authorizeService.checkAccessToken(accessToken)) {
			MessageResult msg = new MessageResult();
			msg.setResultCode("109");
			msg.setResultMsg("accessToken验证失败");
			response.setServerResult(msg);
			return response;
		}
		String userInfo = authorizeService.getUserInfo(accessToken);
		User user = JSON.parseObject(userInfo, User.class);
		return new Response<User>(user);

	}

	@ApiOperation(value = "验证accessToken", notes = "验证accessToken")
	@RequestMapping(value = "/checkAccessToken/{accessToken}", method = RequestMethod.GET)
	public Response<TokenVo> checkAccessToken(@PathVariable("accessToken") String accessToken) {
		Response<User> response = new Response<User>();
		Long expires_in = authorizeService.getAccessTokenExpireIn(accessToken);
		if (expires_in == -2) {
			return new Response<TokenVo>("104", "操作失败", "请求参数access_token不存在/过期");
		}
		TokenVo tokenVo = new TokenVo();
		tokenVo.setExpires_in(expires_in);
		tokenVo.setAccess_token(accessToken);

		return new Response<TokenVo>(tokenVo);
	}
}
