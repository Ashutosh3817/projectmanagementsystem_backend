package com.pms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Issue { 
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	//that means that many users have same issue
	@ManyToOne
	private User assignee; // corrected spelling
	
	//many issues has same project so let's give relationshio many-to-one
	@ManyToOne
	private Project project;

	
}
