package com.premaservices.astroved.palm.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import ru.premaservices.extention.spring.hibernate.validation.constraints.Unique;

@Entity
@Table(name="USERS") @Unique(names = {"username", "email", "student"})
public class User implements Serializable {
	
	private static final long serialVersionUID = 357668609542213439L;
	
	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "student"))
	@Id @GeneratedValue(generator = "generator")
	@Column(name="USERNAME", length = 36, nullable = false, unique = true)
	private String username;
	
	@OneToOne(fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
	private Student student;
	
	@Email
	@Column(name="EMAIL", length = 50, nullable = false)
	private String email;
	
	@NotBlank @Length(min = 10, max = 32)
	@Column(name="PASSWORD", length = 32, nullable = false)
	private String password;

	@Column(name = "CREATE_TIME", nullable = true)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date createTime;
	
	@Column(name="LOCKED")
	private Boolean locked = false;

	@NotNull
	@Column(name="ROLE")
	@Enumerated(EnumType.STRING)
	private Role role = Role.GUEST;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}	
	
}
