package com.libs.util.exception;

public class CoreException extends AppException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int ERROR_EXPIRED = 1000;
	public static final int ERROR_LIMIT = 1001;
	public static final int ERROR_MAC = 1002;

	
	public CoreException(int errorCode) {
		this("", null, errorCode, null);
	}

	public CoreException(int errorCode, Object userObject) {
		this("", null, errorCode, userObject);
	}

	public CoreException(Throwable cause, int errorCode) {
		this("", cause, errorCode, null);
	}

	public CoreException(String msg, Throwable cause, int errorCode,
			Object userObject) {
		super(msg, cause, errorCode, userObject);
	}
}
