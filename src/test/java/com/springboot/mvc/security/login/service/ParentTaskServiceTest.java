package com.springboot.mvc.security.login.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.springboot.mvc.security.login.LoginApplicationTests;
import com.springboot.mvc.security.login.model.ParentTask;
import com.springboot.mvc.security.login.repository.ParentTaskRepo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import java.util.List;
import java.util.Optional;

public class ParentTaskServiceTest extends LoginApplicationTests{
	
	@InjectMocks
    private ParentTaskServiceImpl parentTaskService;
	@Mock 
	private ParentTaskRepo parentTaskrepo;
    private ParentTask parentTask;
    Optional<ParentTask> parentTaskOpt;
	private long parentId = 1;


    @Before
    public void setUp() {
		MockitoAnnotations.initMocks(this);

		parentTask = new ParentTask(1,"P1");
        
        parentTaskOpt = Optional.of(parentTask);
        
        Mockito.when(parentTaskrepo.save(any()))
        .thenReturn(parentTask);
        Mockito.when(parentTaskrepo.findById(any()))
        .thenReturn(parentTaskOpt);
        
        parentTaskService.saveParentTask(parentTask);

    }

    @Test
    public void testfindParentTaskByid() {
        // Run the test
        final ParentTask result = parentTaskService.findParentTaskByid(parentId);
        // Verify the results
        assertEquals(1,result.getParentid());
    }
    
    @Test
    public void testsaveParentTask() {
        // Setup
    		ParentTask parentTask1 = new ParentTask(2,"P2");
        // Run the test
        final ParentTask result = parentTaskService.saveParentTask(parentTask1);
        // Verify the results
        assertTrue(result != null);
    }
    
    @Test
    public void testfindAllParentTask() {
    		final List<ParentTask> parentTasks = parentTaskService.findAllParentTask();
    		assertTrue(parentTasks != null);
    }
    
    @Test
    public void testdeleteParentTask() {
    		assertTrue(parentTaskService.deleteParentTask(parentId));
    }


}
