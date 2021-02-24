package com.libs.util.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

@Configuration
@PropertySources(value = {@PropertySource("classpath:application.properties")})
public class ApplicationProperties {
	@Autowired
	private Environment env;
	
	public String getProps(String key){
		if(env.containsProperty(env.resolvePlaceholders(key))){
			return env.getProperty(env.resolvePlaceholders(key));
		}
		return null;
	}
}
