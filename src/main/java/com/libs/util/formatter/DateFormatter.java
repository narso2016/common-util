package com.libs.util.formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.libs.util.bean.BeanConverter.FieldConverter;
import com.libs.util.log.ILogger;
import com.libs.util.log.LogManager;

@SuppressWarnings("rawtypes")
public class DateFormatter implements FieldConverter {
	   private static String VIEW_DATE_FORMAT = "dd-MMM-yyyy HH:mm:ss";
	  private static ThreadLocal converters = new ThreadLocal();
	   private ILogger logger = LogManager.getDefaultLogger();

	   private DateFormatter() {}

	   @SuppressWarnings("unchecked")
	   public static DateFormatter getInstance() {
		   DateFormatter result = (DateFormatter) converters.get();
	      if (result == null) {
	         result = new DateFormatter();
	         converters.set(result);
	      }
	      return result;
	   }

	   public String convertToString(String fieldName, Object source) {
	      if (source == null)
	         return null;
	      String result = null;
	      if (source instanceof java.util.Date) {
	         result = Formatter.getDateFormat(VIEW_DATE_FORMAT).format(source);
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
	            result = Formatter.getDateFormat(VIEW_DATE_FORMAT).parse(source);
	         }
	         else
	            throw new IllegalArgumentException("Target class " + targetClass.getName() +
	                                               " is not supported by TreemasDateFormatter");
	      }
	      catch (ParseException pe) {
	         this.logger.log(ILogger.LEVEL_ERROR,
	               "Error parsing " + source + " to class " +
	               targetClass.getName() + ": " + pe.toString());
	      }
	      return result;
	   }
	   
	   
	   
	   public Date addMonth(Date date, int amount) {
		   return DateUtils.addMonths(date, amount);
	   }
	   
	   public static Date addDays(Date date, int amount) {
	        return DateUtils.addDays(date, amount);
	    }
	   
	    public static boolean isSameDay(Date date1, Date date2) {
	        if (date1 == null || date2 == null) {
	            throw new IllegalArgumentException("The date must not be null");
	        }
	        Calendar cal1 = Calendar.getInstance();
	        cal1.setTime(date1);
	        Calendar cal2 = Calendar.getInstance();
	        cal2.setTime(date2);
	        return isSameDay(cal1, cal2);
	    }
	    
	   
	    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
	        if (cal1 == null || cal2 == null) {
	            throw new IllegalArgumentException("The date must not be null");
	        }
	        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
	                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
	                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
	    }
	   
	    
		public static int getDay(Date dateInput) {
			 Calendar cal = Calendar.getInstance();
			 cal.setTime(dateInput);
			 int day = cal.get(Calendar.DAY_OF_MONTH);
			 return day;
		}
		
		public static int getMonth(Date dateInput) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateInput);
			int month = cal.get(Calendar.MONTH);
			return month;
		}
		
		public static int getYear(Date dateInput) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateInput);
			int month = cal.get(Calendar.YEAR);
			return month;
		}
		
		public static Date customDateInputDay(Date dateInput, int day) {
			Calendar cal = Calendar.getInstance();
			if(day == 0) return null;
			 cal.setTime(dateInput);
			 LocalDate localDate = dateInput.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			 int month = localDate.getMonthValue();
			 int year = cal.get(Calendar.YEAR);
			 cal.set(year, month-1, day, 0, 0); 
			 return cal.getTime();
		}
	   
	   
	}
