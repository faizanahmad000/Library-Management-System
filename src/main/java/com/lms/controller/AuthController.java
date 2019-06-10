package com.lms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lms.dto.PersonDTO;
import com.lms.exceptions.UserAlreadyExistException;
import com.lms.model.Person;
import com.lms.service.AuthenticationService;
import com.lms.util.ErrorMessage;
import com.lms.util.FieldErrorMessage;

@Scope("session")
@RestController
public class AuthController {
	@Autowired
	private AuthenticationService authenticationService;

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	List<FieldErrorMessage> exceptionHandler(MethodArgumentNotValidException e) {
		List<FieldError> fieldError2 = e.getBindingResult().getFieldErrors();
		List<FieldErrorMessage> fieldErrorMessages = fieldError2.stream()
				.map(fieldError -> new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage()))
				.collect(Collectors.toList());
		return fieldErrorMessages;
	}

	@PostMapping("/login")
	public PersonDTO loginUser(@RequestBody Person person, HttpServletRequest request) throws ValidationException {
		ArrayList<String> sessionVar;
		sessionVar = (ArrayList<String>) request.getSession().getAttribute("lUser");

		if (sessionVar == null) {

			Person temp = authenticationService.login(person.getEmail(), person.getPassword());
			if (temp != null) {
				sessionVar = new ArrayList<>();
				sessionVar.add(temp.getEmail());
				sessionVar.add(temp.getRole().getType());
				request.getSession().setAttribute("lUser", sessionVar);
				return new PersonDTO(temp.getId(),temp.getName(),temp.getEmail(),temp.getRollNo(),temp.getRegistryDate(),temp.getExpiryDate(),temp.getRole(),temp.getPassword());
			} else {
				throw new ValidationException("Invalid User Name And Passwrod");
			}

		} else {
			throw new ValidationException("You are already logged in");
		}

	}

	@GetMapping("/logout")
	public ResponseEntity<ErrorMessage> logoutUser(HttpServletRequest request) {
		ArrayList<String> sessionVar;
		sessionVar = (ArrayList<String>) request.getSession().getAttribute("lUser");
		if (sessionVar == null) {
			return new ResponseEntity<ErrorMessage>(new ErrorMessage("400", "You Need to Login First"),
					HttpStatus.BAD_REQUEST);
		}
		request.getSession().invalidate();
//		request.getSession().setAttribute("lUser", null);
//		System.out.println(session);
		return new ResponseEntity<ErrorMessage>(new ErrorMessage("200", "Ok"), HttpStatus.OK);

	}

	@PostMapping("/registration")
	public PersonDTO regUser(@Valid @RequestBody Person person, HttpServletRequest request)
			throws UserAlreadyExistException {
		ArrayList<String> sessionVar;
		sessionVar = (ArrayList<String>) request.getSession().getAttribute("lUser");
		if (sessionVar == null) {
			Person temp = authenticationService.findByEmail(person.getEmail());

			if (temp == null) {
				Person per = authenticationService.save(person);
				sessionVar = new ArrayList<String>();
				sessionVar.add(per.getEmail());
				sessionVar.add(per.getRole().getType());
				request.getSession().setAttribute("lUser", sessionVar);
				
				return new PersonDTO(per.getId(),per.getName(),per.getEmail(),per.getRollNo(),per.getRegistryDate(),per.getExpiryDate(),per.getRole(),per.getPassword());
			} else {
				throw new UserAlreadyExistException("User already exist");
			}
		} else {
			throw new ValidationException("You need to logout first");
		}

	}
}
