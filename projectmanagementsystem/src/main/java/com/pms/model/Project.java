package com.pms.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="project")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
	@Id
	@GeneratedValue(strategy  = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false,name="project_name")
	private String name;
	@Column(name="desc")
	private String description;
	
	private String category;
	
//	we provide the chat reference to each project so the Project and chat has one-to-one relationship
//  because each project has one chat and vice-versa
	
	//@ElementCollection
	//it will create the seperate table for tags 
	private List<String> tags = new ArrayList<>();
	
	//since we mapped by project it will not create seperate chat for project everytime instead we do perform operation in one chat
	@JsonIgnore
	@OneToOne(mappedBy="project",cascade = CascadeType.ALL,orphanRemoval = true)
	private Chat chat;
	
	//project lead is owner means who created our project
    //ManyToOne because one user can create many project and many project has one user so we provide many-to-one relationship
	@ManyToOne
	private User owner;
	
	//one project has multiple issues so we write OneToMany annotation in issues
	@OneToMany(mappedBy = "project",cascade =CascadeType.ALL,orphanRemoval = true)
	private List<Issue> issues = new ArrayList<>();
	
	//there are multiple users works on multiple project 
	@ManyToMany
	private List<User> team = new ArrayList<>();
	
	
	
	
	/*
### CascadeType.ALL
The cascade attribute defines which operations should be cascaded from the parent entity to the associated child entities. The value CascadeType.ALL means that all operations (e.g., persist, merge, remove, refresh, detach) should be cascaded. In other words, when you perform an operation on a parent entity, the same operation will be applied to all its associated child entities. Here's a breakdown of the cascading operations:

- *PERSIST*: When the parent entity is saved, all its children are also saved.
- *MERGE*: When the parent entity is updated, all its children are also updated.
- *REMOVE*: When the parent entity is deleted, all its children are also deleted.
- *REFRESH*: When the parent entity is refreshed from the database, all its children are also refreshed.
- *DETACH*: When the parent entity is detached from the persistence context, all its children are also detached.

### orphanRemoval = true
The orphanRemoval attribute is used to automatically remove child entities that are no longer referenced by the parent entity. 
If orphanRemoval is set to true, when a child entity is removed from the parent entity’s collection (or set to null), it will be 
deleted from the database. This is useful for ensuring data integrity and avoiding orphaned records.

    */
	
	
}
