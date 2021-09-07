package com.project.cms.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "students")
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String email;
	private String phone;
	@JsonIgnore
	@Pattern(message="Password should contain minimum six characters, at least one uppercase letter, one lowercase letter, one number and one special character",regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$")
	private String password;
	@JsonIgnore
	@ManyToMany
	@JoinTable(
	  name = "subscription", 
	  joinColumns = @JoinColumn(name = "student_id"), 
	  inverseJoinColumns = @JoinColumn(name = "course_id"))
	private Set<Course> subscribedCourses=new HashSet<Course>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	 @JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<Course> getSubscribedCourses() {
		return subscribedCourses;
	}
//	public void setSubscribedCourses(Set<Course> subscribedCourses) {
//		this.subscribedCourses = subscribedCourses;
//	}
	public void addSubscribedCourse(Course subscribedCourse) {
		this.subscribedCourses.add(subscribedCourse);
	}
	public void removeSubscribedCourse(Course subscribedCourse) {
		this.subscribedCourses.remove(subscribedCourse);
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + ", password="
				+ password + ", subscribedCourses=" + subscribedCourses + "]";
	}
	
}
