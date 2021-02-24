package com.libs.util.formatter;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.libs.util.strings.Strings;

public class Formatter {

	public static final String LANG_ID = "ID";
	public static final String LANG_EN = "EN";
	
	public static final String DFORMAT_YMD = "yyyyMMdd";
	public static final String DFORMAT_YMD_SLASHER = "yyyy/MM/dd";
	public static final String DFORMAT_YMD_DASH = "yyyy-MM-dd";
	public static final String DFORMAT_DMY = "ddMMyyyy";
	public static final String DFORMAT_DMY_SLASHER = "dd/MM/yyyy";
	public static final String DFORMAT_DMY_LONG = "dd MMM yyyy";
	public static final String DFORMAT_DMYHMS = "ddMMyyyy_hhmmss";
	public static final String DFORMAT_YMDHHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static final String FULLDATE = "EEE MMM d HH:mm:ss zzz yyyy";
	public static final String DFORMAT_SHORT = "MM/dd/yyyy";
	public static final String TFORMAT_SHORT = "HH:mm";

	public static final NumberFormatter NFORMAT_ID_0 = new NumberFormatter('.', ',', 0);
	public static final NumberFormatter NFORMAT_ID_2 = new NumberFormatter('.', ',', 2);
	public static final NumberFormatter NFORMAT_US_0 = new NumberFormatter(',', '.', 0);
	public static final NumberFormatter NFORMAT_US_2 = new NumberFormatter(',', '.', 2);
	public static final NumberFormatter NFORMAT_PLAIN = new NumberFormatter(',', '.', 0, false);
	public static final NumberFormatter NFORMAT_PLAIN_AUDIT = new NumberFormatter(',', '.', 4, false);

	public static final String[] ALPHABET = { 
		"A", "B", "C", "D", "E", "F",
		"G", "H", "I", "J", "K", "L", 
		"M", "N", "O", "P", "Q", "R", 
		"S", "T", "U", "V", "W", "X", 
		"Y", "Z" };

	@SuppressWarnings("rawtypes")
	private static ThreadLocal numberFormatPool = new ThreadLocal();
	
	@SuppressWarnings("rawtypes")
	private static ThreadLocal dateFormatPool = new ThreadLocal();

	private Formatter() {
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static NumberFormat getNumberFormat(NumberFormatter numberFormatter) {
		NumberFormat result = null;
		
		HashMap numberFormat = (HashMap) numberFormatPool.get();
		if (numberFormat == null) {
			numberFormat = new HashMap();
			numberFormatPool.set(numberFormat);
		} else {
			result = (NumberFormat) numberFormat.get(numberFormatter);
		}
		if (result == null) {

			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setGroupingSeparator(numberFormatter.thousandSeparator);
			symbols.setDecimalSeparator(numberFormatter.decimalSeparator);

			DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###,###,###.##", symbols);
			decimalFormat.setMinimumFractionDigits(numberFormatter.decimal);
			decimalFormat.setMaximumFractionDigits(numberFormatter.decimal);
			decimalFormat.setGroupingSize(numberFormatter.showThousandSeparator ? 3 : 0);
			decimalFormat.setDecimalSeparatorAlwaysShown(numberFormatter.decimal > 0);
			numberFormat.put(numberFormatter, decimalFormat);
			result = decimalFormat;
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static DateFormat getDateFormat(String format) {
		DateFormat result = null;
		HashMap dateFormat = (HashMap) dateFormatPool.get();
		if (dateFormat == null) {
			dateFormat = new HashMap();
			dateFormatPool.set(dateFormat);
		} else {
			result = (DateFormat) dateFormat.get(format);
		}
		if (result == null) {
			result = new SimpleDateFormat(format);
			dateFormat.put(format, result);
		}
		return result;
	}
	
	public static String stem(String value){
		  String ret = "";
		  if(!Strings.isNullOrEmpty(value)){
		        String [] values =value.split(" ");
		        for (int i = 0; i < values.length; i++) {
		            String string = values[i];
		            if(!"".equalsIgnoreCase(string.trim())){
		                if("".equals(ret)){
	                            ret = string;
	                        }else{
	                            ret = ret+" "+string;
	                        }
		            }
		        }
		   }else{
		      ret = value;
		   }
		return ret;
	}

	public static String formatDate(Date date, String format) {
	   return getDateFormat(format).format(date);
	}

	public static String formatDate(Date dt, DateFormat formatter) {
	  return formatter.format(dt);
	}

	public static java.util.Date parseDate(String dateStr, String format) throws ParseException {
	   return getDateFormat(format).parse(dateStr);
	}

	public static Date parseDate(String dateStr, DateFormat formatter) throws ParseException  {
	  return formatter.parse(dateStr);
	}

	public static String castToAlphabet(int index) {
	    String ret = "";
	    int modular = ALPHABET.length;
	    if (index < modular) {
	        ret = ALPHABET[index];
	    }else{
	        int first = index/modular;
	        if(first>modular){
	            first = first%modular;
	        }
	        int last = index%modular;
	        if(first!=0)
	            first=first-1;
	        ret = ALPHABET[first]+""+ALPHABET[last];
	    }
	    return ret;
	}
	
	public static String toCamelCase(String value){
	    String retValue = null;
	    if(!Strings.isNullOrEmpty(value)){
	       retValue = "";
	       String[] data = value.split(" ");
	       for (int i = 0; i < data.length; i++) {
	    	   String string = data[i];
	           String temp = "";
	           if(string.length()>0){
	              String prefix = String.valueOf(string.charAt(0));
	              temp = temp+prefix.toUpperCase();
	              temp = temp+string.substring(1).toLowerCase();
	              temp = Formatter.clearingStrip(temp);
	           }
	           retValue = retValue+temp+" ";
	       }
	       if(retValue.endsWith(" ")){
	    	   retValue = retValue.substring(0,retValue.length()-1);
	       }
	    }
	    return retValue;
	}
	
	public static String clearingStrip(String string) {
        if (string.contains("-")) {
            String[] strings = string.split("-");
            if (strings.length > 0) {
                for (int j = 0; j < strings.length; j++) {
                    String dataTemp = strings[j];
                    String prefix = String.valueOf(dataTemp.charAt(0)).toUpperCase();
                    if (j == 0) {
                        string = prefix + dataTemp.substring(1).toLowerCase() + "-";
                    } else if (j == strings.length - 1) {
                        string = string + prefix + dataTemp.substring(1).toLowerCase();
                    } else {
                        string = string + prefix + dataTemp.substring(1).toLowerCase() + "-";
                    }
                }
            }
        }
        return string;
    }
	
	public String getNumberText(Object o, String language){
		String result = "";
		String separator = "";
		NumberTexter texter = null;
		if(Formatter.LANG_EN.equalsIgnoreCase(language)){
			texter = NumberTexter.LANG_EN;
			separator = "point";
		}else if(Formatter.LANG_ID.equalsIgnoreCase(language)){
			texter = NumberTexter.LANG_ID;
			separator = "koma";
		}
		if(null!=texter){
			if(o.getClass().equals(Double.class)){
				String [] results = texter.numberToString((Double)o);
				result = results[0]+" "+separator+" "+results[1];			
			}else{
				result = texter.numberToString((Long)o);
			}
		}
		return result;
	}
	
	
	public static void main (String[] args) {
		try {
			Date d = Formatter.parseDate("10:30", Formatter.TFORMAT_SHORT);
			System.out.println(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}