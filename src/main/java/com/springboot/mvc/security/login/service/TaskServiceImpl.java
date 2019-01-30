package com.springboot.mvc.security.login.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.mvc.security.login.model.Task;
import com.springboot.mvc.security.login.repository.ProjectRepo;
import com.springboot.mvc.security.login.repository.TaskRepo;

@Service
public class TaskServiceImpl implements TaskService {
	
	@Autowired
	private TaskRepo taskRepo;
	
	@Autowired
	private ProjectRepo projectRepo;

	@Override
	public List<Task> findAllByTaskname(String taskname) {
		return taskRepo.findAllByTaskname(taskname);
	}

	@Override
	public Task findTaskById(long taskid) {
		Optional<Task> task;
		task = taskRepo.findById(taskid);
		if(task.isPresent()) {
			return task.get();
		}else {
			return null;
		}
	}

	@Override
	public Task saveTask(Task task) {
		return taskRepo.save(task);
	}

	@Override
	public boolean deleteTask(long taskid) {
		if (taskRepo.existsById(taskid)){
			taskRepo.deleteById(taskid);
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<Task> findAll() {
		   return taskRepo.findAll();
	}

	@Override
	public List<Task> findAllByProjectid(long projectid) {
		return taskRepo.findAllByProject(projectRepo.findById(projectid).get());
	}

	@Override
	public List<Task> sortTaskByStartDate(long projectid) {
		/*Task task = new Task();                         
		task.setProject(projectRepo.findById(projectid).get());
		System.out.println(task.toString());
		Example<Task> example = Example.of(task);
		List<Task> tasks = taskRepo.findAll(example, orderByStartdateAsc());
		System.out.println(tasks.toString());
		return tasks;*/
		return taskRepo.findAllByProjectOrderByStartdate(projectRepo.findById(projectid).get());

	}

	@Override
	public List<Task> sortTaskByEndDate(long projectid) {
	/*	Task task = new Task();                         
		task.setProject(projectRepo.findById(projectid).get());  
		Example<Task> example = Example.of(task);
		return taskRepo.findAll(example, orderByEnddateAsc());*/
		return taskRepo.findAllByProjectOrderByEnddate(projectRepo.findById(projectid).get());
	}

	@Override
	public List<Task> sortTaskByPriority(long projectid) {
		/*Task task = new Task();                         
		task.setProject(projectRepo.findById(projectid).get());                          
		Example<Task> example = Example.of(task);
		return taskRepo.findAll(example, orderByPriorityAsc());*/
		return taskRepo.findAllByProjectOrderByPriority(projectRepo.findById(projectid).get());

	}

	@Override
	public List<Task> sortTaskByCompleted(long projectid) {
	/*	Task task = new Task();                         
		task.setProject(projectRepo.findById(projectid).get());                          
		Example<Task> example = Example.of(task);
		return taskRepo.findAll(example, orderByActiveAsc());*/
		return taskRepo.findAllByProjectOrderByActive(projectRepo.findById(projectid).get());

	}



	@Override
	public int updateTaskByProjectid(long projectid) {
		// TODO Auto-generated method stub
		return taskRepo.updateTaskByProejct(projectRepo.findById(projectid).get());
	}
}
