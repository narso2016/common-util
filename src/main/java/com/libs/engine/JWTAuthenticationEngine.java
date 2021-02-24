package com.libs.engine;

import com.libs.util.security.AuthenticatedUser;

public interface JWTAuthenticationEngine {
	AuthenticatedUser authenticate(String jwt) throws Exception;
	AuthenticatedUser authenticateToken(String jwt) throws Exception;
}
