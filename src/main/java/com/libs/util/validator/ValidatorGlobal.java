package com.libs.util.validator;

public abstract class ValidatorGlobal {
	public static final int TM_FLAG_FORMAT_NOT_CHECKED = -1;
	public static final int TM_FLAG_FORMAT_DIGIT_DECIMAL = 0;
	public static final int TM_FLAG_FORMAT_DIGIT = 1;
	public static final int TM_FLAG_FORMAT_CHAR = 2;
	public static final int TM_FLAG_FORMAT_ALFANUMERIK = 3;
	public static final int TM_FLAG_FORMAT_ALFANUMERIK_SPACE = 4;
	public static final int TM_FLAG_FORMAT_ALFANUMERIK_WITH_DASH = 5;
	public static final int TM_FLAG_FORMAT_ALFANUMERIK_WITH_DOT = 6;
	public static final int TM_FLAG_FORMAT_ALFANUMERIK_WITH_UNDERSCORE = 7;
	public static final int TM_FLAG_FORMAT_ALFANUMERIK_WITH_DOT_DASH = 8;
	public static final int TM_FLAG_FORMAT_ALFANUMERIK_WITH_DOT_DASH_UNDERSCORE = 9;
	public static final int TM_FLAG_FORMAT_CHAR_SPACE_AND = 10;
	public static final int TM_FLAG_FORMAT_DIGIT_DECIMAL_ALLOW_ZERO = 11;
	public static final int TM_FLAG_FORMAT_DIGIT_ALLOW_ZERO = 12;
	public static final int TM_FLAG_FORMAT_ALFANUMERIK_KURUNG = 13;
	public static final int TM_FLAG_FORMAT_ALFANUMERIK_FREE = 14;
	public static final int TM_FLAG_FORMAT_LATITUDE = 15;
	public static final int TM_FLAG_FORMAT_LONGITUDE = 16;
	
	public static final int TM_FLAG_MANDATORY = 1;
	public static final int TM_FLAG_NON_MANDATORY = 0;
	
	public static final int TM_NOT_CHECKED_LENGTH = 0;
	public static final int TM_CHECKED_LENGTH_FIX = 1;
	public static final int TM_CHECKED_LENGTH_RANGE = 2;
	public static final int TM_CHECKED_LENGTH_RANGE_NOT_EQUAL = 3;
	public static final int TM_CHECKED_LENGTH_RANGE_FOLLOW_MIN = 4;
	public static final int TM_CHECKED_LENGTH_RANGE_FOLLOW_MAX = 5;
	public static final int TM_CHECKED_LENGTH_MAX = 6;
	public static final int TM_CUSTOM_EMAIL_FLAG = -1;
	
	public static final int TM_TYPE_PHONE_AREA = 1;
	public static final int TM_TYPE_PHONE = 2;
	public static final int TM_TYPE_PHONE_EXT = 3;
	public static final int TM_TYPE_PONSEL_AREA = 4;
	public static final int TM_TYPE_PONSEL = 5;
	public static final int TM_TYPE_FAX_AREA = 6;
	public static final int TM_TYPE_FAX = 7;
	
	
	public static final String TM_DATE_DMY 							= "ddMMyyyy";
	public static final String TM_DATE_DMY_SHORT 					= "ddMMyy";
	public static final String TM_DATE_DMY_SHORT_SEPARATOR 			= "dd/MM/yy";
	public static final String TM_DATE_DMY_SHORT_SEPARATOR_OTHER 	= "dd-MM-yy";
	public static final String TM_DATE_DMY_SEPARATOR 				= "dd/MM/yyyy";
	public static final String TM_DATE_DMY_SEPARATOR_OTHER 			= "dd-MM-yyyy";
	public static final String TM_DATE_YMD 							= "yyyyMMdd";
	public static final String TM_DATE_YMD_SHORT 					= "yyMMdd";
	public static final String TM_DATE_YMD_SHORT_SEPARATOR 			= "yy/MM/dd";
	public static final String TM_DATE_YMD_SHORT_SEPARATOR_OTHER 	= "yy-MM-dd";
	public static final String TM_DATE_YMD_SEPARATOR 				= "yyyy/MM/dd";
	public static final String TM_DATE_YMD_SEPARATOR_OTHER 			= "yyyy-MM-dd";
	public static final String TM_TIME_HM 							= "hh:mm";
	public static final String TM_TIME_HMS 							= "hh:mm:ss";
	public static final String TM_DATE_DMY_TIME_HM 					= "ddMMyyyy HHmm";
	public static final String TM_DATE_DMY_TIME_HMS 				= "dd-MM-yyyy HH:mm:ss";

	public static final String TM_OPERATOR_KURANG_DARI 				= "1";
	public static final String TM_OPERATOR_KURANG_DARI_SAMA_DENGAN 	= "2";
	public static final String TM_OPERATOR_LEBIH_DARI 				= "3";
	public static final String TM_OPERATOR_LEBIH_DARI_SAMA_DENGAN 	= "4";
	public static final String TM_OPERATOR_SAMA_DENGAN 				= "5";
}
