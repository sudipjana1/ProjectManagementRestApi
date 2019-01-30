package com.springboot.mvc.security.login.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import com.springboot.mvc.security.login.LoginApplicationTests;
import com.springboot.mvc.security.login.model.ParentTask;
import com.springboot.mvc.security.login.model.Project;
import com.springboot.mvc.security.login.model.Task;
import com.springboot.mvc.security.login.model.User;
import com.springboot.mvc.security.login.model.ProjectDetails;
import com.springboot.mvc.security.login.repository.ParentTaskRepo;
import com.springboot.mvc.security.login.repository.ProjectRepo;
import com.springboot.mvc.security.login.repository.TaskRepo;
import com.springboot.mvc.security.login.repository.UserRepo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ProjectDetailsServiceTest extends LoginApplicationTests{
	
	@InjectMocks
    private ProjectDetailsServiceImpl ProjectDetailsService;
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
    private List<Project> projects;
	private long projectid = 1;
	
	@Mock 
	private UserRepo userRepo;
    private User User;
    private List<User> userlist;
    Optional<User> UserOpt;
	private int Userid = 1;
	
	ProjectDetails ProjectDetails;


    @Before
    public void setUp() {
		MockitoAnnotations.initMocks(this);

		Task = new Task(1,"Task 1", new Date(2018,01,01), new Date(2018,01,31), 10,1, new Project(), new ParentTask());
        
		TaskOpt = Optional.of(Task);
		Tasklist = new ArrayList<Task>();
		Tasklist.add(Task);
		
		project = new Project(1,"P1", new Date(2018,01,01), new Date(2018,10,01), 20);
        
		projectOpt = Optional.of(project);
		projects = new ArrayList<Project>();
		projects.add(project);
		
		Mockito.when(projectrepo.findById(any()))
        .thenReturn(projectOpt);
		Mockito.when(projectrepo.findAll())
        .thenReturn(projects);
		Mockito.when(projectrepo.findAll(new Sort(Sort.Direction.ASC, "startdate")))
        .thenReturn(projects);
		Mockito.when(projectrepo.findAll(new Sort(Sort.Direction.ASC, "enddate")))
        .thenReturn(projects);
		Mockito.when(projectrepo.findAll(new Sort(Sort.Direction.ASC, "priority")))
        .thenReturn(projects);
		
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
        
        User = new User(1,"Sam", "John",101, project, Task);
		UserOpt = Optional.of(User);
		userlist = new ArrayList<User>();
		userlist.add(User);
		
        Mockito.when(userRepo.save(any()))
        .thenReturn(User);
        Mockito.when(userRepo.findById(any()))
        .thenReturn(UserOpt);
        Mockito.when(userRepo.findByEmployeeid(anyInt()))
        .thenReturn(userlist);
        Mockito.when(userRepo.existsById(any()))
        .thenReturn(true);
        Mockito.when(userRepo.findAll())
        .thenReturn(userlist);   
        
        ProjectDetails = new ProjectDetails(project, 1, "N", User); 
        

    }

    @Test
    public void testfindTaskById() {
        // Run the test
        final List<ProjectDetails> result = ProjectDetailsService.findAll();
        // Verify the results
        assertTrue(result != null);
    }
    

    @Test
    public void testsortProjectByStartDate() {
        final List<ProjectDetails> result = ProjectDetailsService.sortProjectByStartDate();
    		assertTrue(result != null);
    }
    
    @Test
    public void testsortProjectByEndDate() {
        final List<ProjectDetails> result = ProjectDetailsService.sortProjectByEndDate();
    		assertTrue(result != null);
    }
    
    @Test
    public void testsortProjectByPriority() {
        final List<ProjectDetails> result = ProjectDetailsService.sortProjectByPriority();
    		assertTrue(result != null);
    }
    
    @Test
    public void testsortProjectByCompleted() {
        final List<ProjectDetails> result = ProjectDetailsService.sortProjectByCompleted();
    		assertTrue(result != null);
    }
    

}
