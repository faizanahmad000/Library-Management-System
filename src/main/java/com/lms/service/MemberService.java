package com.lms.service;

import java.util.List;
import java.util.Optional;

import com.lms.model.Person;
import com.lms.model.Role;

public interface MemberService {
	Role findPersonRole(String email);
	Person findByEmail(String email);
	List<Person> findAll();
	Optional<Person> findByID(Long id);
	Person save( Person person);
	void deleteById(Long id);
	boolean isPersonExist(Long id);
}
