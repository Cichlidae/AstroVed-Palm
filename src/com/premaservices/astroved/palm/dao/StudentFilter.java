package com.premaservices.astroved.palm.dao;

import org.hibernate.criterion.Criterion;

public interface StudentFilter {
	
	public Criterion getFilterRestriction ();

}
