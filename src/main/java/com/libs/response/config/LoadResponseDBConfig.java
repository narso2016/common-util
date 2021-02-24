package com.libs.response.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.libs.response.service.ResponseMapping;
import com.libs.response.service.ResponseMappingDBImpl;
import com.libs.response.service.ResponseMappingDaoService;
import com.libs.response.service.ResponseMappingDaoServiceImpl;

@Configuration
public class LoadResponseDBConfig {
	@Bean
	public ResponseMappingDaoService responseMappingDaoService() {
		return new ResponseMappingDaoServiceImpl();
	}
	
	@Bean
	public ResponseMapping responseMapping( ) {
		return new ResponseMappingDBImpl(responseMappingDaoService());
	}
	
}
