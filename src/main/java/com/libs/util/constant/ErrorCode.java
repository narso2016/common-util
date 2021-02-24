package com.libs.util.constant;

public abstract class ErrorCode extends WebErrorCode {
	public static final int ERR_OPENFIRE					= 101;
	public static final int ERR_NO_CONTENT					= 204;
	public static final int ERR_KEY_ERROR					= 300;
	public static final int ERR_INVALID_CRYPTOGRAPH_TYPE 	= 600;
	public static final int ERR_CACHE_ERROR 				= 900;
	public static final int ERR_WS_ERROR 					= 901;
	public static final int ERR_DUMMY_ERROR 				= 902;
	public static final int ERR_PNET 						= 903;
	public static final int ERR_SUBMIT 						= 910;
	public static final int ERR_VALIDATION_PAGE_DATA 		= 950;
	public static final int ERR_VALIDATION_SANCTION_LIST 	= 951;
	public static final int ERR_VALIDATION_PAGE_PRODUCT 	= 952;
	public static final int ERR_VALIDATION_MAX_PRODUCT 		= 953;
	public static final int ERR_VALIDATION_CAPTCHA 			= 954;
	public static final int ERR_VALIDATION_EMAIL_PONSEL 	= 955;
	public static final int ERR_CARD_KID 					= 990;
	public static final int ERR_CARD_DOB_FAIL 				= 991;
	public static final int ERR_CARD_NOT_FOUND 				= 992;
	public static final int ERR_CARD_HOTCARD 				= 993;
	public static final int ERR_CARD_DORMANT 				= 994;
	public static final int ERR_CARD_IPBAN 					= 995;
	public static final int ERR_CARD_RESTRICTED 			= 996;
	public static final int ERR_ZIPCODE 					= 997;
	public static final int ERR_UNKNOWN 					= 9999;
}
