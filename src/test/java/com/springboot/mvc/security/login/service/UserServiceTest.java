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
import com.springboot.mvc.security.login.model.User;
import com.springboot.mvc.security.login.repository.ParentTaskRepo;
import com.springboot.mvc.security.login.repository.UserRepo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class UserServiceTest extends LoginApplicationTests{
	
	@InjectMocks
    private UserServiceImpl UserService;
	@Mock 
	private UserRepo userRepo;
    private User User;
    private List<User> userlist;
    Optional<User> UserOpt;
	private int Userid = 1;


    @Before
    public void setUp() {
		MockitoAnnotations.initMocks(this);

		User = new User(1,"Sam", "John",101, new Project(), new Task());
        
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
        
        
        
        UserService.saveUser(User);

    }

    @Test
    public void testfindUserById() {
        // Run the test
        final User result = UserService.findUserById(Userid);
        // Verify the results
        assertEquals(1,result.getUserid());
    }
    
    @Test
    public void testsaveUser() {
        // Setup
    		User User1 = new User(2,"Ram", "Jana",102, new Project(), new Task());

        // Run the test
        final User result = UserService.saveUser(User1);
        // Verify the results
        assertTrue(result != null);
    }
    
    @Test
    public void testfindUserByEmployeeid() {
        // Run the test
        final List<User> result = UserService.findUserByEmployeeid(101);
        // Verify the results
        assertEquals(1,result.get(0).getUserid());
    }
    
    @Test
    public void testfindAll() {
    		final List<User> Users = UserService.findAll();
    		assertTrue(Users != null);
    }
    
    @Test
    public void testsortUserByEmployeeid() {
    		final List<User> Users = UserService.sortUserByEmployeeid();
    		assertTrue(Users != null);
    }
    
    @Test
    public void testsortUserByFirstname() {
    		final List<User> Users = UserService.sortUserByFirstname();
    		assertTrue(Users != null);
    }
    
    @Test
    public void testsortUserByLastname() {
    		final List<User> Users = UserService.sortUserByLastname();
    		assertTrue(Users != null);
    }
    
    
    @Test
    public void testdeleteUser() {
    		assertTrue(UserService.deleteUser(Userid));
    }


}
