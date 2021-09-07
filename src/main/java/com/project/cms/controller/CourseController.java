package com.project.cms.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import org.springframework.validation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.cms.exception.CourseNotFoundException;
import com.project.cms.exception.LecturerNotFoundException;
import com.project.cms.jpa.CourseRepository;
import com.project.cms.jpa.LecturerRepository;
import com.project.cms.model.Course;
import com.project.cms.model.Lecturer;
import com.project.cms.model.Student;
import com.project.cms.service.CourseService;

@RestController
public class CourseController {
	

	@Autowired
	CourseService courseService;
	
	
	@PostMapping("/course")
	public ResponseEntity<Object> addCourse(@RequestParam int lecturer_id, @Valid @RequestBody Course course) {
		Course savedCourse=courseService.addCourse(course, lecturer_id);
		URI location=ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(savedCourse.getId()).toUri();
		return ResponseEntity.created(location).build(); 
	}
	
	@GetMapping("/course/search")
	public ResponseEntity<Object> searchCourses(@RequestParam String searchterm) {
		List<Course> matchedCourses=courseService.searchCourses(searchterm);
		return new ResponseEntity(matchedCourses, HttpStatus.OK);
	}
	
	@GetMapping("/course/{course_id}/students_contacts")
	public ResponseEntity<Object> getSubscribedStudents(@PathVariable int course_id){
		Set<Student> students=courseService.getSubscribedStudents(course_id);
		return new ResponseEntity(students, HttpStatus.OK);
	}
	
	@GetMapping("/course/{course_id}/lecturer_contacts")
	public ResponseEntity<Object> getCourseLecturer(@PathVariable int course_id){
		Lecturer lecturer=courseService.getCourseLecturer(course_id);
		return new ResponseEntity(lecturer, HttpStatus.OK);
	}

}
