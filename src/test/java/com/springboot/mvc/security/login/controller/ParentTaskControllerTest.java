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
import com.springboot.mvc.security.login.service.ParentTaskService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ParentTaskController.class)

public class ParentTaskControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ParentTaskService parentTaskService;
	
	private ParentTask parenttask1 = new ParentTask(1,"P1");
	private ParentTask parenttask2 = new ParentTask(2,"P2");
	private List<ParentTask> parenttasks = new ArrayList<ParentTask>();
	private List<ParentTask> parenttasks1 = new ArrayList<ParentTask>();

	
	@Before public void setUp() throws Exception { 
		MockitoAnnotations.initMocks(this);
		parenttask1 = new ParentTask(1,"P1");
		parenttask2 = new ParentTask(2,"P2");
		parenttasks = new ArrayList<ParentTask>();
		parenttasks.add(parenttask1);
		parenttasks.add(parenttask2);

	 }
	 
	
	@Test
	public void shouldReturnHelloWorld() throws Exception {
		mockMvc.perform(get("/projectmanagement/api/parenttask/home")).andExpect(content().string("Home"))
				.andExpect(status().is2xxSuccessful());
	}
	
	@Test
	public void shouldReturnAllParentTask() throws Exception {
		
        given(parentTaskService.findAllParentTask()).willReturn(parenttasks);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/parenttask/")).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("[{\"parentid\":1,\"parenttask\":\"P1\"},{\"parentid\":2,\"parenttask\":\"P2\"}]");
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldNotReturnAllParentTask() throws Exception {
		
        given(parentTaskService.findAllParentTask()).willReturn(parenttasks1);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/parenttask/")).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"errorMessage\":\"Empty Parent Task\"}");
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	@Test
	public void shouldAddReturnParentTask() throws Exception {
		String ex = new String("{\"parentid\":1,\"parenttask\":\"P1\"}");

        given(parentTaskService.saveParentTask(Mockito.any(ParentTask.class))).willReturn(parenttask1);
        MvcResult result = mockMvc.perform(post("/projectmanagement/api/parenttask/")
        		.accept(MediaType.APPLICATION_JSON)
        		.content(ex).contentType(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"parentid\":1,\"parenttask\":\"P1\"}");

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldUpdateReturnParentTask() throws Exception {
		String ex = new String("{\"parentid\":1,\"parenttask\":\"P1\"}");
        
		given(parentTaskService.findParentTaskByid(1)).willReturn(parenttask1);
        given(parentTaskService.saveParentTask(Mockito.any(ParentTask.class))).willReturn(parenttask1);
        
        MvcResult result = mockMvc.perform(put("/projectmanagement/api/parenttask/id/"+1)
        		.accept(MediaType.APPLICATION_JSON)
        		.content(ex).contentType(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"parentid\":1,\"parenttask\":\"P1\"}");

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldNotUpdateReturnParentTask() throws Exception {
		String ex = new String("{\"parentid\":1,\"parenttask\":\"P1\"}");
        
		given(parentTaskService.findParentTaskByid(1)).willReturn(parenttask1);
        given(parentTaskService.saveParentTask(Mockito.any(ParentTask.class))).willReturn(parenttask1);
        
        MvcResult result = mockMvc.perform(put("/projectmanagement/api/parenttask/id/"+3)
        		.accept(MediaType.APPLICATION_JSON)
        		.content(ex).contentType(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"errorMessage\":\"No Parent Task\"}");

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldDeleteReturnParentTask() throws Exception {
		String ex = new String("{\"parentid\":1,\"parenttask\":\"P1\"}");
        
		given(parentTaskService.findParentTaskByid(1)).willReturn(parenttask1);
        given(parentTaskService.deleteParentTask(1)).willReturn(true);
        
        MvcResult result = mockMvc.perform(delete("/projectmanagement/api/parenttask/id/"+1)
        		.accept(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"parentid\":1,\"parenttask\":\"P1\"}");

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldNotDeleteReturnParentTask() throws Exception {
		String ex = new String("{\"parentid\":1,\"parenttask\":\"P1\"}");
        
		given(parentTaskService.findParentTaskByid(1)).willReturn(parenttask1);
        given(parentTaskService.deleteParentTask(1)).willReturn(true);
        
        MvcResult result = mockMvc.perform(delete("/projectmanagement/api/parenttask/id/"+3)
        		.accept(MediaType.APPLICATION_JSON))
        		.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"errorMessage\":\"No Parent Task\"}");

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldReturnParentTask() throws Exception {
		
        given(parentTaskService.findParentTaskByid(1)).willReturn(parenttask1);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/parenttask/id/"+1)).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"parentid\":1,\"parenttask\":\"P1\"}");
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
	
	@Test
	public void shouldNotReturnParentTask() throws Exception {
		
        given(parentTaskService.findParentTaskByid(1)).willReturn(parenttask1);
        MvcResult result = mockMvc.perform(get("/projectmanagement/api/parenttask/id/"+3)).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = new String("{\"errorMessage\":\"No Parent Task\"}");
		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}
}
