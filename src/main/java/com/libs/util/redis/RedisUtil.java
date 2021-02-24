package com.libs.util.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
//
//public enum RedisUtil {
//    INSTANCE;
public class RedisUtil{

	private static RedisUtil INSTANCE;
    private final JedisPool pool;
    private String host;
    private String password;
    private int port;
    private int timeout;

    RedisUtil() {
        pool = new JedisPool(new JedisPoolConfig(),null, 6379, 1000,null);
    }
    

    RedisUtil(String host, String password, int port) {
    	this.host = host;
    	this.password = password;
    	this.port = port;
        pool = new JedisPool(new JedisPoolConfig(), this.host, this.port, 1000,this.password);
    }
    

    RedisUtil(String host, String password, int port, int timeout) {
    	this.host = host;
    	this.password = password;
    	this.port = port;
    	this.timeout = timeout;
        pool = new JedisPool(new JedisPoolConfig(), this.host, this.port, this.timeout,this.password);
    }

    
    public static RedisUtil getInstance(){
    	if(null == INSTANCE){
    		INSTANCE = new RedisUtil();
    	}
    	
    	return INSTANCE;
    }
    
    public static RedisUtil getInstance(String host, String password, int port){
    	if(null == INSTANCE){
    		INSTANCE = new RedisUtil(host, password, port);
    	}
    	
    	return INSTANCE;
    }
    
    public static RedisUtil getInstance(String host, String password, int port, int timeout){
    	if(null == INSTANCE){
    		INSTANCE = new RedisUtil(host, password, port, timeout);
    	}
    	
    	return INSTANCE;
    }
    
    public void sadd(String key, String value) {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            jedis.sadd(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void srem(String key, String value) {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            jedis.srem(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public boolean sismember(String key, String value) {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
    
}
