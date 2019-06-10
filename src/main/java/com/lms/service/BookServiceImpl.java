package com.lms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lms.model.Author;
import com.lms.model.Book;
import com.lms.model.Category;
import com.lms.model.Publisher;
import com.lms.repository.BookRepository;

@Service("bookService")
public class BookServiceImpl implements BookService {
	@Autowired
	private BookRepository bookRepository;

	@Override
	@Transactional
	public Book save(Book book) {
		return bookRepository.save(book);
	}

	@Override
	public List<Book> findAll() {
		return bookRepository.findAll();
	}

	@Override
	public List<Book> findByTitle(String title) {
	return bookRepository.findByTitle( title);
	}

	@Override
	public List<Book> findByDatePublished(String datePublished) {
		return bookRepository.findByDatePublished(datePublished);
	}

	@Override
	public List<Book> findByPublisher(Publisher pub) {
		return bookRepository.findByPublisher(pub);
	}

	@Override
	public List<Book> findByAuthors(List<Author> auths) {
		return bookRepository.findByAuthors(auths);
	}

	@Override
	public List<Book> findByCategories(Category cats) {
		return bookRepository.findByCategories(cats);
	}

	@Override
	public Book findById(Long id) {
		return bookRepository.findById(id).get();
	}

	@Override
	public void deleteById(Long id) {
		bookRepository.deleteById(id);
		
	}

}
