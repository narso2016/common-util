package com.libs.logging.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.libs.logging.LoggingAspect;


@Configuration
public class LoggingConfig {

	@Bean
	public LoggingAspect loggingAspect() {
		return new LoggingAspect();
	}
	
}
