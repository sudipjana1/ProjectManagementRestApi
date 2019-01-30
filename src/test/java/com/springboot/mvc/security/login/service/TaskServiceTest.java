package com.springboot.mvc.security.login.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.springboot.mvc.security.login.LoginApplicationTests;
import com.springboot.mvc.security.login.model.ParentTask;
import com.springboot.mvc.security.login.model.Project;
import com.springboot.mvc.security.login.model.Task;
import com.springboot.mvc.security.login.model.Task;
import com.springboot.mvc.security.login.repository.ParentTaskRepo;
import com.springboot.mvc.security.login.repository.ProjectRepo;
import com.springboot.mvc.security.login.repository.TaskRepo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TaskServiceTest extends LoginApplicationTests{
	
	@InjectMocks
    private TaskServiceImpl TaskService;
	@Mock 
	private TaskRepo TaskRepo;
    private Task Task;
    private List<Task> Tasklist;
    Optional<Task> TaskOpt;
	private int Taskid = 1;
	
	@Mock 
	private ProjectRepo projectrepo;
    private Project project;
    Optional<Project> projectOpt;
	private long projectid = 1;


    @Before
    public void setUp() {
		MockitoAnnotations.initMocks(this);

		Task = new Task(1,"Task 1", new Date(2018,01,01), new Date(2018,01,31), 10,1, new Project(), new ParentTask());
        
		TaskOpt = Optional.of(Task);
		Tasklist = new ArrayList<Task>();
		Tasklist.add(Task);
		
		project = new Project(1,"P1", new Date(2018,01,01), new Date(2018,10,01), 20);
        
		projectOpt = Optional.of(project);
		
		Mockito.when(projectrepo.findById(any()))
        .thenReturn(projectOpt);
		
        Mockito.when(TaskRepo.save(any()))
        .thenReturn(Task);
        Mockito.when(TaskRepo.findById(any()))
        .thenReturn(TaskOpt);
        Mockito.when(TaskRepo.findAllByTaskname(any()))
        .thenReturn(Tasklist);
        Mockito.when(TaskRepo.existsById(any()))
        .thenReturn(true);
        Mockito.when(TaskRepo.findAll())
        .thenReturn(Tasklist);
        
        
        
        TaskService.saveTask(Task);

    }

    @Test
    public void testfindTaskById() {
        // Run the test
        final Task result = TaskService.findTaskById(Taskid);
        // Verify the results
        assertEquals(1,result.getTaskid());
    }
    
    @Test
    public void testsaveTask() {
        // Setup
    		Task Task1 = new Task(2,"Task 2", new Date(2018,01,01), new Date(2018,01,31), 10,1, new Project(), new ParentTask());

        // Run the test
        final Task result = TaskService.saveTask(Task1);
        // Verify the results
        assertTrue(result != null);
    }
    
    @Test
    public void testfindAllByTaskname() {
        // Run the test
        final List<Task> result = TaskService.findAllByTaskname("Task 1");
        // Verify the results
        assertEquals(1,result.get(0).getTaskid());
    }
    
    @Test
    public void testfindAll() {
    		final List<Task> Tasks = TaskService.findAll();
    		assertTrue(Tasks != null);
    }
    
    @Test
    public void testsortTaskByStartDate() {
    		final List<Task> Tasks = TaskService.sortTaskByStartDate(1);
    		assertTrue(Tasks != null);
    }
    
    @Test
    public void testsortTaskByEndDate() {
    		final List<Task> Tasks = TaskService.sortTaskByEndDate(1);
    		assertTrue(Tasks != null);
    }
    
    @Test
    public void testsortTaskByPriority() {
    		final List<Task> Tasks = TaskService.sortTaskByPriority(1);
    		assertTrue(Tasks != null);
    }
    
    @Test
    public void testsortTaskByCompleted() {
    		final List<Task> Tasks = TaskService.sortTaskByCompleted(1);
    		assertTrue(Tasks != null);
    }
    
    @Test
    public void testfindAllByProjectid() {
    		final List<Task> Tasks = TaskService.findAllByProjectid(1);
    		assertTrue(Tasks != null);
    }
    
    
    @Test
    public void testdeleteTask() {
    		assertTrue(TaskService.deleteTask(Taskid));
    }


}
