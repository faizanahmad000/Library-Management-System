package com.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.model.Author;
import com.lms.model.Book;
import com.lms.model.Category;
import com.lms.model.Publisher;

@Repository("bookRepository")
public interface BookRepository extends JpaRepository<Book, Long> {
	List<Book> findByTitle(String title);
	List<Book> findByDatePublished(String datePublished);
	List<Book> findByPublisher(Publisher publisher);
	List<Book> findByAuthors(List<Author> authors);
	List<Book> findByCategories(Category categories);
}
