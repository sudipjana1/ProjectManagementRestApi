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
import com.springboot.mvc.security.login.model.Task;
import com.springboot.mvc.security.login.service.ProjectService;
import com.springboot.mvc.security.login.service.TaskService;






/**
 * Handles requests for the application home page.
 */
@RestController
@RequestMapping("/projectmanagement/api/task")
@CrossOrigin(origins="http://localhost:4200")

public class TaskController {

	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
	@Autowired TaskService taskService;

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

		List<Task> tasks = taskService.findAll();
		logger.info("Parent task");

		if (tasks.isEmpty()) {
			logger.info("Parent task 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("No Task Pending Please Add Task"),HttpStatus.OK);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("Parent task 2");

		return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> add(@RequestBody Task task) {
		logger.info("Welcome home! The client locale is {}.");

		taskService.saveTask(task);
		logger.info("Parent task");

		return new ResponseEntity<Task>(task, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> get(@PathVariable("id") long id) {
		logger.info("Welcome home! The client locale is {}.");

		Task task = taskService.findTaskById(id);
		logger.info("Parent task");

		if (task == null) {
			logger.info("Parent task 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("No Task"),HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("Parent task 2");

		return new ResponseEntity<Task>(task, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Task task) {
		logger.info("Welcome home! The client locale is {}.");

		Task taskTemp = taskService.findTaskById(id);
		logger.info("Parent task");

		if (taskTemp == null) {
			logger.info("Parent task 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("No User"),HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		task.setTaskid(id);;
		logger.info("Parent task 2");
		taskService.saveTask(task);

		return new ResponseEntity<Task>(task, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		logger.info("Welcome home! The client locale is {}.");

		Task task = taskService.findTaskById(id);
		logger.info("Parent task");

		if (task == null) {
			logger.info("Parent task 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("No User"),HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("Parent task 2");
		taskService.deleteTask(id);

		return new ResponseEntity<Task>(task, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/project/{projectid}", method = RequestMethod.GET)
	public ResponseEntity<?> getByProjectid(@PathVariable("projectid") long projectid) {
		logger.info("Welcome home! The client locale is {}.");

		List<Task> tasks = taskService.findAllByProjectid(projectid);
		logger.info("Parent task");

		if (tasks.isEmpty()) {
			logger.info("Parent task 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("No Task for selected Project"),HttpStatus.OK);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("Parent task 2");

		return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/project/{projectid}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateByProjectid(@PathVariable("projectid") long projectid) {
		logger.info("Welcome home! The client locale is {}.");

		int updateCount = taskService.updateTaskByProjectid(projectid);
		logger.info("Parent task");

		if (updateCount == 0) {
			logger.info("Parent task 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("No Task for selected Project"),HttpStatus.OK);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("Parent task 2");

		return new ResponseEntity<String>(Integer.toString(updateCount), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sortbystartdate/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> sortbystartdate(@PathVariable("id") long id) {
		logger.info("Welcome home! The client locale is {}.");

		List<Task> tasks = taskService.sortTaskByStartDate(id);
		logger.info("Parent task");

		if (tasks.isEmpty()) {
			logger.info("Parent task 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("No Task for selected Project"),HttpStatus.OK);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("Parent task 2");

		return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sortbyenddate/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> sortbyenddate(@PathVariable("id") long id) {
		logger.info("Welcome home! The client locale is {}.");

		List<Task> tasks = taskService.sortTaskByEndDate(id);
		logger.info("Parent task");

		if (tasks.isEmpty()) {
			logger.info("Parent task 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("No Task for selected Project"),HttpStatus.OK);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("Parent task 2");

		return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sortbypriority/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> sortbypriority(@PathVariable("id") long id) {
		logger.info("Welcome home! The client locale is {}.");

		List<Task> tasks = taskService.sortTaskByPriority(id);
		logger.info("Parent task");

		if (tasks.isEmpty()) {
			logger.info("Parent task 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("No Task for selected Project"),HttpStatus.OK);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("Parent task 2");

		return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sortbycompleted/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> sortbycompleted(@PathVariable("id") long id) {
		logger.info("Welcome home! The client locale is {}.");

		List<Task> tasks = taskService.sortTaskByCompleted(id);
		logger.info("Parent task");

		if (tasks.isEmpty()) {
			logger.info("Parent task 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("No Task for selected Project"),HttpStatus.OK);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("Parent task 2");

		return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
	}
	

}
