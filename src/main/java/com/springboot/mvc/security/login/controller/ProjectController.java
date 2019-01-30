package com.springboot.mvc.security.login.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.mvc.security.login.model.Project;
import com.springboot.mvc.security.login.model.ProjectDetails;
import com.springboot.mvc.security.login.service.ProjectDetailsService;
import com.springboot.mvc.security.login.service.ProjectService;






/**
 * Handles requests for the application home page.
 */
@RestController
@RequestMapping("/projectmanagement/api/project")
@CrossOrigin(origins="http://localhost:4200")

public class ProjectController {

	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
	@Autowired ProjectService projectService;
	@Autowired ProjectDetailsService projectDetailsService;


	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));

	}


	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
	
	
		return "Home";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<?> listAll() {
		logger.info("Welcome home! The client locale is {}.");

		List<Project> projects = projectService.findAll();
		logger.info("Parent task");

		if (projects.isEmpty()) {
			logger.info("Parent task 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("No Project Defined"),HttpStatus.OK);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("Parent task 2");

		return new ResponseEntity<List<Project>>(projects, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> add(@RequestBody Project project) {
		logger.info("Welcome home! The client locale is {}.");

		projectService.saveProject(project);
		logger.info("Parent task");

		return new ResponseEntity<Project>(project, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> get(@PathVariable("id") long id) {
		logger.info("Welcome home! The client locale is {}.");

		Project project = projectService.findProjectById(id);
		logger.info("Parent task");

		if (project == null) {
			logger.info("Parent task 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("No Project"),HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("Parent task 2");

		return new ResponseEntity<Project>(project, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/projectname/{projectName}", method = RequestMethod.GET)
	public ResponseEntity<?> getBypProjectName(@PathVariable("projectName") String projectName) {
		logger.info("Welcome home! The client locale is {}.");

		Project project = projectService.findProjectByProjectname(projectName);
		logger.info("Parent task");

		if (project == null) {
			logger.info("Parent task 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("No Project with this Project Name"),HttpStatus.OK);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("Parent task 2");

		return new ResponseEntity<Project>(project, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Project project) {
		logger.info("Welcome home! The client locale is {}.");

		Project userTemp = projectService.findProjectById(id);
		logger.info("Parent task");

		if (userTemp == null) {
			logger.info("Parent task 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("No Project"),HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		project.setProjectid(id);
		logger.info("Parent task 2");
		projectService.saveProject(project);

		return new ResponseEntity<Project>(project, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		logger.info("Welcome home! The client locale is {}.");

		Project project = projectService.findProjectById(id);
		logger.info("Parent task");

		if (project == null) {
			logger.info("Parent task 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("No Project"),HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("Parent task 2");
		projectService.deleteProject(id);

		return new ResponseEntity<Project>(project, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sortbystartdate/", method = RequestMethod.GET)
	public ResponseEntity<?> sortbystartdate() {
		logger.info("Welcome home! The client locale is {}.");

		List<ProjectDetails> projects = projectDetailsService.sortProjectByStartDate();
		logger.info("Parent task");

		if (projects.isEmpty()) {
			logger.info("Parent task 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Empty users"),HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("Parent task 2");

		return new ResponseEntity<List<ProjectDetails>>(projects, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sortbyenddate/", method = RequestMethod.GET)
	public ResponseEntity<?> sortbyenddate() {
		logger.info("Welcome home! The client locale is {}.");

		List<ProjectDetails> projects = projectDetailsService.sortProjectByEndDate();
		logger.info("Parent task");

		if (projects.isEmpty()) {
			logger.info("Parent task 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Empty users"),HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("Parent task 2");

		return new ResponseEntity<List<ProjectDetails>>(projects, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sortbypriority/", method = RequestMethod.GET)
	public ResponseEntity<?> sortbypriority() {
		logger.info("Welcome home! The client locale is {}.");

		List<ProjectDetails> projects = projectDetailsService.sortProjectByPriority();
		logger.info("Parent task");

		if (projects.isEmpty()) {
			logger.info("Parent task 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Empty users"),HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("Parent task 2");

		return new ResponseEntity<List<ProjectDetails>>(projects, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sortbycompleted/", method = RequestMethod.GET)
	public ResponseEntity<?> sortbycompleted() {
		logger.info("Welcome home! The client locale is {}.");

		List<ProjectDetails> projects = projectDetailsService.sortProjectByCompleted();
		logger.info("Parent task");

		if (projects.isEmpty()) {
			logger.info("Parent task 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Empty users"),HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("Parent task 2");

		return new ResponseEntity<List<ProjectDetails>>(projects, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getall/", method = RequestMethod.GET)
	public ResponseEntity<?> getall() {
		logger.info("Welcome home! The client locale is {}.");

		List<ProjectDetails> projects = projectDetailsService.findAll();
		logger.info("Parent task");

		if (projects.isEmpty()) {
			logger.info("Parent task 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Empty users"),HttpStatus.OK);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("Parent task 2");

		return new ResponseEntity<List<ProjectDetails>>(projects, HttpStatus.OK);
	}

	

}
