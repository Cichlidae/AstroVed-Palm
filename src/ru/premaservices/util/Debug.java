/**
 * 
 */
package ru.premaservices.util;

/**
 * @author Prema
 *
 */
public final class Debug {
	
	public static final boolean DEBUG = true;
	
	public static void stdoutln (String msg) {
		if (DEBUG)
			System.out.println(msg);
	}

}
