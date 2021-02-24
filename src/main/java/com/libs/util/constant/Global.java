package com.libs.util.constant;

import java.text.NumberFormat;
import com.libs.util.formatter.Formatter;
import com.libs.util.formatter.NumberFormatter;

public abstract class Global {

	protected Global() {
		throw new IllegalStateException("Global class shall not be instantiated! Class=" + this.getClass().getName());
	}

	public static final String WEBLOGIC_CONTEXT_FACTORY = "weblogic.jndi.WLInitialContextFactory";
	
	public static final int ROW_PER_PAGE = 10;
	
	public static final int ROW_PER_PAGE_DEPENDENCY_QUESTION = 1;
	
	public static final String LANG_ID = "id";
	public static final String LANG_EN = "en";
	public static final String LANG = "LANG";
	
	public static final String CRYPTOGRAPH_RC4 = "RC4";
	public static final String CRYPTOGRAPH_AES = "AES";
	
	public static final String SESSION_LOGIN = "SESSION";
	public static final String SESSION_FROM_DASHBOARD_USER = "DASH_USER";
	public static final String SESSION_FROM_DASHBOARD_KEY = "DASH_KEY";
	public static final String SESSION_FROM_DASHBOARD_URI = "DASH_URI";
	public static final String SESSION_PAGE_QUESTION = "QUESTION_PG";
	public static final String SESSION_SEARCH_TYPE_QUESTION = "QUESTION_SEARCH_TYPE";
	public static final String SESSION_SEARCH_VALUE_QUESTION = "QUESTION_SEARCH_VALUE";
	
	public static final String TO_STRING_DATE_FORMAT = "dd-MMM-yyyy";
	public static final String FROM_STRING_DATE_FORMAT = "ddMMyyyy";
	
	public static final NumberFormat FROM_TO_STRING_DOUBLE_FORMAT = Formatter.getNumberFormat(new NumberFormatter(',', '.', 2, false));
	
	public static final String FIELD_DELIMITER = ";";
	public static final String RECORD_DELIMITER = "|";
	public static final String VERSION_DELIMITER = "#";
	public static final String ARRAY_DELIMITER = ":";
	public static final String ARRAY_COMMA_DELIMITER = ",";
	public static final String SPACE_DELIMITER = " ";
	public static final String DEFAULT_REPORT_FILE = "assignment.rptdesign";
	public static final String VIEW_DATETIME_FORMAT = "dd-MMM-yyyy HH:mm:ss";
	public static final String REQUEST_ATTR_IMG_ICON = "imgIcon";
	
	public static final String STRING_TRUE = "1";
	public static final String STRING_FALSE = "0";
	
	public static final int MIN_PASSWORD = 8;
	
	public static final String CONFIG_DOCKER_IP = "config.docker.host.addr";
	public static final String CONFIG_PORT = "server.port";
	public static final String CONFIG_DOCKER_PORT = "config.port.addr";
	
	public static final String REDIS_HOST = "spring.redis.host";
	public static final String REDIS_PASSWORD = "spring.redis.password";
	public static final String REDIS_PORT = "spring.redis.port";

	public static final String REDIS_SET_ACTIVE_SUBJECTS = "active-subjects";
	public static final String REDIS_SET_ACTIVE_JWT_SUBJECTS = "active-jwt-subjects";
	public static final String REDIS_SET_INVALID_SUBJECTS = "invalid-subjects";
	

	public static final String JWT_CONFIG_PARSER_HEADER = "${spring.security.authentication.jwt.name}";
	
	/**
	 * URL address
	 */
	public static final String URI_APPROVAL_PAGE_ADDR = "anklang";
	
	/**
	 *	Error Desc 
	 */
	public static final String ERROR_NOT_FOUND = "Not Found";
	public static final String ERROR_NO_DATA_FOUND = "No Data Found";
	public static final String ERROR_KEY = "Invalid Input Key";
	public static final String ERROR_INVALID_REQUEST = "Invalid Request";
	public static final String ERROR_UNSUPPORTED_MEDIA_TYPE = "Invalid extention! Please use #EXT# file";
	public static final String ERROR_MAX_SIZE_MEDIA = "Your file is to large! Maximum file size is #FILESIZE#";
}
