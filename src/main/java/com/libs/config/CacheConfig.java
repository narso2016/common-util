package com.libs.config;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.Duration;
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
//import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {
	@Value("${spring.redis.host}")
	String REDIS_HOST;

	@Value("${spring.redis.password}")
	String REDIS_PASSWORD;

	@Value("${spring.redis.port}")
	int REDIS_PORT;
	
	@Value("${spring.redis.lettuce.pool.max-idle}")
	int LETTUCE_POOL_MAX_IDLE;
	
	@Value("${spring.redis.lettuce.pool.min-idle}")
	int LETTUCE_POOL_MIN_IDLE;
	
	@Value("${spring.redis.lettuce.pool.max-active}")
	int LETTUCE_POOL_MAX_ACTIVE;
	
	@Value("${spring.redis.lettuce.pool.max-wait}")
	long LETTUCE_POOL_MAX_WAIT;
	
	@Value("${spring.redis.lettuce.shutdown-timeout}")
	long LETTUCE_POOL_SHUTDOWN_TIMEOUT;
	
//    @Autowired
//    RedisConnectionFactory factory;
	
//	spring.redis.lettuce.pool.max-idle=25  
//    spring.redis.lettuce.pool.min-idle=0    
//    spring.redis.lettuce.pool.max-active=80  
//	spring.redis.lettuce.pool.max-wait=-1ms
//	spring.redis.lettuce.shutdown-timeout=200ms
//	spring.redis.cookie-name=SESSION_ID
//	spring.redis.session-expire=1800

	private static Logger logger = LoggerFactory.getLogger(CacheConfig.class);

//	@Bean
//	public JedisConnectionFactory redisConnectionFactory() {
//		JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
//		redisConnectionFactory.setHostName(REDIS_HOST);
//		redisConnectionFactory.setPort(REDIS_PORT);
//		redisConnectionFactory.setPassword(REDIS_PASSWORD);
//		logger.info("Init...Jedis Connection Factory");
//		return redisConnectionFactory;
//	}
	
	@Bean(name = "redisConnectionFactory")
	public JedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(REDIS_HOST, REDIS_PORT);
		redisStandaloneConfiguration.setPassword(REDIS_PASSWORD);
		logger.info("Init...Jedis Connection Factory");
		return new JedisConnectionFactory(redisStandaloneConfiguration);
	}
	
//	 @SuppressWarnings("rawtypes")
//	  @Primary
//	  @Bean(name = "connectionFactory")
//	  public RedisConnectionFactory connectionFactory() {
//	    // Stand-alone configuration
//	    RedisStandaloneConfiguration redisStandaloneConfiguration =
//	        new RedisStandaloneConfiguration(REDIS_HOST, REDIS_PORT);
//	    redisStandaloneConfiguration.setDatabase(1);
//	    redisStandaloneConfiguration.setPassword(REDIS_PASSWORD);
//	    
//	    // Cluster version configuration
//	    // RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
//	    // String[] serverArray = clusterNodes.split(",");
//	    // Set<RedisNode> nodes = new HashSet<RedisNode>();
//	    // for (String ipPort : serverArray) {
//	    // String[] ipAndPort = ipPort.split(":");
//	    // nodes.add(new RedisNode(ipAndPort[0].trim(), Integer.valueOf(ipAndPort[1])));
//	    // }
//	    // redisClusterConfiguration.setPassword(RedisPassword.of(password));
//	    // redisClusterConfiguration.setClusterNodes(nodes);
//	    // redisClusterConfiguration.setMaxRedirects(maxRedirects);
//	    // 
//	    GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();//
//	    poolConfig.setMaxIdle(LETTUCE_POOL_MAX_IDLE);
//	    poolConfig.setMaxTotal(LETTUCE_POOL_MAX_ACTIVE);
//	    poolConfig.setMaxWaitMillis(LETTUCE_POOL_MAX_WAIT);
//	    poolConfig.setMinIdle(LETTUCE_POOL_MIN_IDLE);
//	    LettuceClientConfiguration lettuceClientConfiguration = 
//	    		LettucePoolingClientConfiguration.builder().poolConfig(poolConfig)
//	            .commandTimeout(Duration.ofMillis(LETTUCE_POOL_SHUTDOWN_TIMEOUT)).build();
//	    LettuceConnectionFactory redisConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration);
//	    logger.info("Init...Jedis Connection Factory");
//	    return redisConnectionFactory;
//	  }
	
//    @Bean
//    public LettuceConnectionFactory redisConnectionFactory() {
//        logger.info("Redis (/Lettuce) configuration enabled. With cache timeout " + timeoutSeconds + " seconds.");
//
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setHostName(REDIS_HOST);
//        redisStandaloneConfiguration.setPort(REDIS_PORT);
//        return new LettuceConnectionFactory(redisStandaloneConfiguration);
//    }

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory cf) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(cf);
		logger.info("Init...Redis template");
		return redisTemplate;
	}
	
	  @SuppressWarnings("rawtypes")
	  @Bean
	  public RedisSerializer sessionRedisSerializer() {
	    return new Jackson2JsonRedisSerializer<Object>(Object.class);
	  }
	  
//	 @SuppressWarnings("rawtypes")
//	  @Primary
//	  @Bean(name = "redisTemplate")
//	  public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
//	    RedisTemplate<Object, Object> template = new RedisTemplate<>();
//	    template.setConnectionFactory(connectionFactory);
//	    RedisSerializer stringSerializer = new StringRedisSerializer();
//	    template.setKeySerializer(stringSerializer);
//	    template.setValueSerializer(sessionRedisSerializer());
//	    template.setHashKeySerializer(stringSerializer);
//	    template.setHashValueSerializer(sessionRedisSerializer());
//	    template.afterPropertiesSet();
//	    logger.info("Init...Redis template");
//	    return template;
//	  }

//	@Bean
//	public CacheManager cacheManager(RedisTemplate<String, Object> redisTemplate) {
//		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
//		cacheManager.setDefaultExpiration(3000);
//		logger.info("Init...cache manager");
//		return cacheManager;
//	}
	
//	
//	  @Bean
//	    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
//	        return RedisCacheManager.create(connectionFactory);
//	    }
//
//	    @Bean
//	    public RedisTemplate<String, Serializable> redisTemplate(JedisConnectionFactory connectionFactory) {
//	        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
//	        redisTemplate.setKeySerializer(new StringRedisSerializer());
//	        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//	        redisTemplate.setConnectionFactory(jedisConnectionFactory(jedisPoolConfig()));
//	        return redisTemplate;
//	    }
	    
	 private static RedisCacheConfiguration createCacheConfiguration(long timeoutInSeconds) {
	        return RedisCacheConfiguration.defaultCacheConfig()
	                .entryTtl(Duration.ofSeconds(timeoutInSeconds));
	    }
	
	 @Bean
	  public CacheManager cacheManager(@Qualifier("redisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
	    RedisCacheManager redisCacheManager = RedisCacheManager.create(redisConnectionFactory);
	    logger.info("Init...cache manager");
	    return redisCacheManager;
	  }
	 
	 @Bean
	    public RedisCacheConfiguration cacheConfiguration() {
	        return createCacheConfiguration(3000);
	    }
	 

	@Bean
	public KeyGenerator customKeyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object o, Method method, Object... objects) {
				StringBuilder sb = new StringBuilder();
				sb.append(o.getClass().getName());
				sb.append(method.getName());
				for (Object obj : objects) {
					sb.append(obj.toString());
				}
				
				String key = sb.toString();
		        logger.info("key:" + key);
		        
				return key;
			}
		};
	}
	
}
