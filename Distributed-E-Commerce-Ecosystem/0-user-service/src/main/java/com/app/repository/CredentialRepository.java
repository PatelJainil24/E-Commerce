package com.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.models.Credential;

public interface CredentialRepository extends JpaRepository<Credential, Long>{

	//DSL method
	//input : String 
	// output : Credential -> Optional or List of Credential
	Optional<Credential> findByUsername(String username);
}
