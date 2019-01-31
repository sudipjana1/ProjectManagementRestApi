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

import com.springboot.mvc.security.login.model.User;
import com.springboot.mvc.security.login.service.UserService;






/**
 * Handles requests for the application home page.
 */
@RestController
@RequestMapping("/projectmanagement/api/user")
//@CrossOrigin(origins="http://localhost:4200")
@CrossOrigin(origins="http://127.0.0.1:8081")



public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired UserService userService;

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

		List<User> users = userService.findAll();
		logger.info("User Controller");

		if (users.isEmpty()) {
			logger.info("User Controller 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Empty users"),HttpStatus.OK);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("User Controller 2");

		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> add(@RequestBody User user) {
		logger.info("Welcome home! The client locale is {}.");
		logger.info(user.toString());

		userService.saveUser(user);
		logger.info("User Controller");

		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> get(@PathVariable("id") long id) {
		logger.info("Welcome home! The client locale is {}.");

		User user = userService.findUserById(id);
		logger.info("User Controller");

		if (user == null) {
			logger.info("User Controller 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("No User"),HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("User Controller 2");

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/employeeid/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getByEmployeeid(@PathVariable("id") int id) {
		logger.info("Welcome home! The client locale is {}.");

		List<User> users = userService.findUserByEmployeeid(id);
		logger.info("User Controller");

		if (users.isEmpty()) {
			logger.info("User Controller 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("No User with this Employee ID"),HttpStatus.OK);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("User Controller 2");

		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/task/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getByTasK(@PathVariable("id") long id) {
		logger.info("Welcome home! The client locale is {}.");

		User user = userService.findUserByTaskid(id);
		logger.info("User Controller");

		if (user == null) {
			logger.info("User Controller 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("No User"),HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("User Controller 2");

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody User user) {
		logger.info("Welcome home! The client locale is {}.");

		User userTemp = userService.findUserById(id);
		logger.info("User Controller");

		if (userTemp == null) {
			logger.info("User Controller 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("No User"),HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		user.setUserid(id);
		logger.info("User Controller 2");
		userService.saveUser(user);

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/project/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("id") long id) {
		logger.info("/project/{id}   PUT");

		int updateCount = userService.updateUserByProejctAndTask(id);
		logger.info("User Controller");

		if (updateCount == 0) {
			logger.info("User Controller 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("No User"),HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("User Controller 2");

		return new ResponseEntity<String>(Integer.toString(updateCount), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		logger.info("Welcome home! The client locale is {}.");

		User user = userService.findUserById(id);
		logger.info("User Controller");

		if (user == null) {
			logger.info("User Controller 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("No User"),HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("User Controller 2");
		userService.deleteUser(id);

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sortbyemployeeid/", method = RequestMethod.GET)
	public ResponseEntity<?> sortbyemployeeid() {
		logger.info("Welcome home! The client locale is {}.");

		List<User> users = userService.sortUserByEmployeeid();
		logger.info("User Controller");

		if (users.isEmpty()) {
			logger.info("User Controller 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Empty users"),HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("User Controller 2");

		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sortbyfirstname/", method = RequestMethod.GET)
	public ResponseEntity<?> sortbyfirstname() {
		logger.info("Welcome home! The client locale is {}.");

		List<User> users = userService.sortUserByFirstname();
		logger.info("User Controller");

		if (users.isEmpty()) {
			logger.info("User Controller 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Empty users"),HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("User Controller 2");

		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sortbylastname/", method = RequestMethod.GET)
	public ResponseEntity<?> sortbylastname() {
		logger.info("Welcome home! The client locale is {}.");

		List<User> users = userService.sortUserByLastname();
		logger.info("User Controller");

		if (users.isEmpty()) {
			logger.info("User Controller 1");

			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Empty users"),HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		logger.info("User Controller 2");

		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	

}
