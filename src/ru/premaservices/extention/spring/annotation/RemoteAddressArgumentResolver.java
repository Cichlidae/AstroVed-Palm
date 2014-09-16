package ru.premaservices.extention.spring.annotation;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.NativeWebRequest;

public class RemoteAddressArgumentResolver extends BaseAttributeArgumentResolver {

	@Override
	protected boolean supports(Annotation a) {
		return RemoteAddress.class.isInstance(a);
	}

	@Override
	protected String getAttribute(Annotation a) {
		return null;
	}

	@Override
	protected Object getValue(NativeWebRequest request, String attrName, Class<?> clazz) throws Exception {
		HttpServletRequest httprequest = (HttpServletRequest)request.getNativeRequest();
        return httprequest.getRemoteAddr();    
	}

	@Override
	protected void checkValue(Object value, Annotation a, Class<?> clazz) throws Exception {
		boolean required = ((RemoteAddress)a).required();
		if (required && value == null) {
			throw new IllegalStateException(
					"Missing required parameter '" + getAttribute(a) + "' of type [" + clazz + "]"
			);
		}	
	}

}
