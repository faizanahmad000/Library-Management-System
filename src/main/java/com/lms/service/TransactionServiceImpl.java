package com.lms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lms.model.Book;
import com.lms.model.Person;
import com.lms.model.Transaction;
import com.lms.repository.TransactionRepository;

@Service("transactionService")
public class TransactionServiceImpl implements TransactionService {
@Autowired
private TransactionRepository transactionRepository;
	@Override
	public Transaction save(Transaction transaction) {
		return transactionRepository.save(transaction);
	}
	@Override
	public Transaction findById(Long id) {
		return transactionRepository.findById(id).get(); 
	}
	@Override
	public Transaction findByBook(Book book) {
		return transactionRepository.findByBook(book);
	}
	@Override
	public List<Transaction> findByPerson(Person personTemp) {
		return transactionRepository.findByPerson(personTemp);
	}
	@Override
	public void deleteById(Long id) {
		
		transactionRepository.deleteById(id);
		
	}
	@Override
	public List<Transaction> findAll() {
		return transactionRepository.findAll();
	}
	@Override
	public void delete(Transaction transc) {
		transactionRepository.delete(transc);
		
	}
	@Override
	public List<Transaction> findByExpiryDate(String expiryDate) {
		return transactionRepository.findByExpiryDate(expiryDate);
	}

}
