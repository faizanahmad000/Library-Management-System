package com.lms.service;

import java.util.List;

import com.lms.model.Book;
import com.lms.model.Person;
import com.lms.model.Transaction;

public interface TransactionService {

	Transaction save(Transaction transaction);

	Transaction findById(Long id);

	Transaction findByBook(Book book);

	List<Transaction> findByPerson(Person personTemp);

	void deleteById(Long id);

	List<Transaction> findAll();

	void delete(Transaction transc);

	List<Transaction> findByExpiryDate(String expiryDate);

}
