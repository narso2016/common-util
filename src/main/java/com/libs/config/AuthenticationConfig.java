package com.libs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import com.libs.annotation.EnableCacheConfig;
import com.libs.filter.AuthenticationInterceptor;
import com.libs.filter.GlobalErrorHandler;
import com.libs.persistence.entity.SysUser;
import com.libs.util.repository.AuditTrailRepository;
import com.libs.util.repository.AuditTrailRepositoryImpl;

@Configuration
@EnableCacheConfig
public class AuthenticationConfig extends WebMvcConfigurerAdapter {
	
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setUseSuffixPatternMatch(false);
	}
	
	@Bean
	public AuthenticationInterceptor authenticationInterceptor() {
		return new AuthenticationInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authenticationInterceptor());
	}
	
	@Bean
	public GlobalErrorHandler globalErrorHandler( ) {
		return new GlobalErrorHandler();
	}

	@Bean
	public SessionManager sessionManager() {
		return new SessionManager();
	}
	
	@Bean
	public AuditTrailRepository<SysUser> audit(){
		return new AuditTrailRepositoryImpl<SysUser>();
	}

}
