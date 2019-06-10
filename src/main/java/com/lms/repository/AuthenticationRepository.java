package com.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.model.Person;

@Repository("authenticationRepository")
public interface AuthenticationRepository extends JpaRepository<Person, Long> {
	Person findByEmailAndPassword(String email,String password);
	Person findByEmail(String email);
}
