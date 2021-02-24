package com.libs.util.log;

public interface ILogger {
	public static final int LEVEL_DEBUG_MEDIUM = 10;
	public static final int LEVEL_DEBUG_LOW = 20;
	public static final int LEVEL_DEBUG_HIGH = 30;
	public static final int LEVEL_INFO = 40;
	public static final int LEVEL_WARNING = 50;
	public static final int LEVEL_ERROR = 60;

	public abstract void log(int lvl, String msg);

	public abstract void printStackTrace(Throwable e);

	public abstract void setLogLevel(int lvl);

	public abstract int getLogLevel();

	public abstract String getLogLevelString();

	public abstract String getLogLevelString(int lvl);

	public abstract boolean isLoggable(int level);

}
