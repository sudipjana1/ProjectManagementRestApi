package com.springboot.mvc.security.login.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.mvc.security.login.model.ParentTask;



public interface ParentTaskRepo extends JpaRepository<ParentTask, Long> {
	
	
    List<ParentTask> findByParenttask(String parenttask);

}
