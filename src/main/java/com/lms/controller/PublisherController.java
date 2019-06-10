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

import com.lms.dto.PublisherDTO;
import com.lms.exceptions.UserAlreadyExistException;
import com.lms.model.Publisher;
import com.lms.service.PublisherService;
import com.lms.util.ErrorMessage;
import com.lms.util.FieldErrorMessage;

@RestController
public class PublisherController {
	@Autowired
	private PublisherService publisherService;

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	List<FieldErrorMessage> exceptionHandler(MethodArgumentNotValidException e) {
		List<FieldError> fieldError2 = e.getBindingResult().getFieldErrors();
		List<FieldErrorMessage> fieldErrorMessages = fieldError2.stream()
				.map(fieldError -> new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage()))
				.collect(Collectors.toList());
		return fieldErrorMessages;
	}

	@GetMapping("/publishers")
	public List<PublisherDTO> getAllPublishers() {
		List<PublisherDTO> pubDTO = new ArrayList<>();
		List<Publisher> pubs = publisherService.findAll();
		for (Publisher publisher : pubs) {
			pubDTO.add(new PublisherDTO(publisher.getId(), publisher.getName(), publisher.getAddress()));
		}
		return pubDTO;

	}

	@GetMapping("/publisher/{name}")
	public PublisherDTO getPublisher(@PathVariable String name) throws NoSuchElementException {
		Publisher publisher = publisherService.findByName(name);
		if (publisher != null) {
			return new PublisherDTO(publisher.getId(), publisher.getName(), publisher.getAddress());
		}

		else {
			throw new NoSuchElementException("Not Found");
		}

	}

	@PostMapping("/publisher")
	public PublisherDTO addPublisher(@Valid @RequestBody Publisher publisher) throws UserAlreadyExistException {
		Publisher pub = publisherService.findByName(publisher.getName());
		if (pub == null) {
			Publisher temp= publisherService.save(publisher);
			return new PublisherDTO(temp.getId(), temp.getName(), temp.getAddress());
		} else {
			throw new UserAlreadyExistException("Already Exist");
		}

	}

	@PutMapping("/publisher")
	public PublisherDTO updatePublisher(@Valid @RequestBody Publisher publisher) throws ValidationException {
		if (publisher.getId() == null) {
			throw new ValidationException("Required User ID");
		}
		
		Publisher temp= publisherService.save(publisher);
		return new PublisherDTO(temp.getId(), temp.getName(), temp.getAddress());

	}

	@DeleteMapping("/publisher/{id}")
	public ResponseEntity<ErrorMessage> deletePublisher(@PathVariable Long id) {
		Publisher pub = publisherService.findById(id);
		if (pub == null) {
			throw new ValidationException("Don't Exist");
		} else {
			publisherService.deleteById(id);
			return new ResponseEntity<ErrorMessage>(new ErrorMessage("200", "Ok"), HttpStatus.OK);
		}

	}
}
