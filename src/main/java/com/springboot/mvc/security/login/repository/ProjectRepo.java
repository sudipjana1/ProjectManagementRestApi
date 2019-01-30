package com.springboot.mvc.security.login.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.mvc.security.login.model.ParentTask;
import com.springboot.mvc.security.login.model.Project;



public interface ProjectRepo extends JpaRepository<Project, Long> {
	
	
    Project findByProjectname(String projectname);

}
