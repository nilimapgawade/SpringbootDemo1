package com.collections.genesys.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.collections.genesys.filters.JwtAuthenticationLoggingFilter;
import com.collections.genesys.repository.ApiLogRepository;

import com.collections.genesys.service.DecodeTokenService;

@Configuration
public class FilterConfig {

	private final DecodeTokenService decodeTokenService;

	private final ApiLogRepository apiLogRepo;

	public FilterConfig(DecodeTokenService decodeTokenService, ApiLogRepository apiLogRepo) {
		super();
		this.decodeTokenService = decodeTokenService;
		this.apiLogRepo = apiLogRepo;

	}

	/*
	@Bean
	public FilterRegistrationBean<JwtAuthenticationLoggingFilter> loggingFilter() {
		FilterRegistrationBean<JwtAuthenticationLoggingFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new JwtAuthenticationLoggingFilter(apiLogRepo, decodeTokenService));
		registrationBean.addUrlPatterns("/dcore/v1/*");
		//registrationBean.addInitParameter("excludedUrls", "/dcore/v1/generatetoken");
		//registrationBean.setOrder(1);
		return registrationBean;

	}*/
}
