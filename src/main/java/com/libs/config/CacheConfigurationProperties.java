package com.libs.config;

import java.util.HashMap;
import java.util.Map;

public class CacheConfigurationProperties {
    private long timeoutSeconds;
    private int redisPort;
    private String redisHost;
    private String password;
    
    // Mapping of cacheNames to expira-after-write timeout in seconds
    private Map<String, Long> cacheExpirations = new HashMap<>();
    
	public CacheConfigurationProperties(long timeoutSeconds, int redisPort, String redisHost, String password) {
		super();
		this.timeoutSeconds = timeoutSeconds;
		this.redisPort = redisPort;
		this.redisHost = redisHost;
		this.password = password;
	}

	
	public long getTimeoutSeconds() {
		return timeoutSeconds;
	}

	public void setTimeoutSeconds(long timeoutSeconds) {
		this.timeoutSeconds = timeoutSeconds;
	}

	public int getRedisPort() {
		return redisPort;
	}

	public void setRedisPort(int redisPort) {
		this.redisPort = redisPort;
	}

	public String getRedisHost() {
		return redisHost;
	}

	public void setRedisHost(String redisHost) {
		this.redisHost = redisHost;
	}



	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Map<String, Long> getCacheExpirations() {
		return cacheExpirations;
	}


	public void setCacheExpirations(Map<String, Long> cacheExpirations) {
		this.cacheExpirations = cacheExpirations;
	}
    
 
    
    

}
