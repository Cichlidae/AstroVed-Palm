package ru.premaservices.util;

import javax.servlet.http.HttpSession;

public class ApplicationSecurityManager {
	
	public static final String USER = "user";

    public Object getUser(HttpSession session)
    {
        return session != null ? session.getAttribute(USER) : null;
    }

    public void setUser(HttpSession session, Object employee)
    {
    	if (session != null)
    		session.setAttribute(USER, employee);
    }

    public void removeUser(HttpSession session)
    {
    	if (session != null)
    		session.removeAttribute(USER);
    }
	
}
