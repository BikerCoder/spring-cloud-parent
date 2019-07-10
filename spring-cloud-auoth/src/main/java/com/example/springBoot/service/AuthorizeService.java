package com.example.springBoot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springBoot.config.RedisClient;

@Service
public class AuthorizeService {
	@Autowired
	private RedisClient redisClient;

	public void addAuthCode(String authorizationCode, String username) {
		redisClient.set("authCode:" + authorizationCode, username, 600);
//		LOGGER.info("authCode:" + authCode + "," + userId);

	}

	public boolean checkCode(String code) {

		return redisClient.get("authCode:" + code) != null;
	}

	public String getUserInfoByAuthCode(String code) {
		return redisClient.get("authCode:" + code);
	}

	public void addAccessToken(String accessToken, String userInfoJson) {
		redisClient.set("accessToken:" + accessToken, userInfoJson, 28800);// access_token时效为8小时
	}

	public void deleteAuthCode(String authCode) {
		redisClient.del("authCode:" + authCode);
	}

	public boolean checkAccessToken(String accessToken) {
		return redisClient.get("accessToken:" + accessToken) != null;
	}

	public String getUserInfo(String accessToken) {
		return redisClient.get("accessToken:" + accessToken);
	}

	public Long getAccessTokenExpireIn(String accessToken) {
		return redisClient.getExpireIn("accessToken:" + accessToken);
	}

}
