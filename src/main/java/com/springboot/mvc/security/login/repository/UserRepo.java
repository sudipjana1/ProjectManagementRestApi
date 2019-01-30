package com.springboot.mvc.security.login.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.mvc.security.login.model.Project;
import com.springboot.mvc.security.login.model.Task;
import com.springboot.mvc.security.login.model.User;



public interface UserRepo extends JpaRepository<User, Long> {
	
	@Modifying
    @Transactional
	@Query(value = "update iiht.users set task_id = null where users.task_id in (select task_id from iiht.tasks where tasks.project_id = ?1 )", nativeQuery = true)
	int updateUserByProejctAndTask(long projectid);
	
    List<User> findByEmployeeid(int employeeid);
    User findByProject(Project project);
    User findByTask(Task task);

}
