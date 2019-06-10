package com.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.model.Book;
import com.lms.model.Person;
import com.lms.model.Transaction;

@Repository("transactionRepository")
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	Transaction findByBook(Book book);

	List<Transaction> findByPerson(Person person);

	List<Transaction> findByExpiryDate(String expiryDate);

}
