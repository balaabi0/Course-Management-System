package com.project.cms.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends RuntimeException{
	public StudentNotFoundException(int id) {
		super("Student with id "+id+" not found");
	}
}

