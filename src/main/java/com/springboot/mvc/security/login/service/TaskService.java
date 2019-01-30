package com.springboot.mvc.security.login.service;

import com.springboot.mvc.security.login.model.Task;

import java.util.List;

public interface TaskService {

    public List<Task> findAllByTaskname(String taskname); 
    public Task findTaskById(long taskid); 
    public Task saveTask(Task task);
    
    public boolean deleteTask(long taskid);
    public int updateTaskByProjectid(long projectid); 

    public List<Task> findAll(); 
    public List<Task> findAllByProjectid(long projectid); 

    public List<Task> sortTaskByStartDate(long projectid); 
    public List<Task> sortTaskByEndDate(long projectid); 
    public List<Task> sortTaskByPriority(long projectid); 
    public List<Task> sortTaskByCompleted(long projectid); 
}