package ru.premaservices.extention.spring.hibernate.validation.dao;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ValidationManager {

	@Autowired
    private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Transactional(readOnly = true)
	public boolean validateUnique (Class<?> clazz, Serializable id) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(clazz, id) == null ? true : false;
	}
	
	@Transactional(readOnly = true)
	public boolean validateUnique (Class<?> clazz, String name, Object value) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(clazz);
		criteria.add(Restrictions.eq(name, value));
		return criteria.uniqueResult() == null ? true : false;
	}
	
}
