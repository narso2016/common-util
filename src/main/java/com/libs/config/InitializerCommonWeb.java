package com.libs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.libs.engine.JWTAuthenticationEngine;
import com.libs.engine.JWTAuthenticationEngineImpl;
import com.libs.engine.StandardAuthenticationEngine;
import com.libs.engine.StandardAuthenticationEngineDBImpl;
import com.libs.persistence.service.BranchServiceDAO;
import com.libs.persistence.service.BranchServiceDAOImpl;
import com.libs.persistence.service.NavbarServiceDAO;
import com.libs.persistence.service.NavbarServiceDAOImpl;
import com.libs.persistence.service.RoleServiceDAO;
import com.libs.persistence.service.RoleServiceDAOImpl;
import com.libs.persistence.service.UserServiceDAO;
import com.libs.persistence.service.UserServiceDAOImpl;
import com.libs.util.redis.RedisUtil;

@Configuration
public class InitializerCommonWeb {
	@Value("${spring.redis.host}")
	String REDIS_HOST;

	@Value("${spring.redis.password}")
	String REDIS_PASSWORD;

	@Value("${spring.redis.port}")
	int REDIS_PORT;

	@Bean
	public RedisUtil redis() {
		return RedisUtil.getInstance(REDIS_HOST, REDIS_PASSWORD, REDIS_PORT);
	}

	@Bean
	public StandardAuthenticationEngine standarEngine() {
		return new StandardAuthenticationEngineDBImpl();
	}

	@Bean
	public JWTAuthenticationEngine jwtEngine() {
		return new JWTAuthenticationEngineImpl();
	}
	
	@Bean 
	public UserServiceDAO userServiceDAO() {
		return new UserServiceDAOImpl();
	}
	
	@Bean 
	public RoleServiceDAO roleServiceDAO() {
		return new RoleServiceDAOImpl();
	}
	
	@Bean
	public NavbarServiceDAO navbarServiceDAO() {
		return new NavbarServiceDAOImpl();
	}
	
	@Bean
	public BranchServiceDAO branchServiceDAO() {
		return new BranchServiceDAOImpl();
	}
	
}
