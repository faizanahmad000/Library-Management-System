package com.lms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.model.Author;
import com.lms.repository.AuthorRepository;

@Service("authorService")
public class AuthorServiceImpl implements AuthorService {
	@Autowired
	private AuthorRepository authorRepository;

	@Override
	public List<Author> findAll() {
		return authorRepository.findAll();
	}

	@Override
	public Author findByName(String name) {
		return authorRepository.findByName(name);
	}

	@Override
	public Author save(Author author) {
		return authorRepository.save(author);
	}

	@Override
	public Author findById(Long id) {
		return authorRepository.findById(id).get();
	}

	@Override
	public void deleteById(Long id) {
		authorRepository.deleteById(id);

	}

}
