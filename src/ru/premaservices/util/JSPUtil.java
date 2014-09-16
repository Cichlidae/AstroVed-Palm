package ru.premaservices.util;

import java.util.Collection;

public class JSPUtil {
	
	public static boolean contains (Collection<?> coll, Object o) {
	    return coll.contains(o);
	}
	
	public static String getFirstChar (String str) {
		if (str.length() > 0) return String.valueOf(str.charAt(0));
		else return "";
	}

}
