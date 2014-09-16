package com.premaservices.astroved.palm.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.premaservices.astroved.palm.model.City;
import com.premaservices.astroved.palm.model.Country;

@Repository
public class RegionManager {

	@Autowired
    private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Country updateCountry (Country country) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(country);
		return country;	
	}
	
	public City updateCity (City city) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(city);
		return city;
	}
	
	public Country getCountry (Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Country country = (Country)session.get(Country.class, id);
		if (!Hibernate.isInitialized(country.getCities())) {
			Hibernate.initialize(country.getCities());
		}
		return country;
	}
	
	public Country getCountryLazily (Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Country country = (Country)session.get(Country.class, id);
		return country;
	}
	
	@SuppressWarnings("unchecked")
	public List<Country> getCountries () {
		Session session = sessionFactory.getCurrentSession();
		List<Country> list = (List<Country>)session.createCriteria(Country.class).addOrder(Order.asc("country")).list();
		return list;	
	}
	
	@SuppressWarnings("unchecked")
	public List<Country> getCountriesWithCities () {
		Session session = sessionFactory.getCurrentSession();
		List<Country> list = (List<Country>)session.createCriteria(Country.class).addOrder(Order.asc("country")).list();
		for (Country c : list) {
			Hibernate.initialize(c.getCities());
		}
		return list;	
	}
	
	public City getCity (Integer id) {
		Session session = sessionFactory.getCurrentSession();
		City city = (City)session.get(City.class, id);
		return city;
	}
	
}
