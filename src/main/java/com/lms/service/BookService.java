package com.lms.service;

import java.util.List;

import javax.validation.Valid;

import com.lms.model.Author;
import com.lms.model.Book;
import com.lms.model.Category;
import com.lms.model.Publisher;

public interface BookService {

	Book save(@Valid Book book);

	List<Book> findAll();

	List<Book> findByTitle(String title);

	List<Book> findByDatePublished(String datePublished);

	List<Book> findByPublisher(Publisher pub);

	List<Book> findByAuthors(List<Author> auths);

	List<Book> findByCategories(Category cats);

	Book findById(Long id);

	void deleteById(Long id);

}
