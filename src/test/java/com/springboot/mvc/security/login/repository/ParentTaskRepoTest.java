package com.springboot.mvc.security.login.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;


import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.springboot.mvc.security.login.LoginApplicationTests;
import com.springboot.mvc.security.login.model.ParentTask;


@RunWith(SpringRunner.class)
@SpringBootTest

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class})
@DatabaseSetup("/parentTaskData.xml")
public class ParentTaskRepoTest extends LoginApplicationTests{
	
	@Autowired 
	private ParentTaskRepo parentTaskDao;
	
	@Test
	public void testFindAll() throws Exception{
	
		List<ParentTask> parentTasks = parentTaskDao.findAll();
        assertThat(parentTasks.size(), is(2));
	}
	
	
	@Test
	public void testFindByParentTask() throws Exception{
	
		List<ParentTask> parentTasks = parentTaskDao.findByParenttask("Parent Task 2");
        assertThat(parentTasks.size(), is(1));
	}

}
