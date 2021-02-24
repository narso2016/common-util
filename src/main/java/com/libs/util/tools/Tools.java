package com.libs.util.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Tools {
	
	private static final String NEW_LINE = System.getProperty("line.separator");

	private Tools() {
	}

	public static String handleNull(String toBeChecked, String replacement) {
		return (toBeChecked == null || "null".equals(toBeChecked)) ? replacement
				: toBeChecked;
	}

	public static boolean isInteger(String num) {
		boolean result = true;
		try {
			Integer.parseInt(num);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	public static String replace(String string, String key, String replacement) {
		int index = 0;
		StringBuffer newValue = new StringBuffer(string);

		while ((index = newValue.toString().indexOf(key)) != -1) {
			newValue = newValue.replace(index, index + key.length(),
					replacement);
		}
		return newValue.toString();
	}

	public static Date getSystemDateTime() {
		return new Date(System.currentTimeMillis());
	}

	public static Date getSystemDate() {
		return truncTime(getSystemDateTime());
	}

	public static int indexOf(String[] arr, String val) {
		int result = -1;
		if (arr == null || val == null)
			return result;
		for (int i = 0; i < arr.length && (result == -1); i++) {
			if (arr[i] == null)
				continue;
			if (arr[i].equals(val))
				result = i;
		}
		return result;
	}

	public static Date truncTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}


	public static String getAllParameterValues(HttpServletRequest req,
			String[] includeParams, String[] encodeParams) {
		StringBuffer sb = new StringBuffer();
		boolean first = true;
		try {
			Enumeration en = req.getParameterNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String value = req.getParameter(key);
				if (value != null
						&& (includeParams == null || (indexOf(includeParams,
								key) != -1))) {
					if (!first)
						sb.append("&");
					else
						first = false;
					if (encodeParams != null
							&& (indexOf(encodeParams, key) != -1)) {
						value = java.net.URLEncoder.encode(value, "UTF-8");
					}
					sb.append(key).append("=").append(value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static String getCompleteUri(HttpServletRequest req,
			String[] includeParams, String[] encodeParams) {
		String result = req.getRequestURI() + "?"
				+ getAllParameterValues(req, includeParams, encodeParams);
		try {
			result = java.net.URLEncoder.encode(result, "UTF-8");
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getCompleteUri(HttpServletRequest req) {
		return getCompleteUri(req, null, null);
	}

	public static String getCompleteUriPlain(HttpServletRequest req,
			String[] includeParams) {
		String result = req.getRequestURI() + "?"
				+ getAllParameterValues(req, includeParams, null);
		return result;
	}

	public static String[] explode(String str, String delimiter) {
		StringTokenizer st = new StringTokenizer(str, delimiter);
		String[] result = new String[st.countTokens()];
		for (int i = 0; i < result.length; i++)
			result[i] = st.nextToken();
		return result;
	}

	public static String implode(String prefix, String[] arr, String delimiter) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (i > 0)
				result.append(delimiter);
			result.append(prefix.concat(".").concat(arr[i]));
		}
		return result.toString();
	}
	
	public static String implode(String[] arr, String delimiter) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (i > 0)
				result.append(delimiter);
			result.append(arr[i]);
		}
		return result.toString();
	}

	public static boolean isInArray(String[] arr, String value) {
		boolean result = false;
		for (int i = 0; i < arr.length && !result; i++) {
			if (arr[i] == null) {
				continue;
			}
			result = (arr[i]).equals(value);
		}
		return result;
	}

	public static String leftPad(String value, char padChar, int len) {
		if (len - value.length() <= 2) { // for optimization
			return padString(value, padChar, len, true);
		} else {
			return padStringBuffer(value, padChar, len, true);
		}
	}

	public static String rightPad(String value, char padChar, int len) {
		if (len - value.length() <= 2) { 
			return padString(value, padChar, len, false);
		} else {
			return padStringBuffer(value, padChar, len, false);
		}
	}

	private static String padString(String value, char padChar, int len,
			boolean isLeft) {
		String result = value;
		while (result.length() < len) {
			if (isLeft) {
				result = padChar + result;
			} else {
				result += padChar;
			}
		}
		return result;
	}

	private static String padStringBuffer(String value, char padChar, int len,
			boolean isLeft) {
		StringBuffer result = new StringBuffer(value);
		while (result.length() < len) {
			if (isLeft) {
				result.insert(0, padChar);
			} else {
				result.append(padChar);
			}
		}
		return result.toString();
	}


	public static String mapToString(Map map, String separator) {
		StringBuffer result = new StringBuffer();
		Iterator iter = map.keySet().iterator();
		while (iter.hasNext()) {
			if (result.length() > 0) {
				result.append(separator);
			}
			Object key = iter.next();
			Object value = map.get(key);
			result.append(key).append("=").append(value);
		}
		return result.toString();
	}


	public static String mapToString(Map map) {
		return mapToString(map, "\n");
	}

	public static String getStackTrace(Throwable e) {
		StringBuffer result = new StringBuffer();
		readStackTrace(result, e);
		return result.toString();
	}

	private static void readStackTrace(StringBuffer sb, Throwable e) {
		StackTraceElement[] stElements = e.getStackTrace();
		if (sb.length() == 0)
			sb = sb.append("printStackTrace:\n");
		else
			sb.append("\n nested exception is: \n");

		sb.append("\t").append(e.toString()).append("\n");
		for (int i = 0; i < stElements.length; i++) {
			sb.append("\t\tat ").append(stElements[i].toString()).append("\n");
		}
		if (e.getCause() != null) {
			readStackTrace(sb, e.getCause());
		}
	}

	public static Date adjustDateTimeZone(TimeZone tz1, TimeZone tz2, Date date1) {
		Date result = null;
		int raw1 = tz1.getRawOffset();
		int raw2 = tz2.getRawOffset();
		result = new java.util.Date(date1.getTime() + (raw2 - raw1));
		return result;
	}

	public static String bytesToHex(byte[] bytes) {
		return bytesToHex(bytes, false, false);
	}

	public static String bytesToHex(byte[] bytes, boolean newLine16,
			boolean spaceBetween) {
		StringBuffer result = new StringBuffer(bytes.length / 2);
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0x00FF).toUpperCase();
			if (hex.length() < 2) {
				result.append("0");
			}
			result.append(hex);
			if (spaceBetween) {
				result.append(" ");
			}
			if (newLine16 && ((i + 1) % 16 == 0)) {
				result.append(NEW_LINE);
			}
		}
		return result.toString();
	}

	public static byte[] hexToBytes(String hexString) {
		if (hexString.length() % 2 != 0) {
			throw new IllegalArgumentException("Length of hexString argument must be even");
		}

		int len = hexString.length() / 2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++) {
			String hex = hexString.substring(2 * i, 2 * i + 2);
			result[i] = (byte) Integer.parseInt(hex, 16);
		}
		return result;
	}


	public static String[] split(String original, String delimeter) {
		Vector nodes = new Vector();
		int index = original.indexOf(delimeter);

		while (index >= 0) {
			nodes.addElement(original.substring(0, index));
			original = original.substring(index + delimeter.length());
			index = original.indexOf(delimeter);
		}

		nodes.addElement(original);

		String[] result = new String[nodes.size()];
		if (nodes.size() > 0) {
			for (int loop = 0; loop < nodes.size(); loop++) {
				result[loop] = (String) nodes.elementAt(loop);
			}
		}

		return result;
	}

	public static double convertDMStoNumeric(String degree, String minute,
			String second, String direction) {
		final String SOUTH = "S";
		final String WEST = "W";
		double deg = Double.parseDouble(degree);
		double min = Double.parseDouble(minute);
		double sec = Double.parseDouble(second);

		double result = deg + min / 60 + sec / (60 * 60);

		if (SOUTH.equals(direction) || WEST.equals(direction))
			result = result * -1;

		return result;
	}

	public static double convertDMStoNumeric(String coordinate, String direction) {
		final String REGEX = "[^\\d\\w]+";
		Pattern p = Pattern.compile(REGEX);
		String[] items = p.split(coordinate);
		if (items.length < 2)
			throw new IllegalArgumentException("Not a valid coordinate string.");

		return convertDMStoNumeric(items[0], items[1], items[2], direction);
	}

	public static boolean pointIsInPolygon(double X[], double Y[], double x,
			double y) {
		int i, j;
		boolean c = false;
		for (i = 0, j = X.length - 1; i < X.length; j = i++) {
			if ((((Y[i] <= y) && (y < Y[j])) || ((Y[j] <= y) && (y < Y[i])))
					&& (x < (X[j] - X[i]) * (y - Y[i]) / (Y[j] - Y[i]) + X[i]))
				c = !c;
		}
		return c;
	}


	public static Map splitterUrl(String url) {
		Map params = new HashMap();
		String query = url;
		String[] param = query.split("&");
		for (int i = 0; i < param.length; i++) {
			String[] pair = param[i].split("=");
			String key = null;
			try {
				key = URLDecoder.decode(pair[0], "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String value = null;
			try {
				value = URLDecoder.decode(pair[1], "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			List values = (List) params.get(key);
			if (values == null) {
				values = new ArrayList();
				params.put(key, values);
			}
			values.add(value);
		}
		return params;
	}

	public static Map splitterUrlPrint(String url) {
		Map params = new HashMap();
		String query = url;
		String[] param = query.split(";");
		for (int i = 0; i < param.length; i++) {
			String key = null;
			String value = null;
			try {
				if (i == 0) {
					key = "taskId";
				} else if (i == 1) {
					key = "userId";
				}
				value = URLDecoder.decode(param[i], "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			params.put(key, value);
		}
		return params;
	}
	

	public static List combineList(List first, List second){
		List list = new ArrayList();
		list.addAll(first);
		list.addAll(second);
		return list;
	}
}
