package com.libs.util.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.libs.util.strings.Strings;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.exceptions.JedisConnectionException;
//import redis.clients.jedis.util.*;
import redis.clients.util.Pool;

public class RedisHelper {

	private static final Logger logger = LoggerFactory.getLogger(RedisHelper.class);

	private Pool<Jedis> jedisPool;

	public RedisHelper(Pool<Jedis> jedisPool) {
		this.jedisPool = jedisPool;
	}

	public Pool<Jedis> getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(Pool<Jedis> jedisPool) {
		this.jedisPool = jedisPool;
	}

	public Set<String> keys(String pattern) {
		Jedis jedis = null;
		Set<String> result = null;

		try {
			jedis = (Jedis) jedisPool.getResource();
			result = jedis.keys(pattern);
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedisPool.close();
			}
		}

		return result;
	}

	public long sadd(String key, String... members) {
		long result = Long.MIN_VALUE;
		Jedis jedis = null;
		try {
			jedis = (Jedis) jedisPool.getResource();
			result = jedis.sadd(key, members);
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedisPool.close();
			}
		}

		return result;
	}

	public long srem(String key, String... members) {
		long result = Long.MIN_VALUE;
		Jedis jedis = null;
		try {
			jedis = (Jedis) jedisPool.getResource();
			result = jedis.srem(key, members);
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedisPool.close();
			}
		}

		return result;
	}

	public Set<String> smembers(String key) {
		Set<String> result = null;
		Jedis jedis = null;
		try {
			jedis = (Jedis) jedisPool.getResource();
			result = jedis.smembers(key);
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedisPool.close();
			}
		}

		return result;
	}

	public String getString(String key) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = (Jedis) jedisPool.getResource();
			result = jedis.get(key);
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedisPool.close();
			}
		}

		return result;
	}

	public String setString(String key, String value) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = (Jedis) jedisPool.getResource();
			result = jedis.set(key, value);
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedisPool.close();
			}
		}

		return result;
	}

	public Object get(String key) {
		if (Strings.isNullOrEmpty(key)) {
			return null;
		}
		Object result = null;
		Jedis jedis = null;
		try {
			jedis = (Jedis) jedisPool.getResource();
			byte[] bytes = jedis.get(getKey(key));
			if (bytes != null) {
				result = bytes2Object(bytes);
			}
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedisPool.close();
			}
		}

		return result;
	}

	public void set(String key, Object value) {
		Jedis jedis = null;
		try {
			jedis = (Jedis) jedisPool.getResource();
			jedis.set(getKey(key), object2Bytes(value));
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedisPool.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getMap(String key) {
		if (Strings.isNullOrEmpty(key)) {
			return null;
		}
		Object obj = this.get(key);
		if (obj != null && obj instanceof Map) {
			return (Map<String, Object>) obj;
		} else {
			return null;
		}
	}

	public void setMap(String key, Map<String, Object> map) {
		this.set(key, map);
	}
	public void setRedisMap(String key, Map<String, String> map) {
		Jedis jedis = null;
		try {
			jedis = (Jedis) jedisPool.getResource();
			if (map != null && !map.isEmpty()) {
				jedis.hmset(key, map);
			}
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedisPool.close();
			}
		}
	}

	public void setRedisMap(String key, String field, String value) {
		if (Strings.isNullOrEmpty(key) || Strings.isNullOrEmpty(field)) {
			return;
		}
		Jedis jedis = null;
		try {
			jedis = (Jedis) jedisPool.getResource();
			jedis.hset(key, field, value);
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedisPool.close();
			}
		}
	}

	public Map<String, String> getRedisMap(String key) {
		if (Strings.isNullOrEmpty(key)) {
			return null;
		}
		Jedis jedis = null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			jedis = (Jedis) jedisPool.getResource();
			map = jedis.hgetAll(key);
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedisPool.close();
			}
		}
		return map;
	}

	/**
	 * 返回哈希表 key 中给定域 field 的值
	 * 
	 * @author sumory.wu @date 2014年8月15日 下午12:08:10
	 * @param key
	 * @param field
	 * @return
	 */
	public String getRedisMap(String key, String field) {
		if (Strings.isNullOrEmpty(key)) {
			return null;
		}
		Jedis jedis = null;
		String value = null;
		try {
			jedis = (Jedis) jedisPool.getResource();
			value = jedis.hget(key, field);
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedisPool.close();
			}
		}
		return value;
	}

	/**
	 * 从redis map中返回哈希表 key 中给定域 mapKey 的值
	 * 
	 * @param key
	 *            map key
	 * @param mapKey
	 *            map里某个域的key
	 */
	public String getFromRedisMap(String key, String mapKey) {
		if (Strings.isNullOrEmpty(key) || Strings.isNullOrEmpty(mapKey)) {
			return "";
		}
		Jedis jedis = null;
		String value = null;
		try {
			jedis = (Jedis) jedisPool.getResource();
			value = jedis.hget(key, mapKey);
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedisPool.close();
			}
		}
		return value;
	}

	/**
	 * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
	 * 
	 * @author sumory.wu @date 2014年8月15日 上午11:27:21
	 * @param key
	 * @param fields
	 * @return
	 */
	public long deleteFromRedisMap(String key, String... fields) {
		if (Strings.isNullOrEmpty(key) || fields == null || fields.length == 0) {
			return 0L;
		}
		Jedis jedis = null;
		Long count = null;
		try {
			jedis = (Jedis) jedisPool.getResource();
			count = jedis.hdel(key, fields);
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedisPool.close();
			}
		}
		return count;
	}

	/**
	 * 为哈希表 key 中的域 field 的值加上增量 increment <br>
	 * 
	 * 使用场景： # increment可为负数
	 * 
	 * <pre>
	 * redis> HGET counter page_view
	 * "200"
	 * 
	 * redis> HINCRBY counter page_view -50
	 * (integer) 150
	 * 
	 * redis> HGET counter page_view
	 * "150"
	 * </pre>
	 * 
	 * @author sumory.wu @date 2014年8月15日 上午9:52:14
	 * @param key
	 * @param field
	 * @param increment
	 *            须为整形
	 * @return
	 */
	public long incrMapValue(String key, String field, long increment) {
		long result = Long.MIN_VALUE;
		Jedis jedis = null;
		try {
			jedis = (Jedis) jedisPool.getResource();
			result = jedis.hincrBy(key, field, increment);
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedisPool.close();
			}
		}

		return result;
	}

	// ~===============过期相关===============================

	/**
	 * 设置过期时间点 <br/>
	 * 
	 * 例如： expireat abc 1408071100 即设置到2014/8/15 10:51:40过期
	 * 
	 * 
	 * @author sumory.wu @date 2014年8月15日 上午10:37:30
	 * @param key
	 * @param timestamp
	 *            秒数，从“January 1, 1970 UTC”到设定的过期时间的秒数，即unix时间戳
	 * @return 设置成功返回1
	 */
	public long expireAt(String key, long unixTime) {
		long result = Integer.MIN_VALUE;
		Jedis jedis = null;
		try {
			jedis = (Jedis) jedisPool.getResource();
			result = jedis.expireAt(key, unixTime);// 第二个参数是秒数
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedisPool.close();
			}
		}

		return (result == 0 ? Integer.MIN_VALUE : result);
	}

	/**
	 * 设置在多少秒后过期
	 * 
	 * @author sumory.wu @date 2014年8月15日 上午10:49:15
	 * @param key
	 * @param seconds
	 * @return 设置成功返回1
	 */
	public long expire(String key, int seconds) {
		long result = Integer.MIN_VALUE;
		Jedis jedis = null;
		try {
			jedis = (Jedis) jedisPool.getResource();
			result = jedis.expire(key, seconds);// 第二个参数是秒数
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedisPool.close();
			}
		}

		return (result == 0 ? Integer.MIN_VALUE : result);
	}

	// ~===============判断是否存在===============================
	/**
	 * 判断key是否存在
	 * 
	 * @author sumory.wu @date 2014年8月15日 上午10:57:50
	 * @param key
	 * @return 若 key 存在，返回 1 ，否则返回 0，key过期也会返回0
	 */
	public boolean exists(String key) {
		if (Strings.isNullOrEmpty(key)) {
			return false;
		}
		Jedis jedis = null;
		boolean rest = false;
		try {
			jedis = (Jedis) jedisPool.getResource();
			rest = jedis.exists(key);
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedisPool.close();
			}
		}
		return rest;
	}

	public boolean hexists(String key, String mapKey) {
		if (Strings.isNullOrEmpty(key)) {
			return false;
		}
		Jedis jedis = null;
		boolean rest = false;
		try {
			jedis = (Jedis) jedisPool.getResource();
			rest = jedis.hexists(key, mapKey);
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedisPool.close();
			}
		}
		return rest;
	}

	// ~===============删除===============================

	/**
	 * 删除key
	 * 
	 * @author sumory.wu @date 2014年8月15日 上午11:00:58
	 * @param key
	 * @return 被删除key的数量
	 */
	public long del(String... key) {
		if (key == null || key.length == 0) {
			return 0L;
		}
		Jedis jedis = null;
		Long rest = null;
		try {
			jedis = (Jedis) jedisPool.getResource();
			rest = jedis.del(key);
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedisPool.close();
			}
		}
		return rest;
	}

	// ~====================list列表相关====================

	/**
	 * 返回列表的长度
	 * 
	 * @author sumory.wu @date 2014年8月15日 下午12:34:33
	 * @param listKey
	 * @return 不存在返回0
	 */
	public long lengthOfList(String listKey) {
		Jedis jedis = null;
		Long rest = null;
		try {
			jedis = (Jedis) jedisPool.getResource();
			rest = jedis.llen(listKey);
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				this.jedisPool.close();
			}
		}
		return rest;
	}

	/**
	 * 存入列表尾，listkey不存在会被创建<br/>
	 * 
	 * @param listKey
	 * @param values
	 * @return 返回插入后列表的长度
	 */
	public long pushToListTail(String listKey, String... values) {
		Jedis jedis = null;
		long rest = Long.MIN_VALUE;
		try {
			jedis = (Jedis) jedisPool.getResource();
			if (listKey != null && values != null && values.length != 0) {
				rest = jedis.rpush(listKey, values);
			}
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				this.jedisPool.close();
			}
		}
		return rest;
	}

	/**
	 * 移除并返回列表 key 的尾元素<br/>
	 * 
	 * @param listKey
	 * @return 列表的尾元素，没有返回NULL
	 */
	public String popFromListTail(String listKey) {
		Jedis jedis = null;
		String rest = null;
		try {
			jedis = (Jedis) jedisPool.getResource();
			rest = jedis.rpop(listKey);
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				this.jedisPool.close();
			}
		}
		return rest;
	}

	public long pushToListHead(String listKey, String... values) {
		Jedis jedis = null;
		long rest = Long.MIN_VALUE;
		try {
			jedis = (Jedis) jedisPool.getResource();
			if (listKey != null && values != null && values.length != 0) {
				rest = jedis.lpush(listKey, values);
			}
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				this.jedisPool.close();
			}
		}
		return rest;
	}

	/**
	 * 移除并返回列表 key 的头元素<br/>
	 * 
	 * @param listKey
	 * @return 列表的头元素，没有返回NULL
	 */
	public String popFromListHead(String listKey) {
		Jedis jedis = null;
		String rest = null;
		try {
			jedis = (Jedis) jedisPool.getResource();
			rest = jedis.lpop(listKey);
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				this.jedisPool.close();
			}
		}
		return rest;
	}

	/**
	 * 从列表中取出某个元素<br/>
	 * 
	 * 以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。<br/>
	 * 也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
	 * 
	 * 例如：
	 * 
	 * <pre>
	 * redis> LPUSH mylist "World"
	 * (integer) 1
	 * 
	 * redis> LPUSH mylist "Hello"
	 * (integer) 2
	 * 
	 * redis> LINDEX mylist 0
	 * "Hello"
	 * 
	 * redis> LINDEX mylist -1
	 * "World"
	 * 
	 * redis> LINDEX mylist 3 # index不在 mylist 的区间范围内
	 * (nil)
	 * </pre>
	 * 
	 * @author sumory.wu @date 2014年8月18日 上午11:45:52
	 * @param key
	 * @param index
	 * @return 如果 index 参数的值不在列表的区间范围内(out of range)，返回 nil 。
	 */
	public String getFromList(String key, int index) {
		Jedis jedis = null;
		String rest = null;
		try {
			jedis = (Jedis) jedisPool.getResource();
			rest = jedis.lindex(key, index);
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				this.jedisPool.close();
			}
		}
		return rest;
	}

	/**
	 * 从listKey列表中删除value元素
	 * 
	 * @author sumory.wu @date 2014年8月15日 下午12:24:17
	 * @param listKey
	 * @param value
	 * @return 被移除元素的数量
	 */
	public Long removeFromList(String listKey, String value) {
		Jedis jedis = null;
		long rest = Long.MIN_VALUE;
		try {
			jedis = (Jedis) jedisPool.getResource();
			if (listKey != null && value != null) {
				rest = jedis.lrem(listKey, 0, value);// 第二个参数为0表示全部删除匹配的元素
			}
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				this.jedisPool.close();
			}
		}
		return rest;
	}

	/**
	 * 获取整个列表
	 * 
	 * @author sumory.wu @date 2015年3月9日 下午6:45:01
	 * @param listKey
	 * @return
	 */
	public List<String> getList(String listKey) {
		Jedis jedis = null;
		List<String> rest = null;
		try {
			jedis = (Jedis) jedisPool.getResource();
			if (listKey != null) {
				rest = jedis.lrange(listKey, 0, -1);
			}
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				this.jedisPool.close();
			}
		}
		return rest;
	}

	// ~===== pub / sub ======
	public void subscribe(JedisPubSub ps, String key) {
		Jedis jedis = null;
		try {
			jedis = (Jedis) jedisPool.getResource();
			jedis.subscribe(ps, new String[] { key });
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				this.jedisPool.close();
			}
		}
	}

	public void pubish(String key, String content) {
		Jedis jedis = null;
		try {
			jedis = (Jedis) jedisPool.getResource();
			jedis.publish(key, content);
		} catch (JedisConnectionException e) {
			logger.error("Jedis Connection error", e);
			if (jedis != null) {
				jedisPool.close();
				jedis = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				this.jedisPool.close();
			}
		}
	}

	// ~=============工具========================
	/**
	 * 字节转化为对象
	 * 
	 * @param bytes
	 * @return
	 */
	private Object bytes2Object(byte[] bytes) {
		if (bytes == null || bytes.length == 0)
			return null;
		try {
			ObjectInputStream inputStream;
			inputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
			Object obj = inputStream.readObject();
			return obj;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 对象转化为字节
	 * 
	 * @param value
	 * @return
	 */
	private byte[] object2Bytes(Object value) {
		if (value == null)
			return null;
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream outputStream;
		try {
			outputStream = new ObjectOutputStream(arrayOutputStream);
			outputStream.writeObject(value);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				arrayOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return arrayOutputStream.toByteArray();
	}

	private byte[] getKey(String key) {
		return key.getBytes();
	}

}