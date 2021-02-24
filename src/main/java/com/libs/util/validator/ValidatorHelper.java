package com.libs.util.validator;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.libs.util.constant.Global;
import com.libs.util.formatter.Formatter;
import com.libs.util.strings.Strings;

public class ValidatorHelper {
	public static final String MASK_DIGIT_ONLY = "[0-9]*";
	public static final String MASK_DIGIT_DOT_ONLY = "[0-9.]*";
	public static final String MASK_DECIMAL_ONLY = "^[0-9]+([.][0-9]{1,2})?";
	public static final String MASK_ALFANUMERIK_ONLY = "[0-9a-zA-Z]*";
	public static final String MASK_ALFANUMERIK_SPACE = "^[0-9a-zA-Z][0-9a-zA-Z ]*";
	public static final String MASK_ALFANUMERIK_KURUNG = "^[0-9a-zA-Z()][0-9a-zA-Z() ]*";
	public static final String MASK_CHAR = "[a-zA-Z]*";
	public static final String MASK_ALFANUMERIK_WITH_DASH = "[0-9a-zA-Z\\-]*";
	public static final String MASK_ALFANUMERIK_WITH_DOT = "[0-9a-zA-Z.]*";
	public static final String MASK_ALFANUMERIK_WITH_UNDERSCORE = "[0-9a-zA-Z_]*";
	public static final String MASK_ALFANUMERIK_WITH_DOT_DASH = "[0-9a-zA-Z.\\-]*";
	public static final String MASK_ALFANUMERIK_WITH_DOT_DASH_UNDERSCORE = "[0-9a-zA-Z._\\-]*";
	public static final String MASK_FLAG_FORMAT_CHAR_SPACE_AND = "[a-zA-Z\\x26]*";
	public static final String MASK_FLAG_FORMAT_LATITUDE = "^-?([1-8]?[1-9]|[1-9]0)\\.{1}\\d{1,7}";
	public static final String MASK_FLAG_FORMAT_LONGITUDE = "^-?([1]?[0-7]?[0-9]|[1]?[0-8]?[0])\\.{1}\\d{1,7}";
	
	public static boolean isMaxLengthValid(String input, int maxLength) {
		boolean valid = true;
		
		if (Strings.isNullOrEmpty(input))
			return true;
		
		int len = input.length();
		
		if (len > maxLength)
			valid = false;
		
		return valid;
	}
	
	public static boolean isMinLengthValid(String input, int minLength) {
		boolean valid = true;
		
		if (Strings.isNullOrEmpty(input))
			return false;
		
		int len = input.length();
		
		if (minLength > len)
			valid = false;
		
		return valid;
	}
	
	public static boolean isLengthValid(String input, int length) {
		boolean valid = true;
		
		if(Strings.isNullOrEmpty(input))
			return false;
		
		int len = input.length();
		
		if (len != length)
			valid = false;
		
		return valid;
	}
	
	public static boolean isLengthRangeValid(String input, int[] range) {
		boolean valid = true;
		
		if (Strings.isNullOrEmpty(input) ||
				range == null ||
				range.length != 2)
			return false;
		
		int len = input.length();
		int min = range[0];
		int max = range[1];
		
		if (len < min || len > max)
			valid = false;
		
		return valid;
	}
	
	public static boolean isDigitOnly(String input) {
		boolean isDigit = true;
		
		isDigit = ValidatorHelper.isMaskValid(input, MASK_DIGIT_ONLY);
		
		return isDigit;
	}
	
	public static boolean isDigitDotOnly(String input) {
		boolean isDigit = true;
		
		isDigit = ValidatorHelper.isMaskValid(input, MASK_DIGIT_DOT_ONLY);
		
		return isDigit;
	}
	
	public static boolean isDecimalOnly(String input) {
		boolean isDigit = true;
		
		isDigit = ValidatorHelper.isMaskValid(input, MASK_DECIMAL_ONLY);
		return isDigit;
	}
	
	public static boolean isLatitudeOnly(String input) {
		boolean isDigit = true;
		
		isDigit = ValidatorHelper.isMaskValid(input, MASK_FLAG_FORMAT_LATITUDE);
		return isDigit;
	}
	
	public static boolean isLongitudeOnly(String input) {
		boolean isDigit = true;
		
		isDigit = ValidatorHelper.isMaskValid(input, MASK_FLAG_FORMAT_LONGITUDE);
		return isDigit;
	}
	
	public static boolean isEmptyNumber(String input, int flag){
		boolean isDigit = false;
		if(ValidatorGlobal.TM_FLAG_FORMAT_DIGIT==flag || ValidatorGlobal.TM_FLAG_FORMAT_DIGIT_DECIMAL==flag 
				|| ValidatorGlobal.TM_FLAG_FORMAT_LATITUDE==flag || ValidatorGlobal.TM_FLAG_FORMAT_LONGITUDE==flag){
			if(ValidatorGlobal.TM_FLAG_FORMAT_DIGIT==flag){
				isDigit = ValidatorHelper.isMaskValid(input, MASK_DIGIT_ONLY);
			}else if(ValidatorGlobal.TM_FLAG_FORMAT_DIGIT_DECIMAL==flag){
				isDigit = ValidatorHelper.isMaskValid(input, MASK_DECIMAL_ONLY);
			}else if(ValidatorGlobal.TM_FLAG_FORMAT_LATITUDE==flag){
				isDigit = ValidatorHelper.isMaskValid(input, MASK_FLAG_FORMAT_LATITUDE);
			}else if(ValidatorGlobal.TM_FLAG_FORMAT_LONGITUDE==flag){
				isDigit = ValidatorHelper.isMaskValid(input, MASK_FLAG_FORMAT_LONGITUDE);
			}
			
			if(isDigit){
				BigDecimal val = new BigDecimal(input);
				isDigit = val.compareTo(BigDecimal.ZERO)==0?false:true;
			}
		}else{
			isDigit = true;
		}
		return isDigit;
	}
	
	public static boolean isAlfanumerikOnly(String input) {
		boolean isAlfanumerik = true;
		
		isAlfanumerik = ValidatorHelper.isMaskValid(input, MASK_ALFANUMERIK_ONLY);
		
		return isAlfanumerik;
	}
	
	public static boolean isAlfanumerik(String input) {
		boolean isAlfanumerik = true;
		
		isAlfanumerik = ValidatorHelper.isMaskValid(input, MASK_ALFANUMERIK_SPACE);
		
		return isAlfanumerik;
	}
	
	public static boolean isAlfanumerikKurung(String input) {
		boolean isAlfanumerik = true;
		
		isAlfanumerik = ValidatorHelper.isMaskValid(input, MASK_ALFANUMERIK_KURUNG);
		
		return isAlfanumerik;
	}
	
	public static boolean isAlfanumerikDash(String input) {
		boolean isAlfanumerik = true;
		
		isAlfanumerik = ValidatorHelper.isMaskValid(input, MASK_ALFANUMERIK_WITH_DASH);
		
		return isAlfanumerik;
	}
	
	public static boolean isAlfanumerikDot(String input) {
		boolean isAlfanumerik = true;
		
		isAlfanumerik = ValidatorHelper.isMaskValid(input, MASK_ALFANUMERIK_WITH_DOT);
		
		return isAlfanumerik;
	}
	
	public static boolean isAlfanumerikUnderscore(String input) {
		boolean isAlfanumerik = true;
		
		isAlfanumerik = ValidatorHelper.isMaskValid(input, MASK_ALFANUMERIK_WITH_UNDERSCORE);
		
		return isAlfanumerik;
	}
	
	public static boolean isAlfanumerikDotDash(String input) {
		boolean isAlfanumerik = true;
		
		isAlfanumerik = ValidatorHelper.isMaskValid(input, MASK_ALFANUMERIK_WITH_DOT_DASH);
		
		return isAlfanumerik;
	}
	
	public static boolean isAlfanumerikDDU(String input) {
		boolean isAlfanumerik = true;
		
		isAlfanumerik = ValidatorHelper.isMaskValid(input, MASK_ALFANUMERIK_WITH_DOT_DASH_UNDERSCORE);
		
		return isAlfanumerik;
	}
	
	public static boolean isChar(String input) {
		boolean isAlfanumerik = true;
		
		isAlfanumerik = ValidatorHelper.isMaskValid(input, MASK_CHAR);
		
		return isAlfanumerik;
	}
	
	public static boolean isSpecialChar(String input) {
		boolean isTrue = true;
		
		isTrue = ValidatorHelper.isMaskValid(input, MASK_FLAG_FORMAT_CHAR_SPACE_AND);
		
		return isTrue;
	}
	
	public static boolean isValidDateDD(String input) {
		boolean valid = true;
		
		if (Strings.isNullOrEmpty(input))
			return false;
		
		try {
			int dd = Integer.parseInt(input);
			if (dd < 1 || dd > 31)
				valid = false;
		}
		catch (Exception ex) {
			valid = false;
		}
			
		return valid;
	}
	
	public static boolean isValidDatePicker(String input) {
		boolean valid = true;
		
		if (Strings.isNullOrEmpty(input))
			return false;
		
		try {
			Formatter.parseDate(input, Global.FROM_STRING_DATE_FORMAT);
		}
		catch (Exception ex) {
			valid = false;
		}
			
		return valid;
	}
	
	public static boolean isEqual(int val, int min) {
		return val == min;		
	}
	
	public static boolean isEqual(BigDecimal val, BigDecimal min) {
		return val.compareTo(min)==0;		
	}
	
	
	public static boolean isGreaterThanMin(int val, int min) {
		return val > min;		
	}
	
	public static boolean isGreaterThanMin(BigDecimal val, BigDecimal min) {
		return val.compareTo(min)==1;		
	}
	
	public static boolean isLessThanMax(int val, int max) {
		return val < max;		
	}
	
	public static boolean isLessThanMax(BigDecimal val, BigDecimal max) {
		return val.compareTo(max)==-1;		
	}
	
	public static boolean isGreaterThanEqualMin(int val, int min) {
		return val >= min;		
	}
	
	public static boolean isGreaterThanEqualMin(BigDecimal val, BigDecimal min) {
		return val.compareTo(min)>=0;		
	}
	
	public static boolean isLessThanEqualMax(int val, int max) {
		return val <= max;		
	}
	
	public static boolean isLessThanEqualMax(BigDecimal val, BigDecimal max) {
		return val.compareTo(max)<=0;	
	}
	
	public static boolean isInRange(int val, int[] range) {
		boolean isIn = true;
		
		if (range == null || range.length != 2)
			return false;

		int min = range[0];
		int max = range[1];
		
		if (val < min || val > max)
			isIn = false;
		
		return isIn;
	}
	
	public static boolean isInEqualRange(int val, int[] range) {
		boolean isIn = true;
		
		if (range == null || range.length != 2)
			return false;

		int min = range[0];
		int max = range[1];
		
		if (val <= min || val >= max)
			isIn = false;
		
		return isIn;
	}
	
	public static boolean isInMinEqualRange(int val, int[] range) {
		boolean isIn = true;
		
		if (range == null || range.length != 2)
			return false;

		int min = range[0];
		int max = range[1];
		
		if (val < min || val >= max)
			isIn = false;
		
		return isIn;
	}
	
	public static boolean isInMaxEqualRange(int val, int[] range) {
		boolean isIn = true;
		
		if (range == null || range.length != 2)
			return false;

		int min = range[0];
		int max = range[1];
		
		if (val <= min || val > max)
			isIn = false;
		
		return isIn;
	}
	
	public static boolean isMaskValid(String in, String mask) {
		if(null==in){
			return true;
		}
		boolean valid = true;
			
		Pattern pattern = Pattern.compile(mask);
		Matcher matcher = pattern.matcher(in);
		
		if (!matcher.matches()) {
			valid = false;
		}
		
		return valid;
	}
	
	public static boolean isValidEmail(String in) {
		if (Strings.isNullOrEmpty(in))
			return false;
		
		return Pattern.compile("\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+")
				.matcher(in).matches();
	}
	
	public static boolean isValidEmailBillgen(String in) {
		if (Strings.isNullOrEmpty(in))
			return true;
		
		return Pattern.compile("\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+")
				.matcher(in).matches();
	}
	
	public static boolean isModulusZero(String in, int divider) {
		boolean valid = true;
		
		if (!isDigitOnly(in))
			return false;
		
		long l = Long.parseLong(in);
		if (l % divider != 0)
			valid = false;
		
		return valid;
	}
	
	public static boolean isPonselNoRepeat(String in, int noOfRepeat) {
		if (in == null || in.length() == 0 || in.length() < noOfRepeat)
			return true;
		
		boolean valid = true;
		
		char ch = in.charAt(0);
		int occurence = 0;
		
		for (int i = 0; i < noOfRepeat; i++) {
			if (in.charAt(i) == ch) {
				occurence++;
			}
			else {
				occurence = 0;
			}
		}
		
		if (occurence >= noOfRepeat) {
			return false;
		}
		
		return valid;
	}
	
	public static boolean isLeadingZero(String in) {
		if (in == null || in.length() == 0)
			return true;
		
		char ch = in.charAt(0);
		if (ch == '0')
			return true;
		else
			return false;
	}
	
	
}