package com.project.cms.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.cms.exception.CourseNotFoundException;
import com.project.cms.exception.LecturerNotFoundException;
import com.project.cms.jpa.CourseRepository;
import com.project.cms.jpa.LecturerRepository;
import com.project.cms.model.Course;
import com.project.cms.model.Lecturer;
import com.project.cms.model.Student;

import org.springframework.stereotype.Service;

@Service
public class CourseService {
	@Autowired
	CourseRepository courseRepo;
	@Autowired
	LecturerRepository lecturerRepo;
	
	public Course addCourse(Course course,int lecturer_id) {
		Optional<Lecturer> lecturerOptional=lecturerRepo.findById(lecturer_id);
		if(!lecturerOptional.isPresent())
			throw new LecturerNotFoundException(lecturer_id);
		Lecturer lecturer=lecturerOptional.get();
		course.setLecturer(lecturer);
		Course savedCourse=courseRepo.save(course);
		return savedCourse;
	}
	
	public List<Course> searchCourses(String searchterm){
		List<Course> courses=courseRepo.findAll();	
		List<Course> matchedCourses=courses.stream()
									.filter((course)->(course.getCourse_name().toLowerCase().contains(searchterm.toLowerCase()) || course.getDescription().toLowerCase().contains(searchterm.toLowerCase())))
									.collect(Collectors.toList());
		return matchedCourses;
		
	}
	
	public Set<Student> getSubscribedStudents(int course_id){
		Optional<Course> courseOptional=courseRepo.findById(course_id);
		if(!courseOptional.isPresent())
			throw new CourseNotFoundException(course_id);
		Set<Student> students=courseOptional.get().getSubscribedStudents();
		return students;
	}
	
	public Lecturer getCourseLecturer(int course_id) {
		Optional<Course> courseOptional=courseRepo.findById(course_id);
		if(!courseOptional.isPresent())
			throw new CourseNotFoundException(course_id);
		Lecturer lecturer=courseOptional.get().getLecturer();
		return lecturer;
	}
	
}

