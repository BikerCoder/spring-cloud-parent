package com.demo.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.demo.entity.TokenVo;
import com.demo.entity.User;
import com.demo.feign.ServiceAuoth;
import com.demo.response.Response;

public class Interceptor extends HandlerInterceptorAdapter {
	@Autowired
	private ServiceAuoth serviceAuoth;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			String accessToken = user.getAccessToken();
			Response<TokenVo> result = serviceAuoth.checkAccessToken(accessToken);
			if (result != null && result.getResponseEntity() != null
					&& result.getResponseEntity().getAccess_token() != null) {
				System.out.println("验证通过：" + result.getResponseEntity());
				return true;
			}
		}
		System.out.println("验证不通过");
		response.getWriter().write(info);
		return false;
	}

	private static String info = JSONObject
			.toJSONString(new Response<String>(HttpStatus.UNAUTHORIZED.value() + "", "用户信息不存在或者已过期"));
}
