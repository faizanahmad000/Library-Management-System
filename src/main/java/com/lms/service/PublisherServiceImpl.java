package com.lms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lms.model.Publisher;
import com.lms.repository.PublisherRepository;

@Service("publisherService")
public class PublisherServiceImpl implements PublisherService {
	@Autowired
	private PublisherRepository publisherRepository;

	@Override
	@Transactional
	public List<Publisher> findAll() {
		return publisherRepository.findAll();
	}

	@Override
	@Transactional
	public Publisher findByName(String name) {
		return publisherRepository.findByName(name);
	}

	@Override
	@Transactional
	public Publisher save(Publisher publisher) {
		return publisherRepository.save(publisher);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		 publisherRepository.deleteById(id);
	}

	@Override
	public Publisher findById(Long id) {
		return publisherRepository.findById(id).get();
	}

}
