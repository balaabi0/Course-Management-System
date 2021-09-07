package com.project.cms.controller;
import java.util.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.cms.exception.StudentNotFoundException;
import com.project.cms.jpa.StudentRepository;
import com.project.cms.model.Student;
import com.project.cms.service.StudentService;

@RestController
public class StudentController {
	
		@Autowired
		StudentService studentService;
		
		@PutMapping("/students/{student_id}/change_password")
		public ResponseEntity<Object> changePassword(@PathVariable int student_id, @Valid @RequestBody Student student){
			Student stud=studentService.changePassword(student_id, student);
			return new ResponseEntity<Object>(HttpStatus.OK);
			
		}
}
