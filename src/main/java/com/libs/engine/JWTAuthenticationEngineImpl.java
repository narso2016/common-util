package com.libs.engine;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.libs.util.constant.AuthenticationConstant;
import com.libs.response.exception.UserException;
import com.libs.util.security.AuthenticatedUser;
import com.libs.util.security.jwt.JWTUtil;
import com.libs.util.strings.Strings;

public class JWTAuthenticationEngineImpl implements JWTAuthenticationEngine {
	
	@Value(AuthenticationConstant.JWT_KEY_ENCRIPTION_KEY)
	private String secretKey;

	@Value(AuthenticationConstant.JWT_CONFIG_PARSER_HEADER)
	String prefix;
	
	Logger logger = LoggerFactory.getLogger(JWTAuthenticationEngine.class);


	@Override
	@SuppressWarnings("unchecked")
	public AuthenticatedUser authenticate(String jwt) throws Exception {
		try {
			if(!jwt.startsWith(prefix+" ")) {
				throw new UserException(AuthenticationConstant.JWT_UNSUPPORTED_ERROR_CODE, AuthenticationConstant.JWT_UNSUPPORTED_ERROR_DESC);
			}

			jwt = jwt.substring(7);
			
			Claims claims = JWTUtil.parseJWT(jwt, secretKey);
			AuthenticatedUser usr = new AuthenticatedUser();
			String userName = Strings.isNullOrEmpty(claims.getSubject())?(String) claims.get(AuthenticationConstant.JWT_SUBJECT_CLAIM):claims.getSubject();
			String name = (String) claims.get(AuthenticationConstant.JWT_NAME_CLAIM);
			String roleName = (String) claims.get(AuthenticationConstant.JWT_ROLE_NAME_CLAIM);
			
			List<String> roles = (List<String>) claims.get(AuthenticationConstant.JWT_ROLE_CLAIM);
			List<String> services = (List<String>) claims.get(AuthenticationConstant.JWT_SERVICE_CLAIM);
			List<String> navbars = (List<String>) claims.get(AuthenticationConstant.JWT_NAVBAR_CLAIM); 
			String branchCode = (String)claims.get(AuthenticationConstant.JWT_BRANCH_CLAIM);
			String branchName = (String)claims.get(AuthenticationConstant.JWT_BRANCH_NAME_CLAIM);
			List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
			roles.forEach((role) -> authorities.add(new SimpleGrantedAuthority(role)));
			
			usr.setUserName(userName);			
			usr.setName(name);
			usr.setRoleName(roleName);
			usr.setAuthenticatedMethod(AuthenticationConstant.AUTHENTICATION_METHOD_JWT);
			usr.setRoles(roles);
			usr.setServices(services);	
			usr.setNavbars(navbars);
			usr.setBranchCode(branchCode);
			usr.setBranchName(branchName);
			return usr;
		} catch (ExpiredJwtException e) {
			logger.error("JWT Expired", e);
			throw new UserException(AuthenticationConstant.JWT_EXPIRED_ERROR_CODE, AuthenticationConstant.JWT_EXPIRED_ERROR_DESC);
		} catch (UnsupportedJwtException e) {
			logger.error("JWT Unsupported", e);
			throw new UserException(AuthenticationConstant.JWT_UNSUPPORTED_ERROR_CODE, AuthenticationConstant.JWT_UNSUPPORTED_ERROR_DESC);
		} catch (MalformedJwtException e) {
			logger.error("JWT Malformed", e);
			throw new UserException(AuthenticationConstant.JWT_MALFORMED_ERROR_CODE, AuthenticationConstant.JWT_MALFORMED_ERROR_DESC);
		} catch (Exception e) {
			logger.error("Failed to parse JSON Web Token", e);
			throw new UserException(AuthenticationConstant.JWT_PARSE_ERROR_CODE, AuthenticationConstant.JWT_PARSE_ERROR_DESC);
		}
	}
	
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}	
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
  
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}
	
	//check if the token has expired
		@SuppressWarnings("unused")
		private Boolean isTokenExpired(String token) {
			final Date expiration = getExpirationDateFromToken(token);
			return expiration.before(new Date());
		}
		
	@Override
	public AuthenticatedUser authenticateToken(String jwt) throws Exception {
		try {
			if(!jwt.startsWith(prefix+" ")) {
				throw new UserException(AuthenticationConstant.JWT_UNSUPPORTED_ERROR_CODE, AuthenticationConstant.JWT_UNSUPPORTED_ERROR_DESC);
			}

			jwt = jwt.substring(7);
			
			Claims claims = JWTUtil.parseJWT(jwt, secretKey);
			AuthenticatedUser usr = new AuthenticatedUser();
			String userName = Strings.isNullOrEmpty(claims.getSubject())?(String) claims.get(AuthenticationConstant.JWT_SUBJECT_CLAIM):claims.getSubject();
			String name = (String) claims.get(AuthenticationConstant.JWT_NAME_CLAIM);

			String branchCode = (String)claims.get(AuthenticationConstant.JWT_BRANCH_CLAIM);
			String branchName = (String)claims.get(AuthenticationConstant.JWT_BRANCH_NAME_CLAIM);
			
			usr.setUserName(userName);			
			usr.setName(name);
			usr.setAuthenticatedMethod(AuthenticationConstant.AUTHENTICATION_METHOD_JWT);
			usr.setBranchCode(branchCode);
			usr.setBranchName(branchName);
			return usr;
		} catch (ExpiredJwtException e) {
			logger.error("JWT Expired", e);
			throw new UserException("401", AuthenticationConstant.JWT_EXPIRED_ERROR_DESC);
		} catch (UnsupportedJwtException e) {
			logger.error("JWT Unsupported", e);
			throw new UserException(AuthenticationConstant.JWT_UNSUPPORTED_ERROR_CODE, AuthenticationConstant.JWT_UNSUPPORTED_ERROR_DESC);
		} catch (MalformedJwtException e) {
			logger.error("JWT Malformed", e);
			throw new UserException(AuthenticationConstant.JWT_MALFORMED_ERROR_CODE, AuthenticationConstant.JWT_MALFORMED_ERROR_DESC);
		} catch (Exception e) {
			logger.error("Failed to parse JSON Web Token", e);
			throw new UserException(AuthenticationConstant.JWT_PARSE_ERROR_CODE, AuthenticationConstant.JWT_PARSE_ERROR_DESC);
		}
	}
	
}
