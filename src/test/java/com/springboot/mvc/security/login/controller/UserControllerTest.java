package com.springboot.mvc.security.login.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.springboot.mvc.security.login.model.ParentTask;
import com.springboot.mvc.security.login.model.User;
import com.springboot.mvc.security.login.model.Task;
import com.springboot.mvc.security.login.model.Project;
import com.springboot.mvc.security.login.service.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class)

public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService UserService;
	
	
	
	private Project project1 = new Project(1,"P1", new Date(2018,01,01), new Date(2018,10,01), 20);
	private Project project2 = new Project(2,"P2", new Date(2018,01,11), new Date(2018,05,01), 18);
	private List<User> Users = new ArrayList<User>();
	private List<User> Users1 = new ArrayList<User>();
	
	private Task task1;
	private Task task2;
	
	private User user1;
	private User user2;
	
	
	@Before public void setUp() throws Exception { 
		MockitoAnnotations.initMocks(this);	
		project1 = new Project(1,"P1", new Date(2018,01,01), new Date(2018,10,01), 20);
		project2 = new Project(2,"P2", new Date(2018,01,11), new Date(2018,05,01), 18);
		Users = new ArrayList<User>();
		Users1 = new ArrayList<User>();
		
		task1 = new Task(1,"Task 1", new Date(2018,01,01), new Date(2018,01,31), 10,1, project1, new ParentTask());
		task2 = new Task(2,"Task 2", new Date(2018,01,01), new Date(2018,01,31), 10,1, project2, new ParentTask());
        user1 = new User(1,"Sam", "John",101, project1, task1);
        user2 = new User(2,"Ram", "Bose",101, project2, task2);
        
        Users.add(user1);
		Users.add(user2);
        
    
	 }
	 
	
	@Test
	public void shouldReturnHelloWorld() throws Exception {
		mockMvc.perform(get("/projectmanagement/api/user/home")).andExpect(content().string("Home"))
				.andExpect(status().is2xxSuccessful());
	}
	
	@Test
	public void shouldReturnAllUser() throws Exception {
		
        given(UserService.findAll()).willReturn(Users);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/user/")).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("[{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}},{\"userid\":2,\"firstname\":\"Ram\",\"lastname\":\"Bose\",\"employeeid\":101,\"project\":{\"projectid\":2,\"projectname\":\"P2\",\"startdate\":\"3918-02-11T05:00:00.000+0000\",\"enddate\":\"3918-06-01T04:00:00.000+0000\",\"priority\":18},\"task\":{\"taskid\":2,\"taskname\":\"Task 2\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":2,\"projectname\":\"P2\",\"startdate\":\"3918-02-11T05:00:00.000+0000\",\"enddate\":\"3918-06-01T04:00:00.000+0000\",\"priority\":18},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}]") ;
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldNotReturnAllUser() throws Exception {
		
        given(UserService.findAll()).willReturn(Users1);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/user/")).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"errorMessage\":\"Empty users\"}");
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	
	@Test
	public void shouldAddReturnUser() throws Exception {
		String ex = new String("{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}");
        given(UserService.saveUser(Mockito.any(User.class))).willReturn(user1);
        MvcResult result = mockMvc.perform(post("/projectmanagement/api/user/")
        		.accept(MediaType.APPLICATION_JSON)
        		.content(ex).contentType(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}");
	      

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	
	
	@Test
	public void shouldUpdateReturnUser() throws Exception {
		String ex = new String("{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}");
        
		given(UserService.findUserById(1)).willReturn(user1);
        given(UserService.saveUser(Mockito.any(User.class))).willReturn(user1);
        
        MvcResult result = mockMvc.perform(put("/projectmanagement/api/user/id/"+1)
        		.accept(MediaType.APPLICATION_JSON)
        		.content(ex).contentType(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}");

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	
	
	@Test
	public void shouldNotUpdateReturnUser() throws Exception {
		String ex = new String("{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}");
        
		given(UserService.findUserById(1)).willReturn(user1);
        given(UserService.saveUser(Mockito.any(User.class))).willReturn(user1);
        
        MvcResult result = mockMvc.perform(put("/projectmanagement/api/user/id/"+3)
        		.accept(MediaType.APPLICATION_JSON)
        		.content(ex).contentType(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"errorMessage\":\"No User\"}");

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	
	@Test
	public void shouldReturnUserById() throws Exception {
		String ex = new String("{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}");
        
		given(UserService.findUserById(1)).willReturn(user1);
        
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/user/id/"+1)
        		.accept(MediaType.APPLICATION_JSON)
        		.content(ex).contentType(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}");

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	

	@Test
	public void shouldNotReturnUserById() throws Exception {
		String ex = new String("{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}");
        
		given(UserService.findUserById(1)).willReturn(user1);
        
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/user/id/"+3)
        		.accept(MediaType.APPLICATION_JSON)
        		.content(ex).contentType(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"errorMessage\":\"No User\"}");

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldReturnUserByEmpId() throws Exception {
		String ex = new String("[{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}]");
        List <User> userOne = new ArrayList<User>();
        userOne.add(user1);
		given(UserService.findUserByEmployeeid(101)).willReturn(userOne);
        
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/user/employeeid/"+101)
        		.accept(MediaType.APPLICATION_JSON)
        		.content(ex).contentType(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("[{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}]");

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	

	@Test
	public void shouldNotReturnUserByEmpId() throws Exception {
		String ex = new String("[{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}]");
        
		given(UserService.findUserById(1)).willReturn(user1);
        
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/user/employeeid/"+103)
        		.accept(MediaType.APPLICATION_JSON)
        		.content(ex).contentType(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"errorMessage\":\"No User with this Employee ID\"}");

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldReturnUserByTaskId() throws Exception {
		
		given(UserService.findUserByTaskid(1)).willReturn(user1);
        
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/user/task/"+1)
        		.accept(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}");

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	

	@Test
	public void shouldNotReturnUserByTaskId() throws Exception {
        
		given(UserService.findUserByTaskid(1)).willReturn(user1);
        
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/user/task/"+3)
        		.accept(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"errorMessage\":\"No User\"}");

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldReturnUserBysortbyemployeeid() throws Exception {
		String ex = new String("[{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}]");
        List <User> userOne = new ArrayList<User>();
        userOne.add(user1);
		given(UserService.sortUserByEmployeeid()).willReturn(userOne);
        
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/user/sortbyemployeeid/")
        		.accept(MediaType.APPLICATION_JSON)
        		.content(ex).contentType(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("[{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}]");

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	

	@Test
	public void shouldNotReturnUserBysortbyemployeeid() throws Exception {
		String ex = new String("[{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}]");
        List <User> userOne = new ArrayList<User>();

		given(UserService.sortUserByEmployeeid()).willReturn(userOne);
        
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/user/sortbyemployeeid/")
        		.accept(MediaType.APPLICATION_JSON)
        		.content(ex).contentType(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"errorMessage\":\"Empty users\"}");

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldReturnUserBysortbyfirstname() throws Exception {
		String ex = new String("[{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}]");
        List <User> userOne = new ArrayList<User>();
        userOne.add(user1);
		given(UserService.sortUserByFirstname()).willReturn(userOne);
        
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/user/sortbyfirstname/")
        		.accept(MediaType.APPLICATION_JSON)
        		.content(ex).contentType(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("[{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}]");

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	

	@Test
	public void shouldNotReturnUserBysortbyfirstname() throws Exception {
		String ex = new String("[{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}]");
        List <User> userOne = new ArrayList<User>();

		given(UserService.sortUserByFirstname()).willReturn(userOne);
        
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/user/sortbyfirstname/")
        		.accept(MediaType.APPLICATION_JSON)
        		.content(ex).contentType(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"errorMessage\":\"Empty users\"}");

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldReturnUserBysortbylastname() throws Exception {
		String ex = new String("[{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}]");
        List <User> userOne = new ArrayList<User>();
        userOne.add(user1);
		given(UserService.sortUserByLastname()).willReturn(userOne);
        
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/user/sortbylastname/")
        		.accept(MediaType.APPLICATION_JSON)
        		.content(ex).contentType(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("[{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}]");

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	

	@Test
	public void shouldNotReturnUserBysortbylastname() throws Exception {
		String ex = new String("[{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}]");
        List <User> userOne = new ArrayList<User>();

		given(UserService.sortUserByLastname()).willReturn(userOne);
        
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/user/sortbylastname/")
        		.accept(MediaType.APPLICATION_JSON)
        		.content(ex).contentType(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"errorMessage\":\"Empty users\"}");

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldDeleteReturnUser() throws Exception {
		String ex = new String("{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}");
        
		given(UserService.findUserById(1)).willReturn(user1);
        given(UserService.deleteUser(1)).willReturn(true);
        
        MvcResult result = mockMvc.perform(delete("/projectmanagement/api/user/id/"+1)
        		.accept(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}");

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldNotDeleteReturnUser() throws Exception {
		String ex = new String("{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}");
        
		given(UserService.findUserById(1)).willReturn(user1);
        given(UserService.deleteUser(1)).willReturn(true);
        
        MvcResult result = mockMvc.perform(delete("/projectmanagement/api/user/id/"+3)
        		.accept(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"errorMessage\":\"No User\"}");

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	
	
	@Test
	public void shouldUpdateByprojectReturnUser() throws Exception {
		String ex = new String("{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}");
        
		given(UserService.updateUserByProejctAndTask(1)).willReturn(1);
        given(UserService.saveUser(Mockito.any(User.class))).willReturn(user1);
        
        MvcResult result = mockMvc.perform(put("/projectmanagement/api/user/project/"+1)
        		.accept(MediaType.APPLICATION_JSON)
        		.content(ex).contentType(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}");


	}
	
	
	
	@Test
	public void shouldNotByprojectUpdateReturnUser() throws Exception {
		String ex = new String("{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}");
        
		given(UserService.updateUserByProejctAndTask(1)).willReturn(1);
        given(UserService.saveUser(Mockito.any(User.class))).willReturn(user1);
        
        MvcResult result = mockMvc.perform(put("/projectmanagement/api/user/project/"+3)
        		.accept(MediaType.APPLICATION_JSON)
        		.content(ex).contentType(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"errorMessage\":\"No User\"}");

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	
}
