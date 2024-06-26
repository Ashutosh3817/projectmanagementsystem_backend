package com.pms.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name="first_name",nullable = false,unique=false)
	private String firstName;

	@Column(name="last_name")
	private String lastName;

	@Column(name="password",nullable=false)
	private String password;

	@Column(name="project_size")
	private int projectSize;

	@Column(name="e_mail")
	private String email;

	// means one user has many issues
	@JsonIgnore
	@OneToMany(mappedBy="assignee", cascade = CascadeType.ALL)
	private List<Issue> assignedIssues = new ArrayList<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getProjectSize() {
		return projectSize;
	}

	public void setProjectSize(int projectSize) {
		this.projectSize = projectSize;
	}

	public List<Issue> getAssignedIssues() {
		return assignedIssues;
	}

	public void setAssignedIssues(List<Issue> assignedIssues) {
		this.assignedIssues = assignedIssues;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", password=" + password
				+ ", projectSize=" + projectSize + ", email=" + email + ", assignedIssues=" + assignedIssues + "]";
	}

	public User(long id, String firstName, String lastName, String email, String password, int projectSize,
			List<Issue> assignedIssues) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.projectSize = projectSize;
		this.assignedIssues = assignedIssues;
		this.email = email;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
}
