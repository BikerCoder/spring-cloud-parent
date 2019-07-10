package com.demo.feign;

import org.springframework.stereotype.Component;

import com.demo.entity.TokenVo;
import com.demo.entity.User;
import com.demo.response.MessageResult;
import com.demo.response.Response;

@Component
public class ServiceAuothFallback implements ServiceAuoth {

	@Override
	public Response<TokenVo> accessToken(String code) {
		return exceptionInfo("accessToken");
	}

	private Response exceptionInfo(String methodName) {
		Response resp = new Response();
		MessageResult result = new MessageResult();
		result.setResultMsg("调用spring-cloud-auoth服务出现了异常,方法名称：" + methodName);
		result.setResultCode("450");
		resp.setServerResult(result);
		return resp;
	}

	@Override
	public Response<User> getUserInfo(String accessToken) {
		return exceptionInfo("getUserInfo");
	}

	@Override
	public Response<TokenVo> checkAccessToken(String accessToken) {
		return exceptionInfo("checkAccessToken");
	}
}
