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
import com.springboot.mvc.security.login.service.TaskService;
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
@WebMvcTest(controllers = TaskController.class)

public class TaskControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TaskService TaskService;
	

	@MockBean
	private UserService UserService;
	
	
	private Project project1 = new Project(1,"P1", new Date(2018,01,01), new Date(2018,10,01), 20);
	private Project project2 = new Project(2,"P2", new Date(2018,01,11), new Date(2018,05,01), 18);
	private List<User> Users = new ArrayList<User>();
	private List<User> Users1 = new ArrayList<User>();
	private List<Task> tasks = new ArrayList<Task>();
	private List<Task> tasks1 = new ArrayList<Task>();

	
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
        
        tasks.add(task1);
        tasks.add(task2);
        
        Users.add(user1);
		Users.add(user2);
        
    
	 }
	 
	
	@Test
	public void shouldReturnHelloWorld() throws Exception {
		mockMvc.perform(get("/projectmanagement/api/task/home")).andExpect(content().string("Home"))
				.andExpect(status().is2xxSuccessful());
	}
	
	@Test
	public void shouldReturnAllTask() throws Exception {
		
        given(TaskService.findAll()).willReturn(tasks);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/task/")).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("[{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}},{\"taskid\":2,\"taskname\":\"Task 2\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":2,\"projectname\":\"P2\",\"startdate\":\"3918-02-11T05:00:00.000+0000\",\"enddate\":\"3918-06-01T04:00:00.000+0000\",\"priority\":18},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}]");
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldNotReturnAllTask() throws Exception {
		
        given(TaskService.findAll()).willReturn(tasks1);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/task/")).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"errorMessage\":\"No Task Pending Please Add Task\"}");
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldAddReturnTask() throws Exception {
		String ex = new String("{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}");
				given(TaskService.saveTask(Mockito.any(Task.class))).willReturn(task1);
        MvcResult result = mockMvc.perform(post("/projectmanagement/api/task/")
        		.accept(MediaType.APPLICATION_JSON)
        		.content(ex).contentType(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}");
	      

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldReturnTaskById() throws Exception {
		String ex = new String("{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}");
        
		given(TaskService.findTaskById(1)).willReturn(task1);
        
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/task/id/"+1)
        		.accept(MediaType.APPLICATION_JSON)
        		.content(ex).contentType(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}");

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldNotReturnTaskById() throws Exception {
		String ex = new String("{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}");
        
		given(TaskService.findTaskById(1)).willReturn(task1);
        
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/task/id/"+3)
        		.accept(MediaType.APPLICATION_JSON)
        		.content(ex).contentType(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"errorMessage\":\"No Task\"}");
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	
	@Test
	public void shouldReturnAllTasksortbystartdate() throws Exception {
		
        given(TaskService.sortTaskByStartDate(1)).willReturn(tasks);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/task/sortbystartdate/"+1)).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("[{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}},{\"taskid\":2,\"taskname\":\"Task 2\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":2,\"projectname\":\"P2\",\"startdate\":\"3918-02-11T05:00:00.000+0000\",\"enddate\":\"3918-06-01T04:00:00.000+0000\",\"priority\":18},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}]");
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldNotReturnAllTasksortbystartdate() throws Exception {
		
        given(TaskService.sortTaskByStartDate(1)).willReturn(tasks1);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/task/sortbystartdate/"+3)).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"errorMessage\":\"No Task for selected Project\"}");
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldReturnAllTasksortTaskByEndDate() throws Exception {
		
        given(TaskService.sortTaskByEndDate(1)).willReturn(tasks);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/task/sortbyenddate/"+1)).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("[{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}},{\"taskid\":2,\"taskname\":\"Task 2\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":2,\"projectname\":\"P2\",\"startdate\":\"3918-02-11T05:00:00.000+0000\",\"enddate\":\"3918-06-01T04:00:00.000+0000\",\"priority\":18},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}]");
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldNotReturnAllTasksortTaskByEndDate() throws Exception {
		
        given(TaskService.sortTaskByEndDate(1)).willReturn(tasks1);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/task/sortbyenddate/"+3)).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"errorMessage\":\"No Task for selected Project\"}");
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldReturnAllTasksortTaskByPriority() throws Exception {
		
        given(TaskService.sortTaskByPriority(1)).willReturn(tasks);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/task/sortbypriority/"+1)).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("[{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}},{\"taskid\":2,\"taskname\":\"Task 2\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":2,\"projectname\":\"P2\",\"startdate\":\"3918-02-11T05:00:00.000+0000\",\"enddate\":\"3918-06-01T04:00:00.000+0000\",\"priority\":18},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}]");
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldNotReturnAllTasksortTaskByPriority() throws Exception {
		
        given(TaskService.sortTaskByPriority(1)).willReturn(tasks1);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/task/sortbypriority/"+3)).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"errorMessage\":\"No Task for selected Project\"}");
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	
	@Test
	public void shouldReturnAllTasksortTaskByCompleted() throws Exception {
		
        given(TaskService.sortTaskByCompleted(1)).willReturn(tasks);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/task/sortbycompleted/"+1)).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("[{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}},{\"taskid\":2,\"taskname\":\"Task 2\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":2,\"projectname\":\"P2\",\"startdate\":\"3918-02-11T05:00:00.000+0000\",\"enddate\":\"3918-06-01T04:00:00.000+0000\",\"priority\":18},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}]");
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldNotReturnAllTasksortTaskByCompleted() throws Exception {
		
        given(TaskService.sortTaskByCompleted(1)).willReturn(tasks1);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/task/sortbycompleted/"+3)).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"errorMessage\":\"No Task for selected Project\"}");
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	/*
	@Test
	public void shouldNotReturnAllUser() throws Exception {
		
        given(TaskService.findAll()).willReturn(Users1);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/user/")).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"errorMessage\":\"Empty users\"}");
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	
	@Test
	public void shouldAddReturnUser() throws Exception {
		String ex = new String("{\"userid\":1,\"firstname\":\"Sam\",\"lastname\":\"John\",\"employeeid\":101,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"task\":{\"taskid\":1,\"taskname\":\"Task 1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-03-03T05:00:00.000+0000\",\"priority\":10,\"active\":1,\"project\":{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},\"parenttask\":{\"parentid\":0,\"parenttask\":null}}}");
        given(TaskService.saveUser(Mockito.any(User.class))).willReturn(user1);
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
        
		given(TaskService.findUserById(1)).willReturn(user1);
        given(TaskService.saveUser(Mockito.any(User.class))).willReturn(user1);
        
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
        
		given(TaskService.findUserById(1)).willReturn(user1);
        given(TaskService.saveUser(Mockito.any(User.class))).willReturn(user1);
        
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
        
		given(TaskService.findUserById(1)).willReturn(user1);
        
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
        
		given(TaskService.findUserById(1)).willReturn(user1);
        
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
		given(TaskService.findUserByEmployeeid(101)).willReturn(userOne);
        
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
        
		given(TaskService.findUserById(1)).willReturn(user1);
        
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
		
		given(TaskService.findUserByTaskid(1)).willReturn(user1);
        
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
        
		given(TaskService.findUserByTaskid(1)).willReturn(user1);
        
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
		given(TaskService.sortUserByEmployeeid()).willReturn(userOne);
        
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

		given(TaskService.sortUserByEmployeeid()).willReturn(userOne);
        
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
		given(TaskService.sortUserByFirstname()).willReturn(userOne);
        
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

		given(TaskService.sortUserByFirstname()).willReturn(userOne);
        
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
		given(TaskService.sortUserByLastname()).willReturn(userOne);
        
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

		given(TaskService.sortUserByLastname()).willReturn(userOne);
        
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
        
		given(TaskService.findUserById(1)).willReturn(user1);
        given(TaskService.deleteUser(1)).willReturn(true);
        
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
        
		given(TaskService.findUserById(1)).willReturn(user1);
        given(TaskService.deleteUser(1)).willReturn(true);
        
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
        
		given(TaskService.updateUserByProejctAndTask(1)).willReturn(1);
        given(TaskService.saveUser(Mockito.any(User.class))).willReturn(user1);
        
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
        
		given(TaskService.updateUserByProejctAndTask(1)).willReturn(1);
        given(TaskService.saveUser(Mockito.any(User.class))).willReturn(user1);
        
        MvcResult result = mockMvc.perform(put("/projectmanagement/api/user/project/"+3)
        		.accept(MediaType.APPLICATION_JSON)
        		.content(ex).contentType(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"errorMessage\":\"No User\"}");

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}*/
	


}
