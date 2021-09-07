package com.project.cms.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "courses")
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	@NotNull(message = "Course Name is mandatory")
	@Size(min=1,message="Course Name is mandatory")
	private String course_name;
	@NotNull(message = "Course Name is mandatory")
	@Size(min=1,message = "Course Description is mandatory")
	private String description; 
	@JsonIgnore
	@ManyToOne
	private Lecturer lecturer;
	@JsonIgnore
	@ManyToMany(mappedBy = "subscribedCourses")
	private Set<Student> subscribedStudents=new HashSet<Student>();

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Lecturer getLecturer() {
		return lecturer;
	}
	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}
	public Set<Student> getSubscribedStudents() {
		return subscribedStudents;
	}
//	public void setSubscribedStudents(Set<Student> subscribedStudents) {
//		this.subscribedStudents = subscribedStudents;
//	}
	public void addSubscribedStudent(Student subscribedStudent) {
		this.subscribedStudents.add(subscribedStudent);
	}
	
	@Override
	public String toString() {
		return "Course [id=" + id + ", course_name=" + course_name + ", description=" + description + ", lecturer="
				+ lecturer + ", subscribedStudents=" + subscribedStudents + "]";
	}
	
	
}
