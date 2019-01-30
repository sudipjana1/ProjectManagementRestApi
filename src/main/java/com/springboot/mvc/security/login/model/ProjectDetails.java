package com.springboot.mvc.security.login.model;

public class ProjectDetails {
	private Project project;
	private long noOfTask;
	private String completed;
	private User user;
	public ProjectDetails() {
		// TODO Auto-generated constructor stub
	}
	
	public ProjectDetails(Project project, long noOfTask, String completed, User user) {
		super();
		this.project = project;
		this.noOfTask = noOfTask;
		this.completed = completed;
		this.user = user;
	}

	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public long getNoOfTask() {
		return noOfTask;
	}
	public void setNoOfTask(long noOfTask) {
		this.noOfTask = noOfTask;
	}

	public String getCompleted() {
		return completed;
	}
	public void setCompleted(String completed) {
		this.completed = completed;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

/*	@Override
	public String toString() {
		return "ProjectDetails [project=" + project + ", noOfTask=" + noOfTask + ", completed=" + completed + ", user="
				+ user + "]";
	}*/
	

}
