package com.example.springBoot.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.response.Response;
import com.example.springBoot.mapper.UserMapper;
import com.example.springBoot.service.AuthorizeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(value = "登录接口", tags = { "登录接口" })
public class UserController {
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private AuthorizeService authorizeService;

	@PostMapping("hei")
	public Response<String> login() {
		System.out.println("来了");

		return new Response<String>("helloWord我");
	}

	@ApiOperation(value = "调用OAuth2.0的authorize接口", notes = "调用OAuth2.0的authorize接口")
	@RequestMapping(value = "/auth/authorize", method = { RequestMethod.GET, RequestMethod.POST })
	public Object authorize(HttpServletRequest request,
			@RequestParam(required = false, name = "client_id") String client_id,
			@RequestParam("redirect_uri") String redirect_uri, @RequestParam("response_type") String response_type,
			@RequestParam(required = false, name = "from") String from, Model model)
			throws OAuthSystemException, OAuthProblemException, URISyntaxException {
		// 构建OAuth 授权请求
		client_id = ";";
//		OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);

		// 获取subject
		// 判断用户是否登录
		Subject subject = SecurityUtils.getSubject();
		// 如果用户没有登录,跳转到登录页面
		if (!subject.isAuthenticated()) {
			if (!login(subject, request)) {
				// 登录失败时跳转到登陆页面
//		                model.addAttribute("client", clientService.findByClientId(oauthRequest.getClientId()));
				return "login";
			}
		}

		String username = (String) subject.getPrincipal();

		// 生成授权码
		String authorizationCode = null;

		if (response_type.equals(ResponseType.CODE.toString())) {
			OAuthIssuerImpl oAuthIssuer = new OAuthIssuerImpl(new MD5Generator());
			authorizationCode = oAuthIssuer.authorizationCode();
			// 把授权码放到缓存中
			authorizeService.addAuthCode(authorizationCode, username);
		}

		// 进行OAuth响应构建
		OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse.authorizationResponse(request,
				HttpServletResponse.SC_FOUND);
		// 设置授权码
		builder.setCode(authorizationCode);
		// 根据客户端重定向地址
		// 构建响应
		final OAuthResponse response = builder.location(redirect_uri).buildQueryMessage();

		// 根据OAuthResponse 返回 ResponseEntity响应
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(new URI(response.getLocationUri()));
		return new ResponseEntity(headers, HttpStatus.valueOf(response.getResponseStatus()));
	}

	private boolean login(Subject subject, HttpServletRequest request) {
		String username = request.getParameter("userName");
		String password = request.getParameter("password");
		// 封装用户数据
		UsernamePasswordToken userToken = new UsernamePasswordToken(username, password);
		// 执行登录方法,用捕捉异常去判断是否登录成功
		try {
			subject.login(userToken);
			return true;
		} catch (UnknownAccountException e) {
			// 用户名不存在
			return false;
		} catch (IncorrectCredentialsException e) {
			// 密码错误
			return false;
		}
	}
}
