package com.premaservices.astroved.palm.model;

import java.util.Comparator;

public class StudentComparator implements Comparator<Student> {

	@Override
	public int compare(Student st1, Student st2) {
		if (st1 == null && st2 == null) return 0;
		if (st1 == null) return 1;
		if (st2 == null) return -1;
		int r = st1.getCourse().compareTo(st2.getCourse());
		if (r == 0) {
			return st1.getFamily().compareTo(st2.getFamily());
		}
		else {
			return r;
		}
	}
	
}
