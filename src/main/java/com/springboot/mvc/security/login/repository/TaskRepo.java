package com.springboot.mvc.security.login.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.mvc.security.login.model.ParentTask;
import com.springboot.mvc.security.login.model.Project;
import com.springboot.mvc.security.login.model.Task;



public interface TaskRepo extends JpaRepository<Task, Long> {
	
	@Modifying
    @Transactional
	@Query("delete from  Task t where t.project = ?1")
	int updateTaskByProejct(Project project);
	
    List<Task> findAllByTaskname(String taskname);
    List<Task> findAllByProject(Project project);
    List<Task> findAllByProjectOrderByStartdate(Project project);
    List<Task> findAllByProjectOrderByEnddate(Project project);
    List<Task> findAllByProjectOrderByPriority(Project project);
    List<Task> findAllByProjectOrderByActive(Project project);


    long countByProject(Project project);
    long countByProjectAndActive(Project project, int active);




}
