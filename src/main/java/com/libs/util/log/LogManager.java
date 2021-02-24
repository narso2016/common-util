package com.libs.util.log;

import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LogManager {
	   public static final String DEFAULT_LOGGER_NAME = "DEF_LOG";

	   public static final int MAX_LOG_FILES = 50;

	   private static String defaultLoggerName = DEFAULT_LOGGER_NAME;

	   private static Hashtable<String, TheLogger> loggers = new Hashtable<String, TheLogger>();

	   public static ILogger createFileLogger(
			   String logName, String filename,
	           int limitSizeInKb, int maxLogFiles) throws Exception {

	      return createFileLogger(logName, filename, limitSizeInKb, maxLogFiles, false);
	   }

	   public static ILogger createFileLogger(String logName, String filename,
	                                          int limitSizeInKb, int maxLogFiles, boolean toConsole) throws
	       Exception {
	      if (loggers.get(logName) != null)
	         throw new IllegalArgumentException("File logger " + logName + " is already created");
	      if (maxLogFiles <= 0)
	         maxLogFiles = MAX_LOG_FILES;
	      if (limitSizeInKb <= 0)
	         throw new IllegalArgumentException("Illegal limit parameter: " + maxLogFiles);

	      Logger realLogger = Logger.getLogger(logName);
	      realLogger.setUseParentHandlers(false);
	      realLogger.setLevel(Level.ALL);

	      Handler[] handlers = realLogger.getHandlers();
	      boolean fileHandlerFound = false;
	      boolean consoleHandlerFound = false;
	      for (int i=0; i < handlers.length; i++) {
	         fileHandlerFound = fileHandlerFound || ((handlers[i] instanceof FileHandler));
	         consoleHandlerFound = consoleHandlerFound || ((handlers[i] instanceof ConsoleHandler));
	      }
	      if (!fileHandlerFound) {
	         Handler logHandler = new FileHandler(filename + ".%g", limitSizeInKb * 1024,
	                                              maxLogFiles, true);
	         logHandler.setLevel(Level.ALL);
	         LogFormatter logFormatter = new LogFormatter();
	         logHandler.setFormatter(logFormatter);
	         realLogger.addHandler(logHandler);
	      }
	      if (toConsole && !consoleHandlerFound) {
	         Handler logHandler = new ConsoleHandler();
	         logHandler.setLevel(Level.ALL);
	         logHandler.setFormatter(new LogFormatter());
	         realLogger.addHandler(logHandler);
	      }

	      TheLogger logger = new TheLogger(realLogger);
	      loggers.put(logName, logger);
	      return logger;
	   }

	   public static ILogger createConsoleLogger(String logName) {
	      if (loggers.get(logName) != null)
	         throw new IllegalArgumentException("Console logger " + logName +" is already created");
	      Logger realLogger = Logger.getLogger(logName);
	      realLogger.setUseParentHandlers(false);
	      realLogger.setLevel(Level.ALL);

	      Handler[] handlers = realLogger.getHandlers();
	      boolean consoleFound = false;
	      for (int i=0; i < handlers.length && !consoleFound; i++) {
	         consoleFound = (handlers[i] instanceof ConsoleHandler);
	      }
	      if (!consoleFound) {
	         Handler logHandler = new ConsoleHandler();
	         logHandler.setLevel(Level.ALL);
	         logHandler.setFormatter(new LogFormatter());
	         realLogger.addHandler(logHandler);
	      }

	      TheLogger logger = new TheLogger(realLogger);
	      loggers.put(logName, logger);
	      return logger;
	   }

	   public static void removeLogger(String logName) {
	      TheLogger logger = (TheLogger) loggers.get(logName);
	      if (logger != null) {
	         Handler[] hndls = logger.realLogger.getHandlers();
	         loggers.remove(logName);
	         logger.log(logger.getLogLevel(),
	                    "Logger " + logName +
	             " is removed from LoggerManager. Handler(s) will be closed and removed");
	         for (int i = 0; i < hndls.length; i++) {
	            hndls[i].flush();
	            hndls[i].close();
	            logger.realLogger.removeHandler(hndls[i]);
	         }
	      }
	   }

	   public static ILogger getLogger(String logName) {
	      ILogger result = (ILogger) loggers.get(logName);
	      return result;
	   }

	   public static void setDefaultLoggerName(String logName) {
	      if (loggers.get(logName) == null)
	         throw new IllegalArgumentException("Cannot find logger " + logName);
	      defaultLoggerName = logName;
	   }

	   public static String getDefaultLoggerName() {
	      return defaultLoggerName;
	   }

	   public static ILogger getDefaultLogger() {
	      return getLogger(defaultLoggerName);
	   }

	    @SuppressWarnings({"unchecked","deprecation"})
		private static class TheLogger
	        implements ILogger, java.util.logging.Filter {
		   private static final Integer INTEGER_DEBUG2 = new Integer(LEVEL_DEBUG_MEDIUM);
	       private static final Integer INTEGER_DEBUG1 = new Integer(LEVEL_DEBUG_LOW);
	       private static final Integer INTEGER_DEBUG0 = new Integer(LEVEL_DEBUG_HIGH);
	       private static final Integer INTEGER_INFO = new Integer(LEVEL_INFO);
	       private static final Integer INTEGER_WARNING = new Integer(LEVEL_WARNING);
	       private static final Integer INTEGER_ERROR = new Integer(LEVEL_ERROR);

	       @SuppressWarnings("rawtypes")
		private static Hashtable levelMap = new Hashtable(7, 1f);
	       static {
	          levelMap.put(INTEGER_DEBUG2, Level.FINER);
	          levelMap.put(INTEGER_DEBUG1, Level.FINE);
	          levelMap.put(INTEGER_DEBUG0, Level.CONFIG);
	          levelMap.put(INTEGER_INFO, Level.INFO);
	          levelMap.put(INTEGER_WARNING, Level.WARNING);
	          levelMap.put(INTEGER_ERROR, Level.SEVERE);
	       }

	       private Logger realLogger;
	       private Integer logLevel;

	       private TheLogger(Logger logger) {
	          this.realLogger = logger;
	          this.realLogger.setFilter(this);
	          this.logLevel = INTEGER_INFO;
	          this.realLogger.setLevel( (Level) levelMap.get(this.logLevel));
	          this.realLogger.log(Level.SEVERE,
	                              "Log started. Level=" + this.getLogLevelString());
	       }

	       public void log(int lvl, String msg) {
	          Level realLvl = null;
	          if (LEVEL_DEBUG_MEDIUM == lvl)
	             realLvl = Level.FINER;
	          else if (LEVEL_DEBUG_LOW == lvl)
	             realLvl = Level.FINE;
	          else if (LEVEL_DEBUG_HIGH == lvl)
	             realLvl = Level.CONFIG;
	          else if (LEVEL_INFO == lvl)
	             realLvl = Level.INFO;
	          else if (LEVEL_WARNING == lvl)
	             realLvl = Level.WARNING;
	          else if (LEVEL_ERROR == lvl)
	             realLvl = Level.SEVERE;
	          if (realLvl == null)
	             throw new IllegalArgumentException("Invalid log level");
	          String prefix = this.getLogLevelString(lvl) + ":";
	          this.realLogger.log(realLvl, prefix + msg);
	       }

	       public boolean isLoggable(int level) {
	          return (level >= this.logLevel.intValue());
	       }

	       public void printStackTrace(Throwable e) {
	          this.printStackTrace(null, e);
	       }

	       private void printStackTrace(StringBuffer sb, Throwable e) {
	          StackTraceElement[] stElements = e.getStackTrace();
	          if (sb == null)
	             sb = new StringBuffer("printStackTrace:\n");
	          else
	             sb.append("\n nested exception is: \n");

	          sb.append("\t").append(e.toString()).append("\n");
	          for (int i = 0; i < stElements.length; i++) {
	             sb.append("\t\tat ").append(stElements[i].toString()).append("\n");
	          }
	          if (e.getCause() != null)
	             this.printStackTrace(sb, e.getCause());
	          else
	             log(this.getLogLevel(), sb.toString());
	       }

	       public synchronized void setLogLevel(int lvl) {
	          Integer lvlInteger = new Integer(lvl);
	          if (!levelMap.containsKey(lvlInteger))
	             throw new IllegalArgumentException("Invalid log level!");
	          this.logLevel = lvlInteger;
	          Level level = (Level) levelMap.get(this.logLevel);
	          this.realLogger.setLevel(level);
	          this.realLogger.log(level,
	                              "Log level set to " + getLogLevelString());
	       }

	       public synchronized int getLogLevel() {
	          return this.logLevel.intValue();
	       }

	       public synchronized String getLogLevelString() {
	          return this.getLogLevelString(this.logLevel.intValue());
	       }

	       public String getLogLevelString(int lvl) {
	          String result;
	          switch (lvl) {
	             case LEVEL_DEBUG_MEDIUM:
	                result = "DEBUG2";
	                break;
	             case LEVEL_DEBUG_LOW:
	                result = "DEBUG1";
	                break;
	             case LEVEL_DEBUG_HIGH:
	                result = "DEBUG0";
	                break;
	             case LEVEL_INFO:
	                result = "INFO";
	                break;
	             case LEVEL_WARNING:
	                result = "WARNING";
	                break;
	             case LEVEL_ERROR:
	                result = "ERROR";
	                break;
	             default:
	                result = "[UNKNOWN]";
	                break;
	          }
	          return result;
	       }

	       protected void finalize() throws Throwable {
	          Handler[] handlers = this.realLogger.getHandlers();
	          for (int i=0; i < handlers.length; i++) {
	             handlers[i].flush();
	             handlers[i].close();
	             this.realLogger.removeHandler(handlers[i]);
	          }
	       }

	        public boolean isLoggable(LogRecord record) {
	           return true;  
	        }
	    }

	    private static class LogFormatter
	        extends java.util.logging.Formatter {
	       private LogFormatter() {}

	       public String format(LogRecord record) {
	           Timestamp timestamp = new Timestamp(record.getMillis());
	           String[] callerInfo = getCallerInfo();
	           StringBuffer sb = new StringBuffer(100);
	           sb.append(timestamp.toString()).append(":")
	                 .append("<").append(Thread.currentThread().getName()).append(">")
	                 .append(callerInfo[0])
	                 .append(".").append(callerInfo[1])
	                 .append("(").append(callerInfo[2]).append(")")
	                 .append(": ").append(record.getMessage()).append("\n");
	           return sb.toString();
	        }

	       private String[] getCallerInfo() {
	          String[] result = new String[3];
	          StackTraceElement stack[] = (new Throwable()).getStackTrace();

	          int ix = 0;
	          while (ix < stack.length) {
	             StackTraceElement frame = stack[ix];
	             String clsName = frame.getClassName();
	             if (clsName.indexOf("java.util.logging") >= 0) {
	                break;
	             }
	             ix++;
	          }
	          while (ix < stack.length) {
	             StackTraceElement frame = stack[ix];
	             String clsName = frame.getClassName();
	             if (clsName.indexOf("java.util.logging") < 0 &&
	                 clsName.indexOf("LogManager") < 0) {
	                clsName = clsName.substring(clsName.lastIndexOf(".") + 1);
	                result[0] = clsName;
	                result[1] = frame.getMethodName();
	                result[2] = String.valueOf(frame.getLineNumber());
	                break;
	             }
	             ix++;
	          }
	          return result;
	       }
	    }

	}
