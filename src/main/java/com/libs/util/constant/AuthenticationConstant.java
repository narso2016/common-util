package com.libs.util.constant;

public abstract class AuthenticationConstant {
	public static final String UNAUTHORIZED_MESSAGE = "You do not have authorization";
	public static final String FORBIDDEN_MESSAGE = "You do not have access";
	
	public static final String AUTHENTICATION_METHOD_JWT = "JWT";
	public static final String AUTHENTICATION_METHOD_DB = "DB";
	public static final String AUTHENTICATION_METHOD_LDAP = "LDAP";
	
	public static final String LOGIN_JWT_ADDR = "loginJWT";
	public static final String LOGIN_STANDARD_ADDR = "login";
	public static final String VALIDATE_JWT = "jwt";
	public static final String WHO_AM_I_ADDR = "whoAmI";
	public static final String JWT_PARSER = "testRequest";
	public static final String LOGOUT = "logout";
	
//	public static final String CONFIG_JWT_PARSER_HEADER = "pucuk"; 
//	
//	public static final String CONFIG_WS_GET = "ws/get/";
//		
//	public static final String LDAP_HOST_KEY = "ldap.host";
//	public static final String LDAP_DOMAIN_KEY = "ldap.domain";
//	public static final String LDAP_NAME_FIELD_KEY = "ldap.name.field";
//	public static final String LDAP_NIK_FIELD_KEY = "ldap.nik.field";
//	public static final String LDAP_SEARCH_BASE_KEY = "ldap.search.base";
	
	public static final String JWT_AUTHORIZATION_HEADER = "Authorization";	
	public static final String JWT_CONFIG_PARSER_HEADER = "${spring.security.authentication.jwt.name}";
	public static final String JWT_KEY_ENCRIPTION_KEY = "${spring.security.authentication.jwt.secret}";
	public static final String JWT_SUBJECT_CLAIM = "subject";
	public static final String JWT_NAME_CLAIM = "name";
	public static final String JWT_ROLE_NAME_CLAIM = "roleName";
	public static final String JWT_BRANCH_CLAIM = "branch";
	public static final String JWT_BRANCH_NAME_CLAIM = "branchname";
	public static final String JWT_ROLE_CLAIM = "role";
	public static final String JWT_SERVICE_CLAIM = "service";
	public static final String JWT_NAVBAR_CLAIM = "navbar";

	public static final String AUTHENTICATION_ENGINE_KEY = "auth.engine";
	public static final String AUTHENTICATION_ENGINE_DB = "DB";
	public static final String AUTHENTICATION_ENGINE_LDAP = "LDAP";

	public static final String PASSWORD_SALT_KEY = "${spring.security.authentication.hive}";	
	public static final String PASSWORD_FAIL_LOGIN_COUNT = "${spring.security.authentication.count}";

	public static final String USER_DORMANT_MONTH = "${spring.security.authentication.dormant}";

	public static final String CONFIG_JWT_ENABLE = "${local.security.jwt.enable}";
	public static final String CONFIG_SESSION_ENABLE = "${local.security.session.enable}";
	
	public static final int IS_TRUE = 1;
	
	public static final String INVALID_ALREADY_LOGIN_ERROR_CODE = "L00";
	public static final String INVALID_ALREADY_LOGIN_ERROR_DESC = "User Already Login, Please Contact Administrator to release";
	public static final String JWT_EXPIRED_ERROR_CODE = "L01";
	public static final String JWT_EXPIRED_ERROR_DESC = "JWT Expired";
	public static final String JWT_MALFORMED_ERROR_CODE = "L02";
	public static final String JWT_MALFORMED_ERROR_DESC = "JWT Malformed";
	public static final String JWT_UNSUPPORTED_ERROR_CODE = "L03";
	public static final String JWT_UNSUPPORTED_ERROR_DESC = "JWT Unsupported";
	public static final String JWT_PARSE_ERROR_CODE = "L04";
	public static final String JWT_PARSE_ERROR_DESC = "JWT Parse Error";
	public static final String USER_DB_NOT_FOUND_ERROR_CODE = "L05";
	public static final String USER_DB_NOT_FOUND_ERROR_DESC = "User Not Found";
	public static final String INVALID_PASSWORD_DB_ERROR_CODE = "L05";
	public static final String INVALID_PASSWORD_DB_ERROR_DESC = "Invalid Password";
	public static final String INVALID_LOGIN_LDAP_ERROR_CODE = "L06";
	public static final String INVALID_LOGIN_LDAP_ERROR_DESC = "Invalid LDAP Login";
	public static final String USER_DB_UNAPPROVE_ERROR_CODE = "L07";
	public static final String USER_DB_UNAPPROVE_ERROR_DESC = "User is not approved yet";
	public static final String USER_DB_DORMANT_ERROR_CODE = "L08";
	public static final String USER_DB_DORMANT_ERROR_DESC = "User is Dormant, Harap Hubungi Administrator atau Cabang Bank DKI terdekat ";
	public static final String USER_DB_EXPIRED_ERROR_CODE = "L09";
	public static final String USER_DB_EXPIRED_ERROR_DESC = "User is expired";
	public static final String USER_DB_LOCKED_ERROR_CODE = "L10";
	public static final String USER_DB_LOCKED_ERROR_DESC = "User is locked, Harap Hubungi Administrator atau Cabang Bank DKI terdekat ";
	public static final String USER_DB_EXPIRED = "L11";
	public static final String USER_DB_EXPIRED_DESC = "User password is expired ";
	
}
