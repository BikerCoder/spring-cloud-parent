package com.demo.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.entity.TokenVo;
import com.demo.entity.User;
import com.demo.response.Response;

import io.swagger.annotations.ApiOperation;

@Component
@FeignClient(name = "spring-cloud-auoth", url = "${service.auoth.url}", fallback = ServiceAuothFallback.class)
public interface ServiceAuoth {

	@GetMapping(value = "/auth/accessToken")
	@ApiOperation(value = "用户登录", tags = { "返回用户信息" }, notes = "用户登录接口")
	public Response<TokenVo> accessToken(@RequestParam("code") String code);
	
	@RequestMapping(value = "/auth/getUserInfo/{accessToken}", method = RequestMethod.GET)
	@ApiOperation(value = "accessToken获取用户信息", notes = "accessToken获取用户信息")
	public Response<User> getUserInfo(@PathVariable("accessToken") String accessToken);

	@ApiOperation(value = "验证accessToken", notes = "验证accessToken")
	@RequestMapping(value = "/auth/checkAccessToken/{accessToken}", method = RequestMethod.GET)
	public Response<TokenVo> checkAccessToken(@PathVariable("accessToken") String accessToken);
}
