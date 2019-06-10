package com.lms.service;

import java.util.List;

import com.lms.model.Category;

public interface CategoryService {

	List<Category> findAll();

	Category findByType(String type);

	Category save( Category category);

	void deleteById(Long id);

	Category findById(Long id);


}
