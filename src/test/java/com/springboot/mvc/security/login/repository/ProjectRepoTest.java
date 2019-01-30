package com.springboot.mvc.security.login.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

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
import com.springboot.mvc.security.login.model.Project;


@RunWith(SpringRunner.class)
@SpringBootTest

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class})
@DatabaseSetup("/projectData.xml")
public class ProjectRepoTest extends LoginApplicationTests{
	
	@Autowired 
	private ProjectRepo projectRepo;
	
	@Test
	public void testFindAll() throws Exception{
	
		List<Project> projects = projectRepo.findAll();
        assertThat(projects.size(), is(3));
	}
	
	
	@Test
	public void testFindByProjectname() throws Exception{
	
		Project project = projectRepo.findByProjectname("P2");
		assertEquals("P2",project.getProjectname());
	}

}
