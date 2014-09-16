package com.premaservices.astroved.palm.dao;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class EqualStudentFilter implements StudentFilter {
	
	private String property;
	private Object value;
	
	public EqualStudentFilter () {		
	}
		
	public EqualStudentFilter (String property) {
		this.property = property;
	}
	
	public EqualStudentFilter (String property, Object value) {
		this.property = property;
	}

	@Override
	public Criterion getFilterRestriction() {
		return Restrictions.eq(property, value);
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

}
