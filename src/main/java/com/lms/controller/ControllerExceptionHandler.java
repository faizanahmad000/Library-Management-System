package com.lms.controller;

import java.util.NoSuchElementException;

import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lms.exceptions.EmailAlreadyExist;
import com.lms.exceptions.LoginFirst;
import com.lms.exceptions.UserAlreadyExistException;
import com.lms.util.ErrorMessage;

@ControllerAdvice
public class ControllerExceptionHandler {
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler
	ErrorMessage exceptionHandler (ValidationException e) {
		return new ErrorMessage("400", e.getMessage());
	}
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler
	ErrorMessage notFoundException (NoSuchElementException e) {
		return new ErrorMessage("500", e.getMessage());
	}
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler
	ErrorMessage exceptionUserAlreadyExist (UserAlreadyExistException e) {
		return new ErrorMessage("400", e.getMessage());
	}
	@ResponseBody
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler
	ErrorMessage loginFirst (LoginFirst e) {
		return new ErrorMessage("403", e.getMessage());
	}
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_MODIFIED)
	@ExceptionHandler
	ErrorMessage exceptionEmailAlreadyExist (EmailAlreadyExist e) {
		return new ErrorMessage(e.getStatus(), e.getMessage());
	}

}
