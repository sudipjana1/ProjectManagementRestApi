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
@SequenceGenerator(name="PROJECT_SEQ", sequenceName="project_sequence",allocationSize=1)
@Table(name="projects")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="PROJECT_SEQ")
	@Column(name="project_id")
	long projectid;
	@Column(name="project_name")
	String projectname;
	@Column(name="start_date")
	@Temporal(TemporalType.DATE)
	Date startdate;
	@Column(name="end_date")
	@Temporal(TemporalType.DATE)
	Date enddate;
	@Column(name="priority")
	int priority;
/*	@OneToMany(cascade=CascadeType.REMOVE, mappedBy="project", fetch=FetchType.LAZY)
	List<Task> tasks;
	@OneToOne(cascade=CascadeType.REMOVE, mappedBy="project", fetch=FetchType.LAZY)
	User user;
	
*/

	public Project() {
	}
	




	public Project(long projectid, String projectname, Date startdate, Date enddate, int priority) {
		super();
		this.projectid = projectid;
		this.projectname = projectname;
		this.startdate = startdate;
		this.enddate = enddate;
		this.priority = priority;

	}





	public long getProjectid() {
		return projectid;
	}
	public void setProjectid(long projectid) {
		this.projectid = projectid;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
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


//
//	@Override
//	public String toString() {
//		return "Project [projectid=" + projectid + ", projectname=" + projectname + ", startdate=" + startdate
//				+ ", enddate=" + enddate + ", priority=" + priority + ", tasks=" + tasks + "]";
//	}
	

	

}
