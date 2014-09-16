package com.premaservices.astroved.palm.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.premaservices.astroved.palm.model.Course;
import com.premaservices.astroved.palm.model.Student;

@Repository
public class StudentManager {
	
	@Autowired
    private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Student updateStudent (Student student) {
		Session session = sessionFactory.getCurrentSession();	
		session.saveOrUpdate(student);
		return student;
	}
	
	public Student getStudent (String uid) {
		Session session = sessionFactory.getCurrentSession();
		Student student = (Student)session.get(Student.class, uid);
		if (!Hibernate.isInitialized(student.getBiography())) {
			Hibernate.initialize(student.getBiography());
		}	
		if (!Hibernate.isInitialized(student.getFaces())) {
			Hibernate.initialize(student.getFaces());
		}
		if (!Hibernate.isInitialized(student.getPalms())) {
			Hibernate.initialize(student.getPalms());
		}
		return student;
	}
	
	public Student getStudentLazily (String uid) {
		Session session = sessionFactory.getCurrentSession();
		Student student = (Student)session.get(Student.class, uid);
		return student;
	}
	
	@SuppressWarnings("unchecked")
	public List<Student> getStudents () {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Student.class);		
		criteria.add(Restrictions.eq("hidden", false));		
		return (List<Student>)criteria.list();		
	}
	
	@SuppressWarnings("unchecked")
	public List<Student> getStudents (StudentFilterPack filter) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Student.class);	
		
		if (filter != null) {
			for (StudentFilter f : filter.getArray()) {
				criteria.add(f.getFilterRestriction());
			}
		}		
		criteria.add(Restrictions.eq("hidden", false));
		return (List<Student>)criteria.list();						
	}	

	@SuppressWarnings("unchecked")
	public List<Student> getStudents (StudentFilter filter) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Student.class);	
		
		if (filter != null) {
			criteria.add(filter.getFilterRestriction());
		}
		criteria.add(Restrictions.eq("hidden", false));
		return (List<Student>)criteria.list();	
	}
	
	@SuppressWarnings("unchecked")
	public List<Student> getHiddenStudents () {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Student.class);		
		criteria.add(Restrictions.eq("hidden", true));		
		return (List<Student>)criteria.list();		
	}
	
}
