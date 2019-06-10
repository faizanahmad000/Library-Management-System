package com.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.model.Author;

@Repository("authorRepository")
public interface AuthorRepository extends JpaRepository<Author, Long> {
	Author findByName(String name);
}
