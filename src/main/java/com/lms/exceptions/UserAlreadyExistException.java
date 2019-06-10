package com.lms.exceptions;

import javax.naming.AuthenticationException;

public class UserAlreadyExistException extends AuthenticationException {
	public UserAlreadyExistException(final String msg) {
		super(msg);
	}
}
