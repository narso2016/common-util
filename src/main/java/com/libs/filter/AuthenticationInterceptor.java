package com.libs.filter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.libs.util.constant.AuthenticationConstant;
import com.libs.config.SessionManager;
import com.libs.persistence.entity.SysUser;
import com.libs.persistence.service.UserServiceDAO;
import com.libs.annotation.Authenticate;
import com.libs.util.constant.Global;
import com.libs.util.redis.RedisUtil;
import com.libs.util.security.AuthenticatedUser;
import com.libs.util.security.jwt.JWTUtil;
import com.libs.util.strings.Strings;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class AuthenticationInterceptor implements HandlerInterceptor {

	@Autowired
	SessionManager sessionManager;

	@Autowired
	RedisUtil redis;
	
	@Autowired
	UserServiceDAO userService;
	
	@Value(AuthenticationConstant.JWT_CONFIG_PARSER_HEADER)
	String prefix;
	
	@Value(AuthenticationConstant.JWT_KEY_ENCRIPTION_KEY)
	private String secretKey;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HandlerMethod hm = (HandlerMethod) handler;
		Authenticate authAnnotation = AnnotationUtils.findAnnotation(hm.getMethod(), Authenticate.class);
		if (authAnnotation == null) {
			return true;
		}

		AuthenticatedUser user = sessionManager.getLoginUser();
		if (user == null) {
			String token = request.getHeader(AuthenticationConstant.JWT_AUTHORIZATION_HEADER);
			if(!Strings.isNullOrEmpty(token)) {
				token = token.substring(7);
				Claims claim =  Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
				if(null!=claim) {
					String subject = (String) claim.get(AuthenticationConstant.JWT_SUBJECT_CLAIM);
					JWTUtil.invalidateRelatedTokens(redis, prefix, subject, token);
					SysUser userDB = userService.getSingleUserCache(subject);
					if(null!=userDB) {
						userDB.setLogedin(false);
						userService.saveOrUpdate(userDB);
					}
				}
			}
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, AuthenticationConstant.UNAUTHORIZED_MESSAGE);
			return false;
		}

		if (AuthenticationConstant.AUTHENTICATION_METHOD_JWT.equalsIgnoreCase(user.getAuthenticatedMethod())) {
			// login jwt
			String token = request.getHeader(AuthenticationConstant.JWT_AUTHORIZATION_HEADER);
			if (Strings.isNullOrEmpty(token)) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, AuthenticationConstant.UNAUTHORIZED_MESSAGE);
				return false;
			}
			
			if(!token.startsWith(prefix+" ")) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, AuthenticationConstant.UNAUTHORIZED_MESSAGE);
				return false;
			}

			token = token.substring(7);
			if (redis.sismember(Global.REDIS_SET_INVALID_SUBJECTS, user.getUserName())) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, AuthenticationConstant.UNAUTHORIZED_MESSAGE);
				return false;
			}
		} else if (AuthenticationConstant.AUTHENTICATION_METHOD_DB.equalsIgnoreCase(user.getAuthenticatedMethod())) {
			// login db
		} else if (AuthenticationConstant.AUTHENTICATION_METHOD_LDAP.equalsIgnoreCase(user.getAuthenticatedMethod())) {
			// login ldap
		} else {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, AuthenticationConstant.UNAUTHORIZED_MESSAGE);
			return false;
		}

		String[] methodServices = authAnnotation.value();
		if (methodServices == null || methodServices[0] == null || methodServices[0].equals(""))
			return true;

		List<String> userRoles = user.getRoles();
		for (String methodService : methodServices) {
			for (String userService : userRoles) {
				if (userService.equals(methodService))
					return true;
			}
		}

		response.sendError(HttpServletResponse.SC_FORBIDDEN, AuthenticationConstant.FORBIDDEN_MESSAGE);
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
