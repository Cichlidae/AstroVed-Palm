package ru.premaservices.extention.spring.hibernate.validation.constraints;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ru.premaservices.util.CommonUtil;

public class NotNullObjectIdValidator implements ConstraintValidator<NotNullObjectId, Object> {

	private String value;
	
	@Override
	public void initialize(NotNullObjectId annotation) {
		value = annotation.value();	
	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		
		try {
			
			if (object == null) return false;
			
			Class<?> c = object.getClass();  
			
			String name = "get" + String.valueOf(value.charAt(0)).toUpperCase() + value.substring(1);
			
			Method method = c.getDeclaredMethod(name);
			Object id = method.invoke(object);
			
			if (id == null) return false;
			
			Type type = method.getReturnType();
			
			if (type == String.class) {
				if (!CommonUtil.isNotBlank((String)id)) return false;
			}
			else if (type == Integer.class || type == int.class) {
				if ((Integer)id < 1) return false;
				
			}
			else if (type == Long.class || type == long.class) {
				if ((Long)id < 1) return false;
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return true;
		}
			
		return true;
		
	}

}
