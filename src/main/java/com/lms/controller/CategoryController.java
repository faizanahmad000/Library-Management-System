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

import com.lms.dto.CategoryDTO;
import com.lms.dto.PublisherDTO;
import com.lms.model.Category;
import com.lms.service.CategoryService;
import com.lms.util.ErrorMessage;
import com.lms.util.FieldErrorMessage;

@RestController
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	List<FieldErrorMessage> exceptionHandler(MethodArgumentNotValidException e) {
		List<FieldError> fieldError2 = e.getBindingResult().getFieldErrors();
		List<FieldErrorMessage> fieldErrorMessages = fieldError2.stream()
				.map(fieldError -> new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage()))
				.collect(Collectors.toList());
		return fieldErrorMessages;
	}

	@GetMapping("/categories")
	public List<CategoryDTO> getAllCategories() {
		List<CategoryDTO> catDTO = new ArrayList<>();
		List<Category> cats = categoryService.findAll();
		for (Category category : cats) {
			catDTO.add(new CategoryDTO(category.getId(), category.getType()));
		}
		return catDTO;

	}

	@GetMapping("/category/{type}")
	public CategoryDTO getCategory(@PathVariable String type)throws NoSuchElementException {
		Category category= categoryService.findByType(type);
		if (category != null) {
			return new CategoryDTO(category.getId(), category.getType());
		}

		else {
			throw new NoSuchElementException("Not Found");
		}

	}

	@PostMapping("/category")
	public CategoryDTO addCategory(@Valid @RequestBody Category category) throws ValidationException {
		Category cat = categoryService.findByType(category.getType());
		if (cat == null) {
			Category temp=  categoryService.save(category);
			return new CategoryDTO(temp.getId(), temp.getType());
		} else {
			throw new ValidationException("Already Exist");
		}

	}

	@PutMapping("/category")
	public CategoryDTO updateCategory(@Valid @RequestBody Category category) throws ValidationException {
		if (category.getId() == null) {
			throw new ValidationException("Required ID");
		}
		Category temp=  categoryService.save(category);
		return new CategoryDTO(temp.getId(), temp.getType());

	}

	@DeleteMapping("/category/{id}")
	public ResponseEntity<ErrorMessage> deleteCategory(@PathVariable Long id) {
		Category category = categoryService.findById(id);
		if (category == null) {
			throw new ValidationException("Don't Exist");
		} else {
			categoryService.deleteById(id);
			return new ResponseEntity<ErrorMessage>(new ErrorMessage("200", "Ok"), HttpStatus.OK);
		}

	}
}
