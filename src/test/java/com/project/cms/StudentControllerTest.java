package com.project.cms;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.cms.controller.StudentController;
import com.project.cms.exception.StudentNotFoundException;
import com.project.cms.model.Student;
import com.project.cms.service.StudentService;

@RunWith(SpringRunner.class)
@WebMvcTest(StudentController.class)
public class StudentControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private StudentService studentService;
	
	@Test
	public void changePassword() throws Exception {
		String uri = "/students/1/change_password";
		Student mockStudent=new Student();
		mockStudent.setId(1);
		mockStudent.setName("bala");
		mockStudent.setEmail("bala@gmail.com");
		mockStudent.setPhone("3445334543");
		mockStudent.setPassword("Password@123");
		ObjectMapper Obj = new ObjectMapper();  
		String mockstudentJson = Obj.writeValueAsString(mockStudent);
		Mockito.when(studentService.changePassword(Mockito.anyInt(),Mockito.any(Student.class)))
				.thenReturn(mockStudent);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.put(uri)
										.content(mockstudentJson)
										.accept(MediaType.APPLICATION_JSON)
										.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	
	@Test
	public void changePasswordForUnknownStudent() throws Exception {
		String uri = "/students/11/change_password";
		Student mockStudent=new Student();
		mockStudent.setPassword("Password@123");
		ObjectMapper Obj = new ObjectMapper();  
		String mockstudentJson = Obj.writeValueAsString(mockStudent);
		Mockito.when(studentService.changePassword(Mockito.anyInt(),Mockito.any(Student.class)))
				.thenThrow(StudentNotFoundException.class);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.put(uri)
										.content(mockstudentJson)
										.accept(MediaType.APPLICATION_JSON)
										.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}
	
//	@Test
//	public void invalidPassword() throws Exception {
//		String uri = "/students/1/change_password";
//		Student mockStudent=new Student();
//		mockStudent.setPassword("pass");
//		ObjectMapper Obj = new ObjectMapper();  
//		String mockstudentJson = Obj.writeValueAsString(mockStudent);
//		RequestBuilder requestBuilder = MockMvcRequestBuilders
//										.put(uri)
//										.content(mockstudentJson)
//										.accept(MediaType.APPLICATION_JSON)
//										.contentType(MediaType.APPLICATION_JSON);
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//		MockHttpServletResponse response = result.getResponse();
//		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
//	}
	
}
