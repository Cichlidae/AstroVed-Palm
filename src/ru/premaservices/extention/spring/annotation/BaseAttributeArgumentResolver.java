/**
 * 
 */
package ru.premaservices.extention.spring.annotation;

import java.lang.annotation.Annotation;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

public abstract class BaseAttributeArgumentResolver implements WebArgumentResolver {

	@Override
	public Object resolveArgument (MethodParameter parameter, NativeWebRequest request) throws Exception {
				
		Annotation[] paramAnns = parameter.getParameterAnnotations(); 
		for (Annotation paramAnn : paramAnns) {
			if (supports(paramAnn)) {
				String attrName = getAttribute(paramAnn);
				Object attrValue = getValue(request, attrName, parameter.getParameterType());
				checkValue(attrValue, paramAnn, parameter.getParameterType());
				return attrValue;
			}
		}
		return WebArgumentResolver.UNRESOLVED;
	}
	
	protected abstract boolean supports(Annotation a);
	protected abstract String getAttribute(Annotation a);
	protected abstract Object getValue(NativeWebRequest request, String attrName, Class<?> clazz) throws Exception;
	
	/**
     * Can be used to validate value based on annotation contents. For example, to check if value is required
     * @param value the value returned from the request
     * @param a matched annotation
     */
    protected abstract void checkValue(Object value, Annotation a, Class<?> clazz) throws Exception;

}
