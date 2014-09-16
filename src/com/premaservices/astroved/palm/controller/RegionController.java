package com.premaservices.astroved.palm.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.premaservices.astroved.palm.model.City;
import com.premaservices.astroved.palm.model.Country;
import com.premaservices.astroved.palm.service.RegionService;

@Controller
public class RegionController {
	
	public static final String DICTIONARY_VIEW_REQUEST = "/region/view";
	public static final String COUNTRY_FORM_REQUEST = "/country/update";
	public static final String CITY_FORM_REQUEST = "/city/update";

	public static final String DICTIONARY_VIEW_JSP = "dictionary-view";
	public static final String COUNTRY_FORM_JSP = "country-form";
	public static final String CITY_FORM_JSP = "city-form";
	public static final String COUNTRY_ROW_JSP = "country-row";
	public static final String CITY_ROW_JSP = "city-row";
	
	public static final String COUNTRY_LIST_KEY = "countries";
	public static final String CITY_LIST_KEY = "cities";
	
	@Autowired
	private RegionService service;
	
	@RequestMapping(value = DICTIONARY_VIEW_REQUEST)
	public String getDictionaryView (Model model) {
		model.addAttribute(COUNTRY_LIST_KEY, service.getCountriesWithCities());
		return DICTIONARY_VIEW_JSP;
	}
	
	@RequestMapping(value = COUNTRY_FORM_REQUEST)
	public String getNewCountryForm (Model model) { //AJAX
		model.addAttribute(new Country());
		return COUNTRY_FORM_JSP;
	}
	
	@RequestMapping(value = COUNTRY_FORM_REQUEST + "/{id}")
	public String getEditCountryForm (@PathVariable("id") Integer id, Model model) { //AJAX
		model.addAttribute(service.getCountryLazily(id));
		return COUNTRY_FORM_JSP;
	}

	@RequestMapping(value = CITY_FORM_REQUEST)
	public String getNewCityForm (Model model) { //AJAX
		model.addAttribute(new City());
		model.addAttribute(COUNTRY_LIST_KEY, service.getCountries());
		return CITY_FORM_JSP;
	}
	
	@RequestMapping(value = CITY_FORM_REQUEST + "/{id}")
	public String getEditCityForm (@PathVariable("id") Integer id, Model model) { //AJAX
		model.addAttribute(service.getCity(id));
		return CITY_FORM_JSP;
	}
	
	@RequestMapping(value = COUNTRY_FORM_REQUEST, method = RequestMethod.POST)
	public ModelAndView updateCountry (@ModelAttribute("country") @Valid Country country, BindingResult result) { //AJAX
		
		ModelAndView model = null;
		
		if (result.hasErrors()) {
			model = new ModelAndView(COUNTRY_FORM_JSP);
			model.addAllObjects(result.getModel());
		}
		else {
			model = new ModelAndView(COUNTRY_ROW_JSP);
			Country c = service.updateCountry(country);			
			model.addObject(c);	
		}
		
		return model;
	}
		
	@RequestMapping(value = CITY_FORM_REQUEST, method = RequestMethod.POST)
	public ModelAndView updateCity (@ModelAttribute("city") @Valid City city, BindingResult result) { //AJAX
		
		ModelAndView model = null;
		
		if (result.hasErrors()) {
			model = new ModelAndView(CITY_FORM_JSP);
			model.addAllObjects(result.getModel());
			model.addObject(COUNTRY_LIST_KEY, service.getCountries());
		}
		else {
			City c = service.getCity(service.updateCity(city).getId());
			model = new ModelAndView(CITY_ROW_JSP);
			model.addObject(c);		
		}
		
		return model;
	}
	
}
