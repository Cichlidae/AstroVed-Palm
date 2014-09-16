package com.premaservices.astroved.palm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.premaservices.astroved.palm.model.Student;
import com.premaservices.astroved.palm.dao.EqualStudentFilter;
import com.premaservices.astroved.palm.dao.StudentFilterPack;
import com.premaservices.astroved.palm.dao.StudentFilterType;
import com.premaservices.astroved.palm.dao.StudentManager;

@Service
public class StudentService {

	@Autowired
	private StudentManager studentManager;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Student updateStudent (Student student) {	
		return studentManager.updateStudent(student);	
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Student hideStudent (String uid) {
		Student student = studentManager.getStudentLazily(uid);
		student.setHidden(true);
		return studentManager.updateStudent(student);		
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Student showStudent (String uid) {
		Student student = studentManager.getStudentLazily(uid);
		student.setHidden(false);
		return studentManager.updateStudent(student);		
	}
	
	@Transactional(readOnly = true)
	public List<Student> getStudents () {
		return studentManager.getStudents();
	}
	
	@Transactional(readOnly = true)
	public List<Student> getStudents (StudentFilterPack filter) {
		return studentManager.getStudents(filter);
	}
	
	public List<Student> getHiddenStudents () {
		return studentManager.getHiddenStudents();
	}
	
	@Transactional(readOnly = true)
	public List<Student> searchByFamily (EqualStudentFilter family) {
		return studentManager.getStudents(family);				
	}
	
	@Transactional(readOnly = true)
	public Student getStudent (String uid) {
		return studentManager.getStudent(uid);
	}
	
	@Transactional(readOnly = true)
	public Student getStudentLazily (String uid) {
		return studentManager.getStudentLazily(uid);
	}
	
}
