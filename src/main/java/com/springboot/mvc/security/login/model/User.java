package com.springboot.mvc.security.login.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@SequenceGenerator(name="USER_SEQ", sequenceName="user_sequence",allocationSize=1)
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="USER_SEQ")
	@Column(name="user_id")
	long userid;
	@Column(name="user_first_name")
	String firstname;
	@Column(name="user_last_name")
	String lastname;
	@Column(name="user_employee_id")
	int employeeid;
	
	@OneToOne(cascade=CascadeType.MERGE, fetch=FetchType.LAZY)
	@JoinColumn(name= "project_id", referencedColumnName="project_id")
	Project project;
	
	@OneToOne(cascade=CascadeType.MERGE,fetch=FetchType.LAZY)
	@JoinColumn(name= "task_id", referencedColumnName="task_id")
	Task task;
	/*@ManyToMany(cascade=CascadeType.MERGE)
	@JoinTable(name="subjectsbooks",joinColumns= {@JoinColumn(name="bookId")},inverseJoinColumns= {@JoinColumn(name="subjectId")})
	List<Subject> subjects;*/
	


	public User() {
	}
	
	
	public User(long userid, String firstname, String lastname, int employeeid, Project project, Task task) {
		super();
		this.userid = userid;
		this.firstname = firstname;
		this.lastname = lastname;
		this.employeeid = employeeid;
		this.project = project;
		this.task = task;
	}

/*	public User(String firstname, String lastname, int employeeid) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.employeeid = employeeid;
		
	}*/

	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public int getEmployeeid() {
		return employeeid;
	}
	public void setEmployeeid(int employeeid) {
		this.employeeid = employeeid;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}

	public Task getTask() {
		return task;
	}


	public void setTask(Task task) {
		this.task = task;
	}

/*
	@Override
	public String toString() {
		return "User [userid=" + userid + ", firstname=" + firstname + ", lastname=" + lastname + ", employeeid="
				+ employeeid + ", project=" + project + ", task=" + task + "]";
	}
*/

	

}
