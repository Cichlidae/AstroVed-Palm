package ru.premaservices.util;

import javax.servlet.http.HttpServletRequest;

public final class ServletUtil {
	
	public final static String getServletURI (HttpServletRequest request) {
		
		String pathInfo = request.getPathInfo();
		String requestURI = request.getRequestURI();
		
		if (pathInfo != null) {
			int index = requestURI.indexOf(pathInfo);
			if (index > -1) {
				return requestURI.substring(0, index);
			}
		}
		return requestURI;
	}
	
	public final static String getRedirectUrl (String fromUrl, String toUrl) {
		
		if (!toUrl.startsWith("/")) return toUrl;
		
		String url = fromUrl.substring(1); 
		String newUrl = toUrl;
		
		int startIdx = 0; int idx = -1;
		
		while ((idx = url.indexOf("/", startIdx)) > -1) {
			if (startIdx > 0) 
				newUrl = "/" + newUrl;
			newUrl = ".." + newUrl;
			startIdx = idx + 1;			
		}
		return newUrl;
	}

}
