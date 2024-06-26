package com.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pms.model.User;


public interface UserRepository extends JpaRepository<User,Long> {
	
	User findByEmail(String email);
}
