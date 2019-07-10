package com.example.springBoot.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.response.MessageResult;
import com.demo.response.Response;


@ControllerAdvice
public class GlobalExceptionHandler {
	// 空指针异常
	@ExceptionHandler(NullPointerException.class)
	public @ResponseBody Response<String> nullPointerExceptionHandler(NullPointerException e) {
		Response<String> result = getResultInfo("100", "空指针异常");
		e.printStackTrace();
		return result;
	}

	private Response<String> getResultInfo(String errorCode, String message) {
		Response<String> result = new Response<String>();
		result.setServerResult(new MessageResult(errorCode, message));
		return result;
	}
}
