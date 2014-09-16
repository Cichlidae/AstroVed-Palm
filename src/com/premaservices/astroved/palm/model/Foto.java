package com.premaservices.astroved.palm.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.DiscriminatorType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import ru.premaservices.extention.spring.hibernate.validation.constraints.NotNullObjectId;

@Entity
@Table(name="FOTOS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING, columnDefinition="enum('FACE', 'PALM')")
public abstract class Foto {
	
	@Id
	@Column(name="filesuffix", length = 22, nullable = false, unique = true)
	private String id;
	
	@NotNull @NotNullObjectId(value = "uuid")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="UUID", nullable = false)
	private Student student;
	
	@NotNull
	@Column(name = "TSTAMP", nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date timestamp;
	
	@Lob @Basic(fetch = FetchType.LAZY)
	@Column(name="DESCRIPTION")	
	private String description;	

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}		

}
