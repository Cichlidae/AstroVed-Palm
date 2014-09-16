package com.premaservices.astroved.palm.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import ru.premaservices.extention.spring.hibernate.validation.constraints.NotNullObjectId;

@Entity
@Table(name="STUDENTS")
public class Student {
	
	@Id @GenericGenerator(name = "guid", strategy = "guid") @GeneratedValue(generator="guid")
	@Column(name="UUID", length=36, nullable = false, unique = true)
	private String uid;
	
	@NotBlank
	@Column(name="FAMILY", length=30, nullable = false)
	private String family;
	
	@NotBlank
	@Column(name="NAME", length=50, nullable = false)
	private String name;
	
	@NotNull
	@Column(name = "BIRTHDAY", nullable = false)
	@Temporal(value = TemporalType.DATE)
	private Date birthday;
	
	@NotNull
	@Column(name = "BIRTHDAYTIME", nullable = true)
	@Temporal(value = TemporalType.TIME)
	private Date birthdayTime;
	
	@NotNull @NotNullObjectId(value = "id")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CITY", nullable = false)
	private City city;
	
	@Range(min = 111111111, max = 999999999)
	@Column(name = "DASHAS")
	private Integer dashas;
	
	@Lob @Basic(fetch = FetchType.EAGER)
	@Column(name="PROFESSIONS")
	private String professions;
	
	@NotNull
	@Column(name="COURSE", columnDefinition="enum('FIRST', 'SECOND', 'THIRD', 'FORTH')")
	@Enumerated(EnumType.STRING)
	private Course course = Course.FIRST;
	
	@Lob @Basic(fetch = FetchType.LAZY)
	@Column(name="BIOGRAPHY")
	private String biography;
	
	@OneToOne(mappedBy="student", fetch=FetchType.LAZY)
	@Cascade(value = CascadeType.SAVE_UPDATE)
	private User user;	
	
	@OneToMany(mappedBy="student", fetch=FetchType.LAZY)
	@Cascade(value = CascadeType.SAVE_UPDATE)
	private Set<Face> faces = new HashSet<Face>();
	
	@OneToMany(mappedBy="student", fetch=FetchType.LAZY)
	@Cascade(value = CascadeType.SAVE_UPDATE)
	private Set<Face> palms = new HashSet<Face>();
	
	@Column(name="HIDDEN")
	private Boolean hidden = false;
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getBirthdayTime() {
		return birthdayTime;
	}

	public void setBirthdayTime(Date birthdayTime) {
		this.birthdayTime = birthdayTime;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Integer getDashas() {
		return dashas;
	}

	public void setDashas(Integer dashas) {
		this.dashas = dashas;
	}

	public String getProfessions() {
		return professions;
	}

	public void setProfessions(String professions) {
		this.professions = professions;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Face> getFaces() {
		return faces;
	}

	public void setFaces(Set<Face> faces) {
		this.faces = faces;
	}

	public Set<Face> getPalms() {
		return palms;
	}

	public void setPalms(Set<Face> palms) {
		this.palms = palms;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

}
