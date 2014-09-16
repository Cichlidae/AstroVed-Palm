/**
 * 
 */
package ru.premaservices.extention.spring.annotation;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.context.request.NativeWebRequest;

public class SessionAttributeArgumentResolver extends BaseAttributeArgumentResolver {

	/* (non-Javadoc)
	 * @see ru.premaservices.extention.mvc.annotation.BaseAttributeArgumentResolver#supports(java.lang.annotation.Annotation)
	 */
	@Override
	protected boolean supports(Annotation a) {		
		return SessionAttribute.class.isInstance(a);
	}

	/* (non-Javadoc)
	 * @see ru.premaservices.extention.mvc.annotation.BaseAttributeArgumentResolver#getAttribute(java.lang.annotation.Annotation)
	 */
	@Override
	protected String getAttribute(Annotation a) {		
		return ((SessionAttribute)a).value();
	}

	/* (non-Javadoc)
	 * @see ru.premaservices.extention.mvc.annotation.BaseAttributeArgumentResolver#getValue(org.springframework.web.context.request.NativeWebRequest, java.lang.String)
	 */
	@Override
	protected Object getValue(NativeWebRequest request, String attrName, Class<?> clazz) throws Exception {		
		HttpServletRequest httprequest = (HttpServletRequest)request.getNativeRequest();
	    HttpSession session = httprequest.getSession(false);
	    if (session != null) {
            return session.getAttribute(attrName);
        }
	    else {
	    	throw new HttpSessionRequiredException(
	    			"No HttpSession found for resolving parameter '" + attrName + "' of type [" + clazz + "]"
	    	);
	    }
	}

	/* (non-Javadoc)
	 * @see ru.premaservices.extention.mvc.annotation.BaseAttributeArgumentResolver#checkValue(java.lang.Object, java.lang.annotation.Annotation, org.springframework.core.MethodParameter)
	 */
	@Override
	protected void checkValue(Object value, Annotation a, Class<?> clazz) throws Exception {
		boolean required = ((SessionAttribute)a).required();
		if (required && value == null) {
			throw new IllegalStateException(
					"Missing required parameter '" + getAttribute(a) + "' of type [" + clazz + "]"
			);
		}
	}

}
