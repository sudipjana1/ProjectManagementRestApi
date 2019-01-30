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
import com.springboot.mvc.security.login.repository.ParentTaskRepo;
import com.springboot.mvc.security.login.repository.ProjectRepo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ProjectServiceTest extends LoginApplicationTests{
	
	@InjectMocks
    private ProjectServiceImpl projectService;
	@Mock 
	private ProjectRepo projectrepo;
    private Project project;
    private List<Project> projects;
    Optional<Project> projectOpt;
	private long projectid = 1;


    @Before
    public void setUp() {
		MockitoAnnotations.initMocks(this);

		project = new Project(1,"P1", new Date(2018,01,01), new Date(2018,10,01), 20);
        
		projectOpt = Optional.of(project);
		projects = new ArrayList<Project>();
		projects.add(project);
        
        Mockito.when(projectrepo.save(any()))
        .thenReturn(project);
        Mockito.when(projectrepo.findById(any()))
        .thenReturn(projectOpt);
        Mockito.when(projectrepo.findAll())
        .thenReturn(projects);
        Mockito.when(projectrepo.findByProjectname(any()))
        .thenReturn(project);
        Mockito.when(projectrepo.existsById(any()))
        .thenReturn(true);
        
        
        
        projectService.saveProject(project);

    }

    @Test
    public void testfindProjectById() {
        // Run the test
        final Project result = projectService.findProjectById(projectid);
        // Verify the results
        assertEquals(1,result.getProjectid());
    }
    
    @Test
    public void testsaveProject() {
        // Setup
    		Project project1 = new Project(2,"P2", new Date(2018,01,31), new Date(2018,10,20), 18);

        // Run the test
        final Project result = projectService.saveProject(project1);
        // Verify the results
        assertTrue(result != null);
    }
    
    @Test
    public void testfindProjectByProjectname() {
        // Run the test
        final Project result = projectService.findProjectByProjectname("P1");
        // Verify the results
        assertEquals(1,result.getProjectid());
    }
    
    @Test
    public void testfindAll() {
    		final List<Project> projects = projectService.findAll();
    		assertTrue(projects != null);
    }
    
    @Test
    public void testsortProjectByStartDate() {
    		final List<Project> projects = projectService.sortProjectByStartDate();
    		assertTrue(projects != null);
    }
    
    @Test
    public void testsortProjectByEndDate() {
    		final List<Project> projects = projectService.sortProjectByEndDate();
    		assertTrue(projects != null);
    }
    
    @Test
    public void testsortProjectByPriority() {
    		final List<Project> projects = projectService.sortProjectByPriority();
    		assertTrue(projects != null);
    }
    
    
    @Test
    public void testdeleteProject() {
    		assertTrue(projectService.deleteProject(projectid));
    }


}
