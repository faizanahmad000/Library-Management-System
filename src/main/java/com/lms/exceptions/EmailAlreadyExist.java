package com.lms.exceptions;

import javax.naming.AuthenticationException;

public class EmailAlreadyExist extends AuthenticationException {
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	String status;
	public EmailAlreadyExist(String status,final String msg) {
		super(msg);
		this.status=status;
		
	}
}
