package com.libs.util.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import com.libs.util.constant.Global;
import com.libs.util.cookie.CookieUtil;
import com.libs.util.redis.RedisUtil;
import com.libs.util.strings.Strings;

public class JWTUtil {
	 public static final Long EXPIRATION_TIME = 1000L*60*30;
	
	public static String generateToken(RedisUtil redis, String jwtName, String signingKey, String username) {
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		String subject = username;
		JwtBuilder builder = Jwts.builder().setSubject(subject).setIssuedAt(now).signWith(SignatureAlgorithm.HS256, signingKey);
		String token = builder.compact();
		redis.sadd(Global.REDIS_SET_ACTIVE_SUBJECTS, subject);
		redis.sadd(Global.REDIS_SET_ACTIVE_JWT_SUBJECTS, token);
		return jwtName+" "+token;
	}
	
	public static String generateToken(RedisUtil redis, String jwtName, String signingKey, String username, Map<String, Object> claims) {
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		Date exp = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
		String subject = username;
		JwtBuilder builder = Jwts.builder().setSubject(subject).setClaims(claims).setIssuedAt(now).signWith(SignatureAlgorithm.HS256, signingKey).setExpiration(exp);
		String token = builder.compact();
		redis.sadd(Global.REDIS_SET_ACTIVE_SUBJECTS, subject);
		redis.sadd(Global.REDIS_SET_ACTIVE_JWT_SUBJECTS, token);
		return jwtName+" "+token;
	}
	
	public static String generateTokenNonExpired(RedisUtil redis, String jwtName, String signingKey, String username, Map<String, Object> claims) {
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
//		Date exp = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
		String subject = username;
		JwtBuilder builder = Jwts.builder().setSubject(subject).setClaims(claims).setIssuedAt(now).signWith(SignatureAlgorithm.HS256, signingKey);
		String token = builder.compact();
		redis.sadd(Global.REDIS_SET_ACTIVE_SUBJECTS, subject);
		redis.sadd(Global.REDIS_SET_ACTIVE_JWT_SUBJECTS, token);
		return jwtName+" "+token;
	}
	
	public static String generateTokenWithExp(RedisUtil redis, String jwtName, String signingKey, 
			String username, Map<String, Object> claims, Date exp) {
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		String subject = username;
		JwtBuilder builder = Jwts.builder().setSubject(subject).setClaims(claims).setIssuedAt(now).signWith(SignatureAlgorithm.HS256, signingKey).setExpiration(exp);
		String token = builder.compact();
		redis.sadd(Global.REDIS_SET_ACTIVE_SUBJECTS, subject);
		redis.sadd(Global.REDIS_SET_ACTIVE_JWT_SUBJECTS, token);
		return jwtName+" "+token;
	}
	
	public static String parseToken(RedisUtil redis, String signingKey, String token, String prefix) {
		
		if (token == null) {
			return null;
		}
		
		if(!token.startsWith(prefix)) {
			return null;
		}

		String subject = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody().getSubject();
		if (!redis.sismember(Global.REDIS_SET_ACTIVE_SUBJECTS, subject) || 
				!redis.sismember(Global.REDIS_SET_ACTIVE_JWT_SUBJECTS, token) ||
				redis.sismember(Global.REDIS_SET_INVALID_SUBJECTS, token)){
			return null;
		}

		return subject;
	}
	
	public static String parseToken(RedisUtil redis, HttpServletRequest httpServletRequest, String jwtTokenCookieName, String signingKey) {
		String token = CookieUtil.getValue(httpServletRequest, jwtTokenCookieName);
		if (token == null) {
			return null;
		}

		String subject = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody().getSubject();
		if (!redis.sismember(Global.REDIS_SET_ACTIVE_SUBJECTS, subject) || 
				!redis.sismember(Global.REDIS_SET_ACTIVE_JWT_SUBJECTS, token) ||
				redis.sismember(Global.REDIS_SET_INVALID_SUBJECTS, token)){
			return null;
		}

		return subject;
	}

	public static void invalidateRelatedTokens(RedisUtil redis, String jwtName, String usr, String token) {
		if(!Strings.isNullOrEmpty(token)) {
			if(token.startsWith(jwtName)) {
				token = token.substring(7);
			}
			redis.sadd(Global.REDIS_SET_INVALID_SUBJECTS, token);
			redis.srem(Global.REDIS_SET_ACTIVE_JWT_SUBJECTS, token);
			redis.srem(Global.REDIS_SET_ACTIVE_SUBJECTS, usr);
		}
	}
	
	public static String createJWT(String issuer, String subject, long ttlMillis, Map<String, String> claims, String keyEncription) {
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(keyEncription);

		JwtBuilder builder = Jwts.builder()
								.setId(UUID.randomUUID().toString())
								.setIssuedAt(now)
								.setSubject(subject)
								.setIssuer(issuer)
								.signWith(SignatureAlgorithm.HS512, apiKeySecretBytes);
		claims.forEach((k,v) -> builder.claim(k, v));
		
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}

		return builder.compact();
	}

	public static Claims parseJWT(String jwt, String keyEncription) {
		Claims claims = Jwts.parser()
							.setSigningKey(DatatypeConverter.parseBase64Binary(keyEncription))
							.parseClaimsJws(jwt)
							.getBody();
		return claims;
	}

}
