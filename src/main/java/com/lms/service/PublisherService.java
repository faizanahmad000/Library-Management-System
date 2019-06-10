package com.lms.service;

import java.util.List;

import com.lms.model.Publisher;

public interface PublisherService {

	List<Publisher> findAll();

	Publisher findByName(String name);

	Publisher save( Publisher publisher);

	void deleteById(Long id);

	Publisher findById(Long id);

}
