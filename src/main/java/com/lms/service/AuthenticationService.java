package com.lms.service;

import com.lms.model.Person;

public interface AuthenticationService {

	Person save(Person person);

	Person login(String email, String password);

	Person findByEmail(String email);

}
