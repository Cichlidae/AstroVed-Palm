/**
 * 
 */
package ru.premaservices.util;

/**
 * @author Prema
 *
 */

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory; 

public final class Logger {
	
	public static final String SECURITY_LOGGER = "ru.premaservices.vegcook.securityLogger";

	private Log log;
	
	private Logger () {
	}
	
	public static Logger getLogger(Class<?> clazz) {
		Logger logger = new Logger();
		logger.log = LogFactory.getLog(clazz);
		return logger;
	}
	
	public static Logger getSecurityLogger () {
		Logger logger = new Logger();
		logger.log = LogFactory.getLog(SECURITY_LOGGER);
		return logger;
	}
	
	public void fatal (Object msg) {
		if (log.isFatalEnabled())
			log.fatal(msg);
	}
	
	public void fatal (Object msg, Throwable t) {
		if (log.isFatalEnabled())
			log.fatal(msg, t);
	}
	
	public void error (Object msg) {
		if (log.isErrorEnabled())
			log.error(msg);
	}
	
	public void error (Object msg, Throwable t) {
		if (log.isErrorEnabled())
			log.error(msg, t);
	}
	
	public void warn (Object msg) {
		if (log.isWarnEnabled())
			log.warn(msg);
	}
	
	public void warn (Object msg, Throwable t) {
		if (log.isWarnEnabled())
			log.warn(msg, t);
	}
	
	public void info (Object msg) {
		if (log.isInfoEnabled())
			log.info(msg);
	}
	
	public void info (Object msg, Throwable t) {
		if (log.isInfoEnabled())
			log.info(msg, t);
	}
	
	public void debug (Object msg) {
		if (log.isDebugEnabled())
			log.debug(msg);
	}
	
	public void debug (Object msg, Throwable t) {
		if (log.isDebugEnabled())
			log.debug(msg, t);
	}
	
	public void securityInfo (String msg, String userId, String userLogin, String userIP, long time) {
		this.info(msg + "->" + userLogin + " [" + userId + ", " + userIP + ", " + String.valueOf(new Date(time)) + "]");
	}
	
	public void securityWarn (String msg, String userId, String userLogin, String userIP, long time) {
		this.securityWarn(msg, userId, userLogin, userIP, time, null);
	}
	
	public void securityWarn (String msg, String userId, String userLogin, String userIP, long time, Throwable t) {
		this.info(msg + "->" + userLogin + " [" + userId + ", " + userIP + ", " + String.valueOf(new Date(time)) + "]", t);
	}

}
