package com.springboot.mvc.security.login.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.mvc.security.login.model.Project;
import com.springboot.mvc.security.login.repository.ProjectRepo;

@Service
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	private ProjectRepo projectRepo ;

	@Override
	public Project findProjectByProjectname(String projectname) {
		return projectRepo.findByProjectname(projectname);
	}

	@Override
	public Project findProjectById(long projectid) {
		Optional<Project> project;
		project = projectRepo.findById(projectid);
		if(project.isPresent()) {
			return project.get();
		}else {
			return null;
		}
	}

	@Override
	public Project saveProject(Project project) {
		// TODO Auto-generated method stub
		return projectRepo.save(project);
	}

	@Override
	public boolean deleteProject(long projectid) {
		// TODO Auto-generated method stub
		if(projectRepo.existsById(projectid)) {
			projectRepo.deleteById(projectid);
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<Project> findAll() {
		return projectRepo.findAll();
	}

	@Override
	public List<Project> sortProjectByStartDate() {
		return projectRepo.findAll(orderByStartdateAsc());
	}

	@Override
	public List<Project> sortProjectByEndDate() {
		return projectRepo.findAll(orderByEnddateAsc());
	}

	@Override
	public List<Project> sortProjectByPriority() {
		return projectRepo.findAll(orderByPriorityAsc());
	}
	private Sort orderByStartdateAsc() {
	    return new Sort(Sort.Direction.ASC, "startdate");
	}
	private Sort orderByEnddateAsc() {
	    return new Sort(Sort.Direction.ASC, "enddate");
	}
	private Sort orderByPriorityAsc() {
	    return new Sort(Sort.Direction.ASC, "priority");
	}
}
