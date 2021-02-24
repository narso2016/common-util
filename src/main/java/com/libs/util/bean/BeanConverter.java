package com.libs.util.bean;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.libs.util.formatter.Formatter;
import com.libs.util.formatter.NumberFormatter;
import com.libs.util.log.ILogger;
import com.libs.util.log.LogManager;
import com.libs.util.strings.Strings;

@SuppressWarnings({"rawtypes","unchecked"})
public class BeanConverter {
	
	private static ILogger logger = LogManager.getDefaultLogger();

	private static NumberFormatter default_tostring_number_format = Formatter.NFORMAT_US_2;
	private static String default_tostring_date_format = Formatter.DFORMAT_DMY;
	private static NumberFormatter default_fromstring_number_format = Formatter.NFORMAT_PLAIN;
	private static String default_fromstring_date_format = Formatter.DFORMAT_DMY;
	
	private static final Class[] PARAM_TYPE_STRING = new Class[] { java.lang.String.class };
	private static final int MAX_CACHE_SIZE = 100;

	@SuppressWarnings("serial")
	private static Map cacheMethods = Collections.synchronizedMap(new LinkedHashMap() {

		protected boolean removeEldestEntry(Map.Entry eldest) {
			return size() > MAX_CACHE_SIZE;
		}
	});

	private BeanConverter() {
	}

	public static interface FieldConverter {

		public String convertToString(String fieldName, Object source);

		public Object convertFromString(String fieldName, String source, Class targetClass);
	}

	public static void setDefaultToStringDateFormat(String format) {
		default_tostring_date_format = format;
	}

	public static String getDefaultToStringDateFormat() {
		return default_tostring_date_format;
	}

	public static void setDefaultFromStringDateFormat(String format) {
		default_fromstring_date_format = format;
	}

	public static String getDefaultFromStringDateFormat() {
		return default_fromstring_date_format;
	}

	public static NumberFormatter getDefaultToStringNumberFormat() {
		return default_tostring_number_format;
	}

	public static void setDefaultToStringNumberFormat(NumberFormatter formatType) {
		default_tostring_number_format = formatType;
	}

	public static NumberFormatter getDefaultFromStringNumberFormat() {
		return default_fromstring_number_format;
	}

	public static void setDefaultFromStringNumberFormat(NumberFormatter formatType) {
		default_fromstring_number_format = formatType;
	}

	public static void convertBeanToString(Object sourceBean, Object targetBean, Map fieldConverters) {

		if (sourceBean == null)
			throw new IllegalArgumentException("Cannot convert bean: source bean is null");
		if (targetBean == null)
			throw new IllegalArgumentException("Cannot convert bean: target bean is null");
		try {
			Class fromClass = sourceBean.getClass();
			Class toClass = targetBean.getClass();
			Method[] fromMethods = (Method[]) cacheMethods.get(fromClass.getName());
			if (fromMethods == null) {
				fromMethods = fromClass.getMethods();
				cacheMethods.put(fromClass.getName(), fromMethods);
			}

			for (int i = 0; i < fromMethods.length; i++) {
				Method fromMethod = fromMethods[i];
				if (!fromMethod.getName().startsWith("get") || fromMethod.getParameterTypes().length > 0)
					continue;
				String tmpName = fromMethod.getName().substring(3);

				Method toMethod = null;
				try {
					toMethod = toClass.getMethod("set" + tmpName, PARAM_TYPE_STRING);
				} catch (NoSuchMethodException e) {
				}

				if (toMethod == null)
					continue;

				Object source = fromMethod.invoke(sourceBean, null);
				Object result = null;

				FieldConverter converter = null;
				if (fieldConverters != null) {
					String fieldName = Character.toLowerCase(tmpName.charAt(0)) + tmpName.substring(1);
					converter = (FieldConverter) fieldConverters.get(fieldName);
					if (converter != null)
						result = converter.convertToString(fieldName, source);
					else
						result = convertToString(source);
				} else
					result = convertToString(source);

				toMethod.invoke(targetBean, new Object[] { result });
			}
		} catch (Exception e) {
			logger.log(ILogger.LEVEL_ERROR, "Exception when converting bean " + sourceBean.getClass().getName() + " to "
					+ targetBean.getClass().getName());
			logger.printStackTrace(e);
		}
	}

	public static void convertBeanToString(Object sourceBean, Object targetBean) {
		convertBeanToString(sourceBean, targetBean, null);
	}

	public static String convertToString(Object source) {
		if (source == null)
			return null;

		String result = null;
		if (source instanceof java.lang.Double) {
			NumberFormat formatter = Formatter.getNumberFormat(getDefaultToStringNumberFormat());
			result = formatter.format(((Double) source).doubleValue());
		} else if (source instanceof java.lang.Float) {
			NumberFormat formatter = Formatter.getNumberFormat(getDefaultToStringNumberFormat());
			result = formatter.format(((Float) source).doubleValue());
		} else if (source instanceof java.math.BigDecimal) {
			NumberFormat formatter = Formatter.getNumberFormat(getDefaultToStringNumberFormat());
			result = formatter.format(((java.math.BigDecimal) source).doubleValue());
		} else if (source instanceof java.util.Date) {
			result = Formatter.formatDate((java.util.Date) source, getDefaultToStringDateFormat());
		} else
			result = source.toString();
		return result;
	}

	public static void convertBeanFromString(Object sourceBean, Object targetBean, Map fieldConverters) {
		try {
			Class fromClass = sourceBean.getClass();
			Class toClass = targetBean.getClass();
			Method[] toMethods = (Method[]) cacheMethods.get(toClass.getName());
			if (toMethods == null) {
				toMethods = toClass.getMethods();
				cacheMethods.put(toClass.getName(), toMethods);
			}

			for (int i = 0; i < toMethods.length; i++) {
				Method toMethod = toMethods[i];
				if (!toMethod.getName().startsWith("set") || toMethod.getParameterTypes().length != 1
						|| !toMethod.getReturnType().equals(Void.TYPE))
					continue;
				String tmpName = toMethod.getName().substring(3);

				Method fromMethod = null;
				try {
					fromMethod = fromClass.getMethod("get" + tmpName, null);
				} catch (NoSuchMethodException e) {
				}

				if (fromMethod == null || !fromMethod.getReturnType().equals(java.lang.String.class))
					continue;

				String source = (String) fromMethod.invoke(sourceBean, null);
				Object result = null;

				Class toMethodParamClass = toMethod.getParameterTypes()[0];

				FieldConverter converter = null;

				if (fieldConverters != null) {
					String fieldName = Character.toLowerCase(tmpName.charAt(0)) + tmpName.substring(1);
					converter = (FieldConverter) fieldConverters.get(fieldName);
					if (converter != null)
						result = converter.convertFromString(fieldName, source, toMethodParamClass);
					else
						result = convertFromString(source, toMethodParamClass);
				} else
					result = convertFromString(source, toMethodParamClass);

				if (result == null && toMethodParamClass.isPrimitive()) {
					result = new Integer(0);
				}
				toMethod.invoke(targetBean, new Object[] { result });
			}
		} catch (Exception e) {
			logger.printStackTrace(e);
		}
	}

	public static void convertBeanFromString(Object sourceBean, Object targetBean) {
		convertBeanFromString(sourceBean, targetBean, null);
	}

	public static Object convertFromString(String source, Class targetClass) {
		if (source == null)
			return null;

		Object result = null;
		try {
			if (targetClass.equals(java.lang.String.class)) {
				result = source;
			} else if (targetClass.equals(Integer.TYPE) || targetClass.equals(java.lang.Integer.class)) {
				result = Integer.valueOf(source);
			} else if (targetClass.equals(Double.TYPE) || targetClass.equals(java.lang.Double.class)
					|| targetClass.equals(Float.TYPE) || targetClass.equals(java.lang.Float.class)) {
				NumberFormat formatter = Formatter.getNumberFormat(getDefaultFromStringNumberFormat());
				result = formatter.parse(source);
			} else if (targetClass.equals(Long.TYPE) || targetClass.equals(java.lang.Long.class)) {
				result = Long.valueOf(source);
			} else if (targetClass.equals(java.util.Date.class)) {
				result = Formatter.parseDate(source, getDefaultFromStringDateFormat());
			} else if (targetClass.equals(java.math.BigDecimal.class)) {
				result = new java.math.BigDecimal(source);
			} else
				logger.log(ILogger.LEVEL_WARNING, "Unsupported class: " + targetClass.getName());
		} catch (ParseException pe) {
			logger.log(ILogger.LEVEL_ERROR,
					"Error parsing " + source + " to " + targetClass.getName() + ": " + pe.toString());
		}
		return result;
	}

	public static List convertBeansToString(List list, Class targetClass, Map fieldConverters) {
		List result = new ArrayList(list.size());
		Iterator iter = list.iterator();
		try {
			while (iter.hasNext()) {
				Object target = targetClass.newInstance();
				convertBeanToString(iter.next(), target, fieldConverters);
				result.add(target);
			}
		} catch (Exception e) {
			logger.printStackTrace(e);
		}
		return result;
	}

	public static List convertBeansFromString(List list, Class targetClass) {
		return convertBeansFromString(list, targetClass, null);
	}

	public static List convertBeansFromString(List list, Class targetClass, Map fieldConverters) {
		List result = new ArrayList(list.size());
		Iterator iter = list.iterator();
		try {
			while (iter.hasNext()) {
				Object target = targetClass.newInstance();
				convertBeanFromString(iter.next(), target, fieldConverters);
				result.add(target);
			}
		} catch (Exception e) {
			logger.printStackTrace(e);
		}
		return result;
	}

	public static List convertBeansToString(List list, Class targetClass) {
		return convertBeansToString(list, targetClass, null);

	}

	public static Map convertBeanToStringMap(Object sourceBean, Map fieldConverters) {
		if (sourceBean == null)
			throw new IllegalArgumentException("Cannot convert bean: source bean is null");
		Map result = new HashMap();
		try {
			Class fromClass = sourceBean.getClass();
			Method[] fromMethods = (Method[]) cacheMethods.get(fromClass.getName());
			if (fromMethods == null) {
				fromMethods = fromClass.getMethods();
				cacheMethods.put(fromClass.getName(), fromMethods);
			}
			for (int i = 0; i < fromMethods.length; i++) {
				Method fromMethod = fromMethods[i];
				if (!fromMethod.getName().startsWith("get") || fromMethod.getParameterTypes().length > 0)
					continue;
				String tmpName = fromMethod.getName().substring(3);

				Object source = fromMethod.invoke(sourceBean, null);
				String resultText = null;

				FieldConverter converter = null;
				String fieldName = Character.toLowerCase(tmpName.charAt(0)) + tmpName.substring(1);
				if (fieldConverters != null) {
					converter = (FieldConverter) fieldConverters.get(fieldName);
					if (converter != null)
						resultText = converter.convertToString(fieldName, source);
					else
						resultText = convertToString(source);
				} else
					resultText = convertToString(source);

				result.put(fieldName, resultText);
			}
		} catch (Exception e) {
			logger.log(ILogger.LEVEL_ERROR,
					"Exception when converting bean " + sourceBean.getClass().getName() + " to Map");
			logger.printStackTrace(e);
		}
		return result;
	}

	public static void convertBeanFromStringMap(Map sourceMap, Object targetBean, Map fieldConverters) {
		try {
			Class toClass = targetBean.getClass();
			Method[] toMethods = (Method[]) cacheMethods.get(toClass.getName());
			if (toMethods == null) {
				toMethods = toClass.getMethods();
				cacheMethods.put(toClass.getName(), toMethods);
			}

			for (int i = 0; i < toMethods.length; i++) {
				Method toMethod = toMethods[i];
				if (!toMethod.getName().startsWith("set") || toMethod.getParameterTypes().length != 1
						|| !toMethod.getReturnType().equals(Void.TYPE))
					continue;
				String tmpName = toMethod.getName().substring(3);
				String fieldName = Character.toLowerCase(tmpName.charAt(0)) + tmpName.substring(1);

				Object objSource = sourceMap.get(fieldName);
				String source = objSource == null ? null : objSource.toString();
				Object result = null;

				Class toMethodParamClass = toMethod.getParameterTypes()[0];

				FieldConverter converter = null;
				if (fieldConverters != null) {
					converter = (FieldConverter) fieldConverters.get(fieldName);
					if (converter != null)
						result = converter.convertFromString(fieldName, source, toMethodParamClass);
					else
						result = convertFromString(source, toMethodParamClass);
				} else
					result = convertFromString(source, toMethodParamClass);

				if (result == null && toMethodParamClass.isPrimitive()) {
					result = new Integer(0);
				}
				toMethod.invoke(targetBean, new Object[] { result });
			}
		} catch (Exception e) {
			logger.printStackTrace(e);
		}
	}

	public static List convertBeansToStringMap(List list, Map fieldConverters) {
		List result = new ArrayList(list.size());
		Iterator iter = list.iterator();
		try {
			while (iter.hasNext()) {
				Map map = convertBeanToStringMap(iter.next(), fieldConverters);
				result.add(map);
			}
		} catch (Exception e) {
			logger.printStackTrace(e);
		}
		return result;
	}

	public static List convertBeansFromStringMap(List list, Class targetClass, Map fieldConverters) {
		List result = new ArrayList(list.size());
		Iterator iter = list.iterator();
		try {
			while (iter.hasNext()) {
				Object target = targetClass.newInstance();
				convertBeanFromStringMap((Map) iter.next(), target, fieldConverters);
				result.add(target);
			}
		} catch (Exception e) {
			logger.printStackTrace(e);
		}
		return result;
	}
	
	public static Map<String, Object> convertBeanToMap(Object sourceBean) {
		Map<String, Object> retResult = new HashMap<String, Object>();
		if (sourceBean == null)
			throw new IllegalArgumentException("Cannot convert bean: source bean is null");
		try {
			Class fromClass = sourceBean.getClass();
			Method[] fromMethods = (Method[]) cacheMethods.get(fromClass.getName());
			if (fromMethods == null) {
				fromMethods = fromClass.getMethods();
				cacheMethods.put(fromClass.getName(), fromMethods);
			}

			for (int i = 0; i < fromMethods.length; i++) {
				Method fromMethod = fromMethods[i];
				if (!fromMethod.getName().startsWith("get") || !fromMethod.getName().startsWith("is") || fromMethod.getParameterTypes().length > 0)
					continue;
				
				String tmpName = "";
				
				if (fromMethod.getName().startsWith("get")) tmpName = fromMethod.getName().substring(3);
				else if (fromMethod.getName().startsWith("is")) tmpName = fromMethod.getName().substring(2);
				else continue;
				
				if(fromMethod.getReturnType().isInstance(String.class)) {
					String value = (String)fromMethod.invoke(sourceBean, null);
					if(!Strings.isNullOrEmpty(value)) retResult.put(tmpName, value); 
				}else if(fromMethod.getReturnType().isInstance(Integer.class)
						|| fromMethod.getReturnType().isInstance(Long.class)
						|| fromMethod.getReturnType().isInstance(Double.class)
						|| fromMethod.getReturnType().isInstance(BigDecimal.class)
						|| fromMethod.getReturnType().isInstance(Float.class)
						|| fromMethod.getReturnType().isInstance(Boolean.class)) {
					Object value = fromMethod.invoke(sourceBean, null);
					if(null!=value) retResult.put(tmpName, value); 
				}else if(fromMethod.getReturnType().isPrimitive()) {
					retResult.put(tmpName, fromMethod.invoke(sourceBean, null)); 
				}else {
					Object value = fromMethod.invoke(sourceBean, null);
					if(null!=value) retResult.put(tmpName, value);
				}
				
			}
			return retResult;
		} catch (Exception e) {
			logger.log(ILogger.LEVEL_ERROR, "Exception when converting bean " + sourceBean.getClass().getName());
			logger.printStackTrace(e);
		}
		return retResult;
	}
}
