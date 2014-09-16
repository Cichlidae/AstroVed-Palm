package com.premaservices.astroved.palm.dao;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class TrueStudentFilter implements StudentFilter {
	
	private String property;
	
	public TrueStudentFilter () {		
	}
		
	public TrueStudentFilter (String property) {
		this.property = property;
	}		

	@Override
	public Criterion getFilterRestriction() {
		return Restrictions.eq(property, true);
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

}
