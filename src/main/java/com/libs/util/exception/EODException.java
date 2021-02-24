package com.libs.util.exception;

public class EODException extends CoreException{
	private static final long serialVersionUID = 1L;

	public static final int UNKNOWN = 999;

	public static final int INVALID_CONFIG = 1;

	public static final int READ_CONFIG = 2;

	public static final int LOGGER = 3;

	public static final int EOD_ERROR = 4;

	public static final int DB_ERROR = 5;

	public static final int REPORT_ERROR = 6;

	public static final int INVALID_PATH = 7;

	public static final int WRITE_TEXT_FILE = 8;

	public static final int HOUSEKEEPING_ERROR = 9;

	public static final int BACKUP_ERROR = 10;

	   public EODException(Throwable cause, int errorCode) {
	      this(cause, errorCode, "");
	   }

	   public EODException(int errorCode) {
	      this(null, UNKNOWN);
	   }

	   public EODException(Throwable cause, int errorCode, String msg) {
	      super(msg, cause, errorCode, null);
	   }
}
