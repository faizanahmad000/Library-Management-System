package com.lms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lms.dto.AuthorDTO;
import com.lms.dto.PublisherDTO;
import com.lms.exceptions.UserAlreadyExistException;
import com.lms.model.Author;
import com.lms.model.Publisher;
import com.lms.service.AuthorService;
import com.lms.util.ErrorMessage;
import com.lms.util.FieldErrorMessage;

@RestController
public class AuthorController {
	@Autowired
	private AuthorService authorService;

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	List<FieldErrorMessage> exceptionHandler(MethodArgumentNotValidException e) {
		List<FieldError> fieldError2 = e.getBindingResult().getFieldErrors();
		List<FieldErrorMessage> fieldErrorMessages = fieldError2.stream()
				.map(fieldError -> new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage()))
				.collect(Collectors.toList());
		return fieldErrorMessages;
	}

	@GetMapping("/authors")
	public List<AuthorDTO> getAllAuthors() {
		
		
		List<AuthorDTO> authDTO = new ArrayList<>();
		List<Author> auths = authorService.findAll();
		for (Author author : auths) {
			authDTO.add(new AuthorDTO(author.getId(), author.getName()));
		}
		return authDTO;

	}

	@GetMapping("/author/{name}")
	public AuthorDTO getAuthor(@PathVariable String name) {
		Author author= authorService.findByName(name);
		if (author != null) {
			return new AuthorDTO(author.getId(), author.getName());
		}

		else {
			throw new NoSuchElementException("Not Found");
		}
	}

	@PostMapping("/author")
	public AuthorDTO addAuthor(@Valid @RequestBody Author author) throws UserAlreadyExistException {
		Author aut= authorService.findByName(author.getName());
		if (aut == null) {
			Author temp= authorService.save(author);
			return new AuthorDTO(temp.getId(), temp.getName());
		} else {
			throw new UserAlreadyExistException("Already Exist");
		}

	}

	@PutMapping("/author")
	public AuthorDTO updateAuthor(@Valid @RequestBody Author author) throws ValidationException {
		if (author.getId() == null) {
			throw new ValidationException("Required  ID");
		}
		Author temp= authorService.save(author);
		return new AuthorDTO(temp.getId(), temp.getName());

	}

	@DeleteMapping("/author/{id}")
	public ResponseEntity<ErrorMessage> deleteAuthor(@PathVariable Long id) {
		Author author = authorService.findById(id);
		if (author == null) {
			throw new ValidationException("Don't Exist");
		} else {
			authorService.deleteById(id);
			return new ResponseEntity<ErrorMessage>(new ErrorMessage("200", "Ok"), HttpStatus.OK);
		}

	}
}
