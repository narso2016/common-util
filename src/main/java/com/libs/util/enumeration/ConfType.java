package com.libs.util.enumeration;

import com.libs.util.constant.ErrorCode;
import com.libs.util.exception.AppException;
import com.libs.util.strings.Strings;

public enum ConfType {
	N_CONF_TYPE("n"), D_CONF_TYPE("d"), C_CONF_TYPE("c"), W_CONF_TYPE("w"), T_CONF_TYPE("t");
	
	private final String type;
	
	private ConfType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type;
	}
	
	public static boolean checkType(String type) throws AppException{
		if(Strings.isNullOrEmpty(type)) throw new AppException(ErrorCode.ERROR_PRECONDITION_FAILED, "Error, Config type (conftpye) is null or empty");
		type = type.toLowerCase();
		
		if(N_CONF_TYPE.toString().equals(type) 
				|| D_CONF_TYPE.toString().equals(type)
				|| C_CONF_TYPE.toString().equals(type)
				|| W_CONF_TYPE.toString().equals(type)
				|| T_CONF_TYPE.toString().equals(type)) return true;

		return false;
	}
}
