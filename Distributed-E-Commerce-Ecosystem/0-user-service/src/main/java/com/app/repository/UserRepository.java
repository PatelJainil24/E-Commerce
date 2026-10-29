package com.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	
	//fetch userDetails by Username
	// input username
	// output User
	 Optional<User> findByCredentialUsername(String username);
	 
	 // This causes the N+1 query problem : @EntityGraph
	 @EntityGraph(attributePaths = "credential")
	 List<User> findAll();
}
