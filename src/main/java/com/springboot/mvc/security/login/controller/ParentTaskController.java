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

import com.springboot.mvc.security.login.model.ParentTask;
import com.springboot.mvc.security.login.service.ParentTaskService;






/**
 * Handles requests for the application home page.
 */
@RestController
@RequestMapping("/projectmanagement/api/parenttask")
//@CrossOrigin(origins="http://localhost:4200")
@CrossOrigin(origins="http://127.0.0.1:8081")



public class ParentTaskController {

	private static final Logger logger = LoggerFactory.getLogger(ParentTaskController.class);
	@Autowired ParentTaskService parentTaskService;

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
	public ResponseEntity<?> listAllParentTask() {
		logger.info("Welcome home! The client locale is {}.");

		List<ParentTask> parentTasks = parentTaskService.findAllParentTask();
		logger.info("Parent task");

		if (parentTasks.isEmpty()) {
			logger.info("Parent task 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Empty Parent Task"),HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("Parent task 2");

		return new ResponseEntity<List<ParentTask>>(parentTasks, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> addParentTask(@RequestBody ParentTask parentTask) {
		logger.info("Welcome home! The client locale is {}.");

		parentTaskService.saveParentTask(parentTask);
		logger.info("Parent task");

		return new ResponseEntity<ParentTask>(parentTask, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getParentTask(@PathVariable("id") long id) {
		logger.info("Welcome home! The client locale is {}.");

		ParentTask parentTask = parentTaskService.findParentTaskByid(id);
		logger.info("Parent task");

		if (parentTask == null) {
			logger.info("Parent task 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("No Parent Task"),HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("Parent task 2");

		return new ResponseEntity<ParentTask>(parentTask, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateParentTask(@PathVariable("id") long id, @RequestBody ParentTask parentTask) {
		logger.info("Welcome home! The client locale is {}.");

		ParentTask parentTasktemp = parentTaskService.findParentTaskByid(id);
		logger.info("Parent task");

		if (parentTasktemp == null) {
			logger.info("Parent task 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("No Parent Task"),HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		parentTask.setParentid(id);
		logger.info("Parent task 2");
		parentTaskService.saveParentTask(parentTask);

		return new ResponseEntity<ParentTask>(parentTask, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> updateParentTask(@PathVariable("id") long id) {
		logger.info("Welcome home! The client locale is {}.");

		ParentTask parentTask = parentTaskService.findParentTaskByid(id);
		logger.info("Parent task");

		if (parentTask == null) {
			logger.info("Parent task 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("No Parent Task"),HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("Parent task 2");
		parentTaskService.deleteParentTask(id);

		return new ResponseEntity<ParentTask>(parentTask, HttpStatus.OK);
	}
	

}
