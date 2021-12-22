package com.myapp.board.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.myapp.board.interceptor.LoggerInterceptor;

@Configuration
public class MVCConfiguratiobn implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerInterceptor()).excludePathPatterns("/css/**", "/fonts/**", "/plugin/**", "/scripts/**");
		WebMvcConfigurer.super.addInterceptors(registry); 
	}
	
}
