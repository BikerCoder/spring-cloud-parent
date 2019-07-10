package com.example.springBoot.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.example.springBoot.entity.User;
import com.example.springBoot.mapper.UserMapper;

public class UserRealm extends AuthorizingRealm{
	
	@Autowired
	private UserMapper userMapper;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken user = (UsernamePasswordToken) token;
		User realUser = new User();
	    realUser.setUserName(user.getUsername());
//	    realUser.setPassword(String.copyValueOf(user.getPassword()));
	    User newUser = userMapper.getUser(realUser);
		String s = JSONObject.toJSONString(newUser);
	    if(newUser == null){
			//用户名错误
			//shiro会抛出UnknownAccountException异常
			return null;
		}
		return new SimpleAuthenticationInfo(s, newUser.getPassword(), getName());
	}

}
