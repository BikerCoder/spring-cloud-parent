package com.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.demo.utils.Interceptor;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
	@Bean
	public Interceptor getInterceptor() {
		return new Interceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry){
		// 这里可以添加多个拦截器
		registry.addInterceptor(getInterceptor()).addPathPatterns("/**", "/doc.html").excludePathPatterns(
				"/user/login/**",
				"/swagger-resources",
				"/loginUser", "/", "/webjars/**", "/v2/**");
		super.addInterceptors(registry);
	}
}
