package com.project.cms.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.cms.exception.StudentNotFoundException;
import com.project.cms.jpa.StudentRepository;
import com.project.cms.model.Student;

@Service
public class StudentService {
	
	@Autowired
	StudentRepository studentRepo;
	
	public Student changePassword(int student_id,Student student) {
		Optional<Student> studentOptional=studentRepo.findById(student_id);
		if(!studentOptional.isPresent())
				throw new StudentNotFoundException(student_id);
		Student studentDetail=studentOptional.get();
		String pass=student.getPassword();
		studentDetail.setPassword(pass);
		return studentRepo.save(studentDetail);
	}

}
