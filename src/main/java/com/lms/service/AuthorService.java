package com.lms.service;

import java.util.List;

import com.lms.model.Author;

public interface AuthorService {

	List<Author> findAll();

	Author findByName(String name);

	Author save(Author author);

	Author findById(Long id);

	void deleteById(Long id);

}
