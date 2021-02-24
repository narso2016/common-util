package com.libs.engine;

import com.libs.util.security.AuthenticatedUser;

public interface StandardAuthenticationEngine {

	AuthenticatedUser authenticate(String username, String password) throws Exception;
	
	AuthenticatedUser authenticate(String username, String password, String methode) throws Exception;
	
	AuthenticatedUser authenticateUserIdOnly(String username, String methode) throws Exception;
	
	AuthenticatedUser authenticateUsername(String username, String methode) throws Exception;
	
	void logoutUser(String username) throws Exception;
}
