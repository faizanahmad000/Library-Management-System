package com.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.model.Publisher;

@Repository("publisherRepository")
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
	Publisher findByName(String name);
}
