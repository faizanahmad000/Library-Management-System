package com.lms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lms.dto.PersonDTO;
import com.lms.exceptions.LoginFirst;
import com.lms.model.Person;
import com.lms.model.Role;
import com.lms.service.MemberService;
import com.lms.util.FieldErrorMessage;

@RestController
public class MembersController {
	@Autowired
	private MemberService memberService;

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	List<FieldErrorMessage> exceptionHandler(MethodArgumentNotValidException e) {
		List<FieldError> fieldError2 = e.getBindingResult().getFieldErrors();
		List<FieldErrorMessage> fieldErrorMessages = fieldError2.stream()
				.map(fieldError -> new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage()))
				.collect(Collectors.toList());
		return fieldErrorMessages;
	}

	@GetMapping("/viewMembers")
	public List<PersonDTO> regUser(HttpServletRequest request) throws LoginFirst {
		ArrayList<String> sessionVar;
		sessionVar = (ArrayList<String>) request.getSession().getAttribute("lUser");
		if (sessionVar == null) {
			throw new LoginFirst("You Need to Login first as admin");
		} else {
			Role role = memberService.findPersonRole(sessionVar.get(0));
			if (role.getType().equalsIgnoreCase("admin")) {
				List<Person> persons= memberService.findAll();
				List<PersonDTO> personDTOS=new ArrayList<PersonDTO>();
				for (Person per : persons) {
					personDTOS.add( new PersonDTO(per.getId(),per.getName(),per.getEmail(),per.getRollNo(),per.getRegistryDate(),per.getExpiryDate(),per.getRole(),per.getPassword()));
				}
				return personDTOS;
				
			} else {
				throw new LoginFirst("You dont have admin priviliges");
			}
		}

	}

	@PutMapping("/updateMember")
	public PersonDTO updateUser(@Valid @RequestBody Person person, HttpServletRequest request)
			throws LoginFirst, ValidationException {

		if (person.getId() == null) {
			throw new ValidationException("Required User ID");
		} else {
			ArrayList<String> sessionVar;
			sessionVar = (ArrayList<String>) request.getSession().getAttribute("lUser");
			if (sessionVar == null) {
				throw new LoginFirst("You Need to Login first");
			} else {

				Optional<Person> p = memberService.findByID(person.getId());
				if (!p.isPresent()) {
					throw new ValidationException("ID not valid");
				} else {
					Person temp = p.get();
					Person sessionPerson = memberService.findByEmail(sessionVar.get(0));
					Role pRole = sessionPerson.getRole();
					if (temp.getEmail().equalsIgnoreCase(person.getEmail())
							&& temp.getRole().getType().equalsIgnoreCase(person.getRole().getType())) {
						if (person.getEmail().equalsIgnoreCase(sessionVar.get(0))
								|| pRole.getType().equalsIgnoreCase("admin")) {

							Person per= memberService.save(person);
							return new PersonDTO(per.getId(),per.getName(),per.getEmail(),per.getRollNo(),per.getRegistryDate(),per.getExpiryDate(),per.getRole(),per.getPassword());
						} else {
							throw new LoginFirst("You Dont Have priviliges to update this person");
						}

					} else {
						throw new LoginFirst("Invalid User Data or email and role cannot be updated");
					}
				}

			}
		}

	}

	@DeleteMapping("/deleteMember")
	public void deleteMember(@RequestParam(value = "id", required = true) Long id, HttpServletRequest request)
			throws LoginFirst, ValidationException {
		ArrayList<String> sessionVar;
		sessionVar = (ArrayList<String>) request.getSession().getAttribute("lUser");
		if (sessionVar == null) {
			throw new LoginFirst("You Need to Login first");
		} else {
			if (memberService.isPersonExist(id)) {
				Person sessionPerson = memberService.findByEmail(sessionVar.get(0));

				if (sessionPerson.getRole().getType().equalsIgnoreCase("admin")) {
					memberService.deleteById(id);
				} else {
					throw new LoginFirst("You Dont have previliges");
				}
			} else {
				throw new ValidationException("Id not valid");
			}

		}
	}
/*
 * handle if not found
 */
	@GetMapping("/searchMember")
	public PersonDTO findByQuery(@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "id", required = false) Long id, HttpServletRequest request)
			throws LoginFirst, ValidationException {

		ArrayList<String> sessionVar;
		sessionVar = (ArrayList<String>) request.getSession().getAttribute("lUser");
		if (sessionVar == null) {
			throw new LoginFirst("You Need to Login first");
		} else {

			Person sessionPerson = memberService.findByEmail(sessionVar.get(0));

			if (sessionPerson.getRole().getType().equalsIgnoreCase("admin")) {
				if (email != null) {
					Person per= memberService.findByEmail(email);
					return new PersonDTO(per.getId(),per.getName(),per.getEmail(),per.getRollNo(),per.getRegistryDate(),per.getExpiryDate(),per.getRole(),per.getPassword());
				} else {
					Optional<Person> p = memberService.findByID(id);
					Person per= p.get();
					return new PersonDTO(per.getId(),per.getName(),per.getEmail(),per.getRollNo(),per.getRegistryDate(),per.getExpiryDate(),per.getRole(),per.getPassword());
				}
			} else {
				throw new LoginFirst("You Dont have previliges");
			}

		}

	}

}
