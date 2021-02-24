package com.libs.util.bean;

import java.text.ParseException;

import com.libs.util.bean.BeanConverter.FieldConverter;
import com.libs.util.formatter.Formatter;
import com.libs.util.log.ILogger;
import com.libs.util.log.LogManager;

@SuppressWarnings("rawtypes")
public class DateTimeConverter implements FieldConverter {

	private static ILogger logger = LogManager.getDefaultLogger();
	private static String VIEW_DATETIME_FORMAT = "dd-MMM-yyyy HH:mm:ss";

	private static ThreadLocal converters = new ThreadLocal();

	private DateTimeConverter() {
	}

	@SuppressWarnings("unchecked")
	public static DateTimeConverter getInstance() {
		DateTimeConverter result = (DateTimeConverter) converters.get();
		if (result == null) {
			result = new DateTimeConverter();
			converters.set(result);
		}
		return result;
	}

	public String convertToString(String fieldName, Object source) {
	      if (source == null)
	         return null;
	      String result = null;
	      if (source instanceof java.util.Date) {
	         result = Formatter.getDateFormat(VIEW_DATETIME_FORMAT).format(source);
	      }
	      else
	         result = source.toString();
	      return result;
	   }


	   public Object convertFromString(String fieldName, String source, Class targetClass) {
	      if (source == null)
	         return null;

	      Object result = null;
	      try {
	         if (java.util.Date.class.equals(targetClass)) {
	            result = Formatter.getDateFormat(VIEW_DATETIME_FORMAT).parse(source);
	         }
	         else
	            throw new IllegalArgumentException("Target class " + targetClass.getName() +
	                                               " is not supported by DMYTimeConverter");
	      }
	      catch (ParseException pe) {
	         logger.log(ILogger.LEVEL_ERROR,
	               "Error parsing " + source + " to class " +
	               targetClass.getName() + ": " + pe.toString());
	      }
	      return result;
	   }
}
