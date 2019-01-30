package com.springboot.mvc.security.login.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import com.springboot.mvc.security.login.model.User;
import com.springboot.mvc.security.login.repository.TaskRepo;
import com.springboot.mvc.security.login.repository.UserRepo;


@Service
public class UserServiceImpl implements UserService{
    
	@Autowired
    private UserRepo userRepo;

	@Autowired 
	private TaskRepo taskRepo;
	
	private Sort orderByEmployeeidAsc() {
	    return new Sort(Sort.Direction.ASC, "employeeid");
	}
	
	private Sort orderByFirstnameAsc() {
	    return new Sort(Sort.Direction.ASC, "firstname");
	}
	
	private Sort orderByLastnameAsc() {
	    return new Sort(Sort.Direction.ASC, "lastname");
	}
	
	@Override
    public User saveUser(User user) {
        return userRepo.save(user);
    }
    
	@Override
    public boolean deleteUser(long userid) {
    		if (userRepo.findById(userid).isPresent()) {
    			userRepo.deleteById(userid);
    			return true;
    		}else
    			return false;
    }

	@Override
	public List<User> findUserByEmployeeid(int employeeid) {
        return userRepo.findByEmployeeid(employeeid);
	}

	@Override
	public List<User> sortUserByEmployeeid() {
		return userRepo.findAll(orderByEmployeeidAsc());
	}

	@Override
	public List<User> sortUserByFirstname() {
		return userRepo.findAll(orderByFirstnameAsc());
	}

	@Override
	public List<User> sortUserByLastname() {
		return userRepo.findAll(orderByLastnameAsc());
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return userRepo.findAll();
	}

	@Override
	public User findUserById(long id) {
		Optional<User> user;
		user = userRepo.findById(id);
		if(user.isPresent()) {
			return user.get();
		}else {
			return null;
		}
	}

	@Override
	public User findUserByTaskid(long taskid) {
		// TODO Auto-generated method stub
		return userRepo.findByTask(taskRepo.findById(taskid).get());
	}

	@Override
	public int updateUserByProejctAndTask(long projectid) {
		// TODO Auto-generated method stub
		return userRepo.updateUserByProejctAndTask(projectid);
	}
	


}