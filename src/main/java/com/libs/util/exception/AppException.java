package com.libs.util.exception;

public class AppException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected int errorCode = 0;
	
	protected String errCode = "";
	
	protected int statusCode = 0;

	protected Object userObject = null;

	public AppException(int code) {
		this("", null, code, null);
	}
	

	public AppException(int code, String msg) {
		this(msg, null, code, null);
	}
	
	public AppException(int status, int code, String msg) {
		this(msg, null, status, code, null);
	}
	
	public AppException(int status, String code, String msg) {
		this(msg, null, status, code, null);
	}
	

	public AppException(Throwable cause) {
		this("", cause, 0, null);
	}

	public AppException(Throwable cause, int code) {
		this("", cause, code, null);
	}

	public AppException(String msg, Throwable cause, int code, Object userObject) {
		super(msg, cause);
		this.errorCode = code;
		this.userObject = userObject;
	}
	
	public AppException(String msg, Throwable cause, int status, int code, Object userObject) {
		super(msg, cause);
		this.statusCode = status;
		this.errorCode = code;
		this.userObject = userObject;
	}
	
	public AppException(String msg, Throwable cause, int status, String code, Object userObject) {
		super(msg, cause);
		this.statusCode = status;
		this.errCode = code;
		this.userObject = userObject;
	}

	public int getErrorCode() {
		return this.errorCode;
	}
	
	public String getErrCode() {
		return this.errCode;
	}
	
	public int getStatusCode() {
		return this.statusCode;
	}
	

	public Object getUserObject() {
		return this.userObject;
	}

	public String toString() {
		String userObjTxt = this.userObject == null ? "" : ". User object="
				+ this.userObject.toString();
		if (super.getCause() == null) {
			return super.toString() + ". Error code=" + this.errorCode
					+ userObjTxt;
		} else {
			return super.toString() + ". Error code=" + this.errorCode
					+ userObjTxt + "\n\tcause=" + super.getCause().toString();
		}
	}

}
