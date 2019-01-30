package com.springboot.mvc.security.login.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.mvc.security.login.model.Project;
import com.springboot.mvc.security.login.model.ProjectDetails;
import com.springboot.mvc.security.login.model.User;
import com.springboot.mvc.security.login.repository.ProjectRepo;
import com.springboot.mvc.security.login.repository.TaskRepo;
import com.springboot.mvc.security.login.repository.UserRepo;

@Service
public class ProjectDetailsServiceImpl implements ProjectDetailsService {
	@Autowired
	private TaskRepo taskRepo;
	
	@Autowired
	private ProjectRepo projectRepo;
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public List<ProjectDetails> findAll() {
		// TODO Auto-generated method stub
		
		List<ProjectDetails> pdetails = new ArrayList<ProjectDetails>();
		List<Project> projects = projectRepo.findAll();
		Iterator<Project> it = projects.iterator();
		while(it.hasNext()) {
			Project project = (Project) it.next();
			User user = userRepo.findByProject(project);
			long noOfTask = taskRepo.countByProject(project);
			long inCompleteCount = taskRepo.countByProjectAndActive(project, 1);
			String completed = "N";
			if(inCompleteCount == 0 && noOfTask > 0) {
				completed = "Y";
			}
			pdetails.add(new ProjectDetails(project,noOfTask,completed,user));
		}
		return pdetails;
	}

	@Override
	public List<ProjectDetails> sortProjectByStartDate() {
		// TODO Auto-generated method stub
		List<ProjectDetails> pdetails = new ArrayList<ProjectDetails>();
		List<Project> projects = projectRepo.findAll(orderByStartdateAsc());
		Iterator<Project> it = projects.iterator();
		while(it.hasNext()) {
			Project project = (Project) it.next();
			User user = userRepo.findByProject(project);
			long noOfTask = taskRepo.countByProject(project);
			long inCompleteCount = taskRepo.countByProjectAndActive(project, 1);
			String completed = "N";
			if(inCompleteCount == 0 && noOfTask > 0) {
				completed = "Y";
			}
			pdetails.add(new ProjectDetails(project,noOfTask,completed,user));
		}
		return pdetails;
	}

	@Override
	public List<ProjectDetails> sortProjectByEndDate() {
		// TODO Auto-generated method stub
		List<ProjectDetails> pdetails = new ArrayList<ProjectDetails>();
		List<Project> projects = projectRepo.findAll(orderByEnddateAsc());
		Iterator<Project> it = projects.iterator();
		while(it.hasNext()) {
			Project project = (Project) it.next();
			User user = userRepo.findByProject(project);
			long noOfTask = taskRepo.countByProject(project);
			long inCompleteCount = taskRepo.countByProjectAndActive(project, 1);
			String completed = "N";
			if(inCompleteCount == 0 && noOfTask > 0) {
				completed = "Y";
			}
			pdetails.add(new ProjectDetails(project,noOfTask,completed,user));
		}
		return pdetails;
	}

	@Override
	public List<ProjectDetails> sortProjectByPriority() {
		// TODO Auto-generated method stub
		List<ProjectDetails> pdetails = new ArrayList<ProjectDetails>();
		List<Project> projects = projectRepo.findAll(orderByPriorityAsc());
		Iterator<Project> it = projects.iterator();
		while(it.hasNext()) {
			Project project = (Project) it.next();
			long noOfTask = taskRepo.countByProject(project);
			User user = userRepo.findByProject(project);
			long inCompleteCount = taskRepo.countByProjectAndActive(project, 1);
			String completed = "N";
			if(inCompleteCount == 0 && noOfTask > 0) {
				completed = "Y";
			}
			pdetails.add(new ProjectDetails(project,noOfTask,completed,user));
		}
		return pdetails;
	}

	@Override
	public List<ProjectDetails> sortProjectByCompleted() {
		// TODO Auto-generated method stub
		List<ProjectDetails> pdetails = findAll();
		List<ProjectDetails> pdetailsort = pdetails.stream().sorted(Comparator.comparing(ProjectDetails::getCompleted))
				.collect(Collectors.toList());
		return pdetailsort;
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
