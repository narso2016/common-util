package com.libs.util.formatter;

import java.text.DecimalFormat;

import com.libs.util.bean.BeanConverter.FieldConverter;
import com.libs.util.log.ILogger;
import com.libs.util.log.LogManager;
@SuppressWarnings("rawtypes")
public class DecimalFormatter implements FieldConverter{
	private static DecimalFormat decimalFormat  = new DecimalFormat("###.######");
	   private static ThreadLocal converters = new ThreadLocal();
	   private ILogger logger = LogManager.getDefaultLogger();

	   @SuppressWarnings("unchecked")
	   public static DecimalFormatter getInstance() {
		   DecimalFormatter result = (DecimalFormatter) converters.get();
	      if (result == null) {
	         result = new DecimalFormatter();
	         converters.set(result);
	      }
	      return result;
	   }

	   public String convertToString(String fieldName, Object source) {
	      if (source == null)
	         return null;
	      String result = null;
	      if (source instanceof java.lang.Double) {
	         result = decimalFormat.format(source);
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
	         if (java.lang.Double.class.equals(targetClass)) {
	            result = Double.valueOf(source);
	         }
	         else
	            throw new IllegalArgumentException("Target class " + targetClass.getName() +
	                                               " is not supported by DecimalConverter");
	      }
	      catch (NumberFormatException nfe) {
	         this.logger.log(ILogger.LEVEL_ERROR,
	               "Error parsing " + source + " to class " +
	               targetClass.getName() + ": " + nfe.toString());
	      }
	      return result;
	   }
	
}
