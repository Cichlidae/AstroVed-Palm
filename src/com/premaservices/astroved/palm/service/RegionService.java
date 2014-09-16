package com.premaservices.astroved.palm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.premaservices.astroved.palm.dao.RegionManager;
import com.premaservices.astroved.palm.model.City;
import com.premaservices.astroved.palm.model.Country;

@Service
public class RegionService {

	@Autowired
	private RegionManager regionManager;
	
	@Transactional(readOnly = true)
	public List<Country> getCountries () {
		return regionManager.getCountries();
	}
	
	@Transactional(readOnly = true)
	public List<Country> getCountriesWithCities () {
		return regionManager.getCountriesWithCities();
	}
	
	@Transactional(readOnly = true)
	public Country getCountry (Integer id) {
		Country country = regionManager.getCountry(id);
		country.getCities();
		return country;
	}
	
	@Transactional(readOnly = true)
	public Country getCountryLazily (Integer id) {
		Country country = regionManager.getCountry(id);
		return country;
	}
	
	@Transactional(readOnly = true)
	public City getCity (Integer id) {
		City city = regionManager.getCity(id);
		return city;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Country updateCountry (Country country) {
		return regionManager.updateCountry(country);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public City updateCity (City city) {
		return regionManager.updateCity(city);
	}
	
}
