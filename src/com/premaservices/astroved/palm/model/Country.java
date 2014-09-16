package com.premaservices.astroved.palm.model;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.validator.constraints.NotBlank;

import ru.premaservices.extention.spring.hibernate.validation.constraints.Unique;

@Entity
@Table(name="COUNTRIES") @Unique(names = {"country"})
public class Country {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID") 
	private Integer id;
	
	@NotBlank
	@Column(name="COUNTRY", length=30, nullable = false, unique = true)
	private String country;
	
	@OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
	@Sort(type = SortType.NATURAL)
	@Cascade(value = CascadeType.SAVE_UPDATE)
	private SortedSet<City> cities = new TreeSet<City>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public SortedSet<City> getCities() {
		return cities;
	}

	public void setCities(SortedSet<City> cities) {
		this.cities = cities;
	}

}
