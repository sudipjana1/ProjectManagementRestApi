package com.springboot.mvc.security.login.service;

import com.springboot.mvc.security.login.model.Project;

import java.util.List;

import org.springframework.data.domain.Sort;



public interface ProjectService {

    public Project findProjectByProjectname(String projectname); 
    public Project findProjectById(long projectid); 


    public Project saveProject(Project project);
    
    public boolean deleteProject(long projectid);
    public List<Project> findAll(); 

    public List<Project> sortProjectByStartDate(); 
    public List<Project> sortProjectByEndDate(); 
    public List<Project> sortProjectByPriority(); 
    //public List<Project> sortProjectByCompleted(); 


}