package com.project.cms.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CourseNotFoundException extends RuntimeException{
	public CourseNotFoundException(int id) {
		super("Course with id "+id+" not found");
	}
}

