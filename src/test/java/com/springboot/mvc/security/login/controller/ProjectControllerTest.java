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
import com.springboot.mvc.security.login.model.Project;
import com.springboot.mvc.security.login.model.ProjectDetails;
import com.springboot.mvc.security.login.model.Task;
import com.springboot.mvc.security.login.model.User;
import com.springboot.mvc.security.login.service.ProjectDetailsService;
import com.springboot.mvc.security.login.service.ProjectService;

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
@WebMvcTest(controllers = ProjectController.class)

public class ProjectControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProjectService projectService;
	
	@MockBean
	private ProjectDetailsService projectDetailsService;
	
	private Project project1 = new Project(1,"P1", new Date(2018,01,01), new Date(2018,10,01), 20);
	private Project project2 = new Project(2,"P2", new Date(2018,01,11), new Date(2018,05,01), 18);
	private List<Project> projects = new ArrayList<Project>();
	private List<Project> projects1 = new ArrayList<Project>();
	
	private Task task1;
	private Task task2;
	
	private User user1;
	private User user2;
	
	ProjectDetails projectDetails1;
	ProjectDetails projectDetails2;
	List<ProjectDetails> projectDetailsList;
	List<ProjectDetails> projectDetailsList1;




	
	@Before public void setUp() throws Exception { 
		MockitoAnnotations.initMocks(this);	
		project1 = new Project(1,"P1", new Date(2018,01,01), new Date(2018,10,01), 20);
		project2 = new Project(2,"P2", new Date(2018,01,11), new Date(2018,05,01), 18);
		projects = new ArrayList<Project>();
		projects1 = new ArrayList<Project>();
		projects.add(project1);
		projects.add(project2);
		task1 = new Task(1,"Task 1", new Date(2018,01,01), new Date(2018,01,31), 10,1, project1, new ParentTask());
		task2 = new Task(2,"Task 2", new Date(2018,01,01), new Date(2018,01,31), 10,1, project2, new ParentTask());
        user1 = new User(1,"Sam", "John",101, project1, task1);
        user2 = new User(2,"Ram", "Bose",101, project2, task2);
        
        projectDetails1 = new ProjectDetails(project1, 1, "N", user1); 
        projectDetails2 = new ProjectDetails(project2, 1, "N", user2); 
        projectDetailsList = new ArrayList<ProjectDetails>();
        projectDetailsList1 = new ArrayList<ProjectDetails>();

        projectDetailsList.add(projectDetails1);
        projectDetailsList.add(projectDetails2);
	 }
	 
	
	@Test
	public void shouldReturnHelloWorld() throws Exception {
		mockMvc.perform(get("/projectmanagement/api/project/home")).andExpect(content().string("Home"))
				.andExpect(status().is2xxSuccessful());
	}
	
	@Test
	public void shouldReturnAllproject() throws Exception {
		
        given(projectService.findAll()).willReturn(projects);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/project/")).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("[{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20},{\"projectid\":2,\"projectname\":\"P2\",\"startdate\":\"3918-02-11T05:00:00.000+0000\",\"enddate\":\"3918-06-01T04:00:00.000+0000\",\"priority\":18}]");
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldNotReturnAllproject() throws Exception {
		
        given(projectService.findAll()).willReturn(projects1);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/project/")).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"errorMessage\":\"No Project Defined\"}");
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldAddReturnproject() throws Exception {
		String ex = new String("{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20}");

        given(projectService.saveProject(Mockito.any(Project.class))).willReturn(project1);
        MvcResult result = mockMvc.perform(post("/projectmanagement/api/project/")
        		.accept(MediaType.APPLICATION_JSON)
        		.content(ex).contentType(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20}");


		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	
	
	@Test
	public void shouldUpdateReturnproject() throws Exception {
		String ex = new String("{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20}");
        
		given(projectService.findProjectById(1)).willReturn(project1);
        given(projectService.saveProject(Mockito.any(Project.class))).willReturn(project1);
        
        MvcResult result = mockMvc.perform(put("/projectmanagement/api/project/id/"+1)
        		.accept(MediaType.APPLICATION_JSON)
        		.content(ex).contentType(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20}");

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldNotUpdateReturnproject() throws Exception {
		String ex = new String("{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20}");
        
		given(projectService.findProjectById(1)).willReturn(project1);
        given(projectService.saveProject(Mockito.any(Project.class))).willReturn(project1);
        
        MvcResult result = mockMvc.perform(put("/projectmanagement/api/project/id/"+3)
        		.accept(MediaType.APPLICATION_JSON)
        		.content(ex).contentType(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"errorMessage\":\"No Project\"}");

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	
	@Test
	public void shouldDeleteReturnproject() throws Exception {
		String ex = new String("{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20}");
        
		given(projectService.findProjectById(1)).willReturn(project1);
        given(projectService.deleteProject(1)).willReturn(true);
        
        MvcResult result = mockMvc.perform(delete("/projectmanagement/api/project/id/"+1)
        		.accept(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20}");

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldNotDeleteReturnproject() throws Exception {
		String ex = new String("{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20}");
        
		given(projectService.findProjectById(1)).willReturn(project1);
        given(projectService.deleteProject(1)).willReturn(true);
        
        MvcResult result = mockMvc.perform(delete("/projectmanagement/api/project/id/"+3)
        		.accept(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"errorMessage\":\"No Project\"}");

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	
	
	@Test
	public void shouldReturnproject() throws Exception {
		
        given(projectService.findProjectById(1)).willReturn(project1);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/project/id/"+1)).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20}");
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldNotReturnproject() throws Exception {
		
        given(projectService.findProjectById(1)).willReturn(project1);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/project/id/"+3)).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"errorMessage\":\"No Project\"}");
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldReturnprojectByProjectName() throws Exception {
		
        given(projectService.findProjectByProjectname("P1")).willReturn(project1);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/project/projectname/"+"P1")).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"projectid\":1,\"projectname\":\"P1\",\"startdate\":\"3918-02-01T05:00:00.000+0000\",\"enddate\":\"3918-11-01T04:00:00.000+0000\",\"priority\":20}");
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldNotReturnprojectByProjectName() throws Exception {
		
        given(projectService.findProjectByProjectname("P1")).willReturn(project1);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/project/projectname/"+"P3")).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"errorMessage\":\"No Project with this Project Name\"}");
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldReturnProjectDetails() throws Exception {
		
        given(projectDetailsService.findAll()).willReturn(projectDetailsList);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/project/getall/")).andReturn();
		assertTrue(result != null);
	}
	
	@Test
	public void shouldNotReturnProjectDetails() throws Exception {
		
        given(projectDetailsService.findAll()).willReturn(projectDetailsList1);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/project/getall/")).andReturn();
		assertTrue(result != null);
	}
	
	@Test
	public void shouldReturnsortbystartdate() throws Exception {
		
        given(projectDetailsService.sortProjectByStartDate()).willReturn(projectDetailsList);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/project/sortbystartdate/")).andReturn();
		assertTrue(result != null);
	}
	
	@Test
	public void shouldNotReturnsortbystartdate() throws Exception {
		
        given(projectDetailsService.sortProjectByStartDate()).willReturn(projectDetailsList1);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/project/sortbystartdate/")).andReturn();
		assertTrue(result != null);
	}
	
	@Test
	public void shouldReturnsortProjectByEndDate() throws Exception {
		
        given(projectDetailsService.sortProjectByEndDate()).willReturn(projectDetailsList);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/project/sortbyenddate/")).andReturn();
		assertTrue(result != null);
	}
	
	@Test
	public void shouldNotReturnsortProjectByEndDate() throws Exception {
		
        given(projectDetailsService.sortProjectByEndDate()).willReturn(projectDetailsList1);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/project/sortbyenddate/")).andReturn();
		assertTrue(result != null);
	}
	
	@Test
	public void shouldReturnsortProjectByPriority() throws Exception {
		
        given(projectDetailsService.sortProjectByPriority()).willReturn(projectDetailsList);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/project/sortbypriority/")).andReturn();
		assertTrue(result != null);
	}
	
	@Test
	public void shouldNotReturnsortProjectByPriority() throws Exception {
		
        given(projectDetailsService.sortProjectByPriority()).willReturn(projectDetailsList1);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/project/sortbypriority/")).andReturn();
		assertTrue(result != null);
	}
	
	@Test
	public void shouldReturnsortProjectByCompleted() throws Exception {
		
        given(projectDetailsService.sortProjectByCompleted()).willReturn(projectDetailsList);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/project/sortbycompleted/")).andReturn();
		assertTrue(result != null);
	}
	
	@Test
	public void shouldNotReturnsortProjectByCompleted() throws Exception {
		
        given(projectDetailsService.sortProjectByCompleted()).willReturn(projectDetailsList1);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/project/sortbycompleted/")).andReturn();
		assertTrue(result != null);
	}

}
