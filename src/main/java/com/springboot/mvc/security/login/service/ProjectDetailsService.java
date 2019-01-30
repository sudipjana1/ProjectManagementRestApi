package com.springboot.mvc.security.login.service;

import com.springboot.mvc.security.login.model.Project;
import com.springboot.mvc.security.login.model.ProjectDetails;

import java.util.List;

import org.springframework.data.domain.Sort;



public interface ProjectDetailsService {


    public List<ProjectDetails> findAll(); 

    public List<ProjectDetails> sortProjectByStartDate(); 
    public List<ProjectDetails> sortProjectByEndDate(); 
    public List<ProjectDetails> sortProjectByPriority(); 
    public List<ProjectDetails> sortProjectByCompleted(); 


}