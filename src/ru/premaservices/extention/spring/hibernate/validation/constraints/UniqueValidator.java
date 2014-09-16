package ru.premaservices.extention.spring.hibernate.validation.constraints;

import java.lang.reflect.Method;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import org.springframework.beans.factory.annotation.Autowired;

import ru.premaservices.extention.spring.hibernate.validation.dao.ValidationManager;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {

	@Autowired
	private ValidationManager manager;
	
	private String[] names;
	
	@Override
	public void initialize(Unique annotation) {
		names = annotation.names();
	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		
		boolean result = true;
		
		if (manager == null || object == null) return true;
		
		Class<?> c = object.getClass(); 
		Object property = null;
		
		ConstraintViolationBuilder builder = context.buildConstraintViolationWithTemplate("{ru.premaservices.astroved.student.validation.Unique." + c.getSimpleName() + ".message}");
		
		for (String name : names) {
		
			try {	
				String m = "get" + String.valueOf(name.charAt(0)).toUpperCase() + name.substring(1);		
				Method method = c.getDeclaredMethod(m);
				property = method.invoke(object);			
			}
			catch (Exception e) {
				e.printStackTrace();
				return false;
			}
				
			boolean r = manager.validateUnique(c, name, property);
			if (!r) {	
				builder.addNode(name).addConstraintViolation();
			}
			result &= r;
		
		}
			
		return result;
	}

}
