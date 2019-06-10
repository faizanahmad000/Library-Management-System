package com.lms.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lms.model.Person;
import com.lms.repository.AuthenticationRepository;

@Service("authenticationService")
public class AuthenticationServiceImpl implements AuthenticationService {
	@Autowired
	private AuthenticationRepository authenticationRepository;

	@Override
	@Transactional
	public Person save(Person person) {
		return authenticationRepository.save(person);

	}

	@Override
	@Transactional
	public Person login(String email, String password) {
		
		return authenticationRepository.findByEmailAndPassword(email, password);
		
	}
	@Override
	@Transactional
	public Person findByEmail(String email) {
		
		return authenticationRepository.findByEmail(email);
		
	}

}
