package com.springboot.mvc.security.login.service;

import java.util.List;

import com.springboot.mvc.security.login.model.ParentTask;


public interface ParentTaskService {

    public ParentTask findParentTaskByid(long parentid); 
    
    public List<ParentTask> findAllParentTask(); 


    public ParentTask saveParentTask(ParentTask parentTask);
    
    public boolean deleteParentTask(long parentid);
}