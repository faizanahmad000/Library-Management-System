package com.lms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.model.Category;
import com.lms.repository.CategoryRepository;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
@Autowired
private CategoryRepository categoryRepository;
	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll(); 
	}

	@Override
	public Category findByType(String type) {
		return categoryRepository.findByType(type);
	}

	@Override
	public Category save(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public void deleteById(Long id) {
		 categoryRepository.deleteById(id);
		
	}

	@Override
	public Category findById(Long id) {
		return categoryRepository.findById(id).get();
		
	}

}
