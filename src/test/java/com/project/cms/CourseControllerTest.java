package com.project.cms;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.cms.controller.CourseController;
import com.project.cms.exception.CourseNotFoundException;
import com.project.cms.exception.LecturerNotFoundException;
import com.project.cms.model.Course;
import com.project.cms.model.Lecturer;
import com.project.cms.model.Student;
import com.project.cms.service.CourseService;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;


@RunWith(SpringRunner.class)
@WebMvcTest(CourseController.class)
public class CourseControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private CourseService courseService;

	@Test
	public void addCourse() throws Exception {
		String uri = "/course?lecturer_id=1";
		Course mockCourse=new Course();
		mockCourse.setId(11);
		mockCourse.setCourse_name("unit test");
		mockCourse.setDescription("course about unit testing");
		ObjectMapper Obj = new ObjectMapper();  
		String mockCourseJson = Obj.writeValueAsString(mockCourse);  
		Mockito.when(courseService.addCourse(Mockito.any(Course.class),Mockito.anyInt()))
				.thenReturn(mockCourse);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.post(uri)
										.accept(MediaType.APPLICATION_JSON)
										.content(mockCourseJson)
										.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertEquals("http://localhost/course/11", 
				response.getHeader(HttpHeaders.LOCATION));
		
	}
	
	@Test
	public void addCourseWithUnknownLecturer() throws Exception {
		String uri = "/course?lecturer_id=13";
		Course mockCourse=new Course();
		mockCourse.setId(11);
		mockCourse.setCourse_name("unit test");
		mockCourse.setDescription("course about unit testing");
		ObjectMapper Obj = new ObjectMapper();  
		String mockCourseJson = Obj.writeValueAsString(mockCourse);  
		Mockito.when(courseService.addCourse(Mockito.any(Course.class),Mockito.anyInt()))
				.thenThrow(LecturerNotFoundException.class);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.post(uri)
										.accept(MediaType.APPLICATION_JSON)
										.content(mockCourseJson)
										.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
		
	}
	
	@Test
	public void addCourseWithoutName() throws Exception {
		String uri = "/course?lecturer_id=1";
		Course mockCourse=new Course();
//		mockCourse.setCourse_name("");
		mockCourse.setDescription("course about unit testing");
		ObjectMapper Obj = new ObjectMapper();  
		String mockCourseJson = Obj.writeValueAsString(mockCourse);  
//		Mockito.when(courseService.addCourse(Mockito.any(Course.class),Mockito.anyInt()))
//				.thenReturn(mockCourse);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.post(uri)
										.accept(MediaType.APPLICATION_JSON)
										.content(mockCourseJson)
										.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
	}
	
	@Test
	public void searchCourses() throws Exception {
		String uri = "/course/search?searchterm=test";
		Course mockCourse1=new Course();
		mockCourse1.setId(11);
		mockCourse1.setCourse_name("unit test");
		mockCourse1.setDescription("course about unit testing");
		Course mockCourse2=new Course();
		mockCourse2.setId(12);
		mockCourse2.setCourse_name("Integration test");
		mockCourse2.setDescription("course about Integration testing");
		List<Course> mockCourses = new ArrayList<Course>();
		mockCourses.add(mockCourse1);
		mockCourses.add(mockCourse2);
		ObjectMapper Obj = new ObjectMapper();  
		String mockCoursesJson = Obj.writeValueAsString(mockCourses);  
		Mockito.when(courseService.searchCourses(Mockito.anyString()))
				.thenReturn( mockCourses);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.get(uri)
										.accept(MediaType.APPLICATION_JSON)
										.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(mockCoursesJson, result.getResponse()
				.getContentAsString(), false);
	}
	
	
	@Test
	public void getSubscribedStudents() throws Exception {
		String uri = "/course/15/students_contacts";
		Student mockStudent=new Student();
		mockStudent.setId(1);
		mockStudent.setName("bala");
		mockStudent.setEmail("bala@gmail.com");
		mockStudent.setPhone("3445334543");
		Set<Student> mockStudents=new HashSet<Student>();
		mockStudents.add(mockStudent);
		ObjectMapper Obj = new ObjectMapper();  
		String mockStudentsJson = Obj.writeValueAsString(mockStudents);  
		Mockito.when(courseService.getSubscribedStudents(Mockito.anyInt()))
				.thenReturn(mockStudents);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.get(uri)
										.accept(MediaType.APPLICATION_JSON)
										.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(mockStudentsJson, result.getResponse()
				.getContentAsString(), false);
	}
	
	@Test
	public void getSubscribedStudentsForUnknownCourse() throws Exception {
		String uri = "/course/15/students_contacts";
		Mockito.when(courseService.getSubscribedStudents(Mockito.anyInt()))
				.thenThrow(CourseNotFoundException.class);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.get(uri)
										.accept(MediaType.APPLICATION_JSON)
										.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}
	
	@Test
	public void getCourseLecturer() throws Exception {
		String uri = "/course/15/lecturer_contacts";
		Lecturer mockLecturer=new Lecturer();
		mockLecturer.setId(1);
		mockLecturer.setName("bala");
		mockLecturer.setEmail("bala@gmail.com");
		mockLecturer.setPhone("3445334543");
		ObjectMapper Obj = new ObjectMapper();  
		String mockLecturerJson = Obj.writeValueAsString(mockLecturer);  
		Mockito.when(courseService.getCourseLecturer(Mockito.anyInt()))
				.thenReturn(mockLecturer);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.get(uri)
										.accept(MediaType.APPLICATION_JSON)
										.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(mockLecturerJson, result.getResponse()
				.getContentAsString(), false);
	}
	
	@Test
	public void getLecturerForUnknownCourse() throws Exception {
		String uri = "/course/15/lecturer_contacts";
		Mockito.when(courseService.getCourseLecturer(Mockito.anyInt()))
				.thenThrow(CourseNotFoundException.class);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.get(uri)
										.accept(MediaType.APPLICATION_JSON)
										.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}
	
}
