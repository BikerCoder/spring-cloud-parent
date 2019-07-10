package com.demo.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.demo.entity.User;

public class UserUtil {

	public static HttpSession getSession() {
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = req.getSession();

		return session;
	}

	public static void setUserSession(User userVo) {
		getSession().setAttribute("user", userVo);

	}
}
