package com.premaservices.astroved.palm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import ru.premaservices.extention.spring.hibernate.validation.constraints.NotNullObjectId;
import ru.premaservices.extention.spring.hibernate.validation.constraints.Unique;

@Entity
@Table(name="CITIES") @Unique(names = {"city"})
public class City implements Comparable<City> {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private Integer id;
	
	@NotBlank 
	@Column(name="CITY", length=30, nullable = false, unique = true)
	private String city;
	
	@NotNull @NotNullObjectId(value = "id")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="COUNTRY", nullable = false)
	private Country country;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public int compareTo(City that) {
		if (that == null) {
	       return -1;
	    }
		return this.getCity().compareTo(that.getCity());
	}
	
}
