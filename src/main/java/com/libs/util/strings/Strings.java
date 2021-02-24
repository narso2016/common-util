package com.libs.util.strings;

public class Strings {
	public static boolean isNullOrEmpty(String key){
        if(null==key){
            return true;
        }else if("".equals(key.trim())){
            return true;
        }
        return false;
    }
}
