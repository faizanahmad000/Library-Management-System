package com.lms.exceptions;

import javax.naming.AuthenticationException;

public class LoginFirst extends AuthenticationException {
	public LoginFirst(final String msg) {
		super(msg);
	}
}
