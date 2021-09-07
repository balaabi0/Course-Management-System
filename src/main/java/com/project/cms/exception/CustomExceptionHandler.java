package com.project.cms.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String,String> er=new HashMap<String,String>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        er.put(fieldName, errorMessage);
	    });
		ErrorDetails err= new ErrorDetails(new Date(),er.toString());	
//		ErrorDetails err= new ErrorDetails(new Date(),ex.getBindingResult().toString());		
		return new ResponseEntity(err,HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object>handleAllExceptions(Exception ex, WebRequest request){
		ErrorDetails err= new ErrorDetails(new Date(),ex.getMessage());
		return new ResponseEntity(err,HttpStatus.INTERNAL_SERVER_ERROR);		
	}
	
	@ExceptionHandler(StudentNotFoundException.class)
	public final ResponseEntity<Object>handleStudentNotFoundExceptions(Exception ex, WebRequest request){
		ErrorDetails err= new ErrorDetails(new Date(),ex.getMessage());		
		return new ResponseEntity(err,HttpStatus.NOT_FOUND);		
	}
	
	@ExceptionHandler(LecturerNotFoundException.class)
	public final ResponseEntity<Object>handleLecturerNotFoundExceptions(Exception ex, WebRequest request){
		ErrorDetails err= new ErrorDetails(new Date(),ex.getMessage());		
		return new ResponseEntity(err,HttpStatus.NOT_FOUND);		
	}
	
	@ExceptionHandler(CourseNotFoundException.class)
	public final ResponseEntity<Object>handleCourseNotFoundExceptions(Exception ex, WebRequest request){
		ErrorDetails err= new ErrorDetails(new Date(),ex.getMessage());		
		return new ResponseEntity(err,HttpStatus.NOT_FOUND);	
		
		
	}
	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public final ResponseEntity<Object>handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request){
//		ErrorDetails err= new ErrorDetails(new Date(),ex.getMessage());		
//		return new ResponseEntity(err,HttpStatus.BAD_REQUEST);		
//	}
}	
