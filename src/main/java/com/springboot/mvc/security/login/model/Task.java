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
@SequenceGenerator(name="TASK_SEQ", sequenceName="task_sequence",allocationSize=1)
@Table(name="tasks")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="TASK_SEQ")
	@Column(name="task_id")
	long taskid;
	@Column(name="task_name")
	String taskname;
	@Column(name="task_start_date")
	@Temporal(TemporalType.DATE)
	Date startdate;
	@Column(name="task_end_date")
	@Temporal(TemporalType.DATE)
	Date enddate;
	@Column(name="task_priority")
	int priority;
	@Column(name="task_status")
	int active;
	@ManyToOne(cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
	@JoinColumn(name= "project_id", referencedColumnName="project_id")
	Project project;
	@ManyToOne(cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
	@JoinColumn(name= "parent_id", referencedColumnName="parent_id")
	ParentTask parenttask;
	/*@OneToOne(cascade=CascadeType.REMOVE, mappedBy="task", fetch=FetchType.LAZY)
	User user;*/
	/*@ManyToOne(cascade=CascadeType.MERGE)
	List<User> user;*/
	/*@ManyToMany(cascade=CascadeType.MERGE)
	@JoinTable(name="subjectsbooks",joinColumns= {@JoinColumn(name="bookId")},inverseJoinColumns= {@JoinColumn(name="subjectId")})
	List<Subject> subjects;*/
	


	public Task() {
	}




	public Task(long taskid, String taskname, Date startdate, Date enddate, int priority, int active, Project project,
			ParentTask parenttask) {
		super();
		this.taskid = taskid;
		this.taskname = taskname;
		this.startdate = startdate;
		this.enddate = enddate;
		this.priority = priority;
		this.active = active;
		this.project = project;
		this.parenttask = parenttask;
	}




	public long getTaskid() {
		return taskid;
	}



	public void setTaskid(long taskid) {
		this.taskid = taskid;
	}



	public String getTaskname() {
		return taskname;
	}



	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}



	public Date getStartdate() {
		return startdate;
	}



	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}



	public Date getEnddate() {
		return enddate;
	}



	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}



	public int getPriority() {
		return priority;
	}



	public void setPriority(int priority) {
		this.priority = priority;
	}



	public int isActive() {
		return active;
	}



	public void setActive(int active) {
		this.active = active;
	}



	public Project getProject() {
		return project;
	}



	public void setProject(Project project) {
		this.project = project;
	}



	public ParentTask getParenttask() {
		return parenttask;
	}



	public void setParenttask(ParentTask parenttask) {
		this.parenttask = parenttask;
	}




	public int getActive() {
		return active;
	}



//	@Override
//	public String toString() {
//		return "Task [bookId=" + taskid + ", taskname=" + taskname + ", startdate=" + startdate + ", enddate=" + enddate
//				+ ", priority=" + priority + ", active=" + active + ", project=" + project + ", parenttask="
//				+ parenttask + "]";
//	}



	

}
