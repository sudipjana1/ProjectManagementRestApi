package com.springboot.mvc.security.login.service;

import com.springboot.mvc.security.login.model.User;

import java.util.List;

import org.springframework.data.domain.Sort;



public interface UserService {

    public List<User> findUserByEmployeeid(int employeeid); 
    public User findUserById(long id); 
    public User findUserByTaskid(long taskid);


    public User saveUser(User user);
    public int updateUserByProejctAndTask(long projectid);
    
    public boolean deleteUser(long userid);
    public List<User> findAll(); 

    public List<User> sortUserByEmployeeid(); 
    public List<User> sortUserByFirstname(); 
    public List<User> sortUserByLastname(); 


}