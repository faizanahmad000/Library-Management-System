package com.lms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lms.dto.AuthorDTO;
import com.lms.dto.BookDTO;
import com.lms.dto.CategoryDTO;
import com.lms.dto.PersonDTO;
import com.lms.dto.PublisherDTO;
import com.lms.dto.TransactionDTO;
import com.lms.model.Book;
import com.lms.model.Person;
import com.lms.model.Transaction;
import com.lms.service.BookService;
import com.lms.service.MemberService;
import com.lms.service.TransactionService;
import com.lms.util.ErrorMessage;
import com.lms.util.FieldErrorMessage;

@RestController
public class TransactionController {
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private BookService bookService;
	@Autowired
	private MemberService memberService;

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	List<FieldErrorMessage> exceptionHandler(MethodArgumentNotValidException e) {
		List<FieldError> fieldError2 = e.getBindingResult().getFieldErrors();
		List<FieldErrorMessage> fieldErrorMessages = fieldError2.stream()
				.map(fieldError -> new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage()))
				.collect(Collectors.toList());
		return fieldErrorMessages;
	}

	@PostMapping("/transaction")
	public TransactionDTO addTransaction(@Valid @RequestBody Transaction transaction) {
		Transaction temp = transactionService.save(transaction);
		PersonDTO person = new PersonDTO(temp.getPerson().getId(), temp.getPerson().getName(),
				temp.getPerson().getEmail(), temp.getPerson().getRollNo(), temp.getPerson().getRegistryDate(),
				temp.getPerson().getExpiryDate());
		PublisherDTO publisher = new PublisherDTO(temp.getBook().getPublisher().getId(),
				temp.getBook().getPublisher().getName(), temp.getBook().getPublisher().getAddress());
		ArrayList<CategoryDTO> cats = new ArrayList<>();
		CategoryDTO cat;
		for (int i = 0; i < temp.getBook().getCategories().size(); i++) {
			cat = new CategoryDTO(temp.getBook().getCategories().get(i).getId(),
					temp.getBook().getCategories().get(i).getType());
			cats.add(cat);
		}
		ArrayList<AuthorDTO> auths = new ArrayList<>();
		AuthorDTO auth;
		for (int i = 0; i < temp.getBook().getAuthors().size(); i++) {
			auth = new AuthorDTO(temp.getBook().getAuthors().get(i).getId(),
					temp.getBook().getAuthors().get(i).getName());
			auths.add(auth);
		}
		BookDTO book = new BookDTO(temp.getBook().getId(), temp.getBook().getTitle(), temp.getBook().getDatePublished(),
				publisher, cats, auths);
		TransactionDTO trans = new TransactionDTO(temp.getId(), temp.getIssueDate(), temp.getExpiryDate(), person,
				book);
		return trans;

	}

	@GetMapping("/transaction/id/{id}")
	public TransactionDTO getTransactionById(@PathVariable Long id) {
		Transaction temp = transactionService.findById(id);
		PersonDTO person = new PersonDTO(temp.getPerson().getId(), temp.getPerson().getName(),
				temp.getPerson().getEmail(), temp.getPerson().getRollNo(), temp.getPerson().getRegistryDate(),
				temp.getPerson().getExpiryDate());
		PublisherDTO publisher = new PublisherDTO(temp.getBook().getPublisher().getId(),
				temp.getBook().getPublisher().getName(), temp.getBook().getPublisher().getAddress());
		ArrayList<CategoryDTO> cats = new ArrayList<>();
		CategoryDTO cat;
		for (int i = 0; i < temp.getBook().getCategories().size(); i++) {
			cat = new CategoryDTO(temp.getBook().getCategories().get(i).getId(),
					temp.getBook().getCategories().get(i).getType());
			cats.add(cat);
		}
		ArrayList<AuthorDTO> auths = new ArrayList<>();
		AuthorDTO auth;
		for (int i = 0; i < temp.getBook().getAuthors().size(); i++) {
			auth = new AuthorDTO(temp.getBook().getAuthors().get(i).getId(),
					temp.getBook().getAuthors().get(i).getName());
			auths.add(auth);
		}
		BookDTO book = new BookDTO(temp.getBook().getId(), temp.getBook().getTitle(), temp.getBook().getDatePublished(),
				publisher, cats, auths);
		TransactionDTO trans = new TransactionDTO(temp.getId(), temp.getIssueDate(), temp.getExpiryDate(), person,
				book);
		return trans;

	}
	
	
	@GetMapping("/transactions")
	public List<TransactionDTO> getAllTransactions() {
		List<Transaction> transactions=transactionService.findAll();
		List<TransactionDTO> transDTOS=new ArrayList<TransactionDTO>();
		for(int k=0;k<transactions.size();k++) {
			Transaction temp = transactions.get(k);
			PersonDTO person = new PersonDTO(temp.getPerson().getId(), temp.getPerson().getName(),
					temp.getPerson().getEmail(), temp.getPerson().getRollNo(), temp.getPerson().getRegistryDate(),
					temp.getPerson().getExpiryDate());
			PublisherDTO publisher = new PublisherDTO(temp.getBook().getPublisher().getId(),
					temp.getBook().getPublisher().getName(), temp.getBook().getPublisher().getAddress());
			ArrayList<CategoryDTO> cats = new ArrayList<>();
			CategoryDTO cat;
			for (int i = 0; i < temp.getBook().getCategories().size(); i++) {
				cat = new CategoryDTO(temp.getBook().getCategories().get(i).getId(),
						temp.getBook().getCategories().get(i).getType());
				cats.add(cat);
			}
			ArrayList<AuthorDTO> auths = new ArrayList<>();
			AuthorDTO auth;
			for (int i = 0; i < temp.getBook().getAuthors().size(); i++) {
				auth = new AuthorDTO(temp.getBook().getAuthors().get(i).getId(),
						temp.getBook().getAuthors().get(i).getName());
				auths.add(auth);
			}
			BookDTO book = new BookDTO(temp.getBook().getId(), temp.getBook().getTitle(), temp.getBook().getDatePublished(),
					publisher, cats, auths);
			TransactionDTO trans = new TransactionDTO(temp.getId(), temp.getIssueDate(), temp.getExpiryDate(), person,
					book);
			transDTOS.add(trans);
		}

		return transDTOS;

	}
	@GetMapping("/transactions/expirydate")
	public List<TransactionDTO> getAllTransactionsByExpiryDate(@RequestParam(value = "expiryDate", required = true) String expiryDate) {
		List<Transaction> transactions=transactionService.findByExpiryDate(expiryDate);
		List<TransactionDTO> transDTOS=new ArrayList<TransactionDTO>();
		for(int k=0;k<transactions.size();k++) {
			Transaction temp = transactions.get(k);
			PersonDTO person = new PersonDTO(temp.getPerson().getId(), temp.getPerson().getName(),
					temp.getPerson().getEmail(), temp.getPerson().getRollNo(), temp.getPerson().getRegistryDate(),
					temp.getPerson().getExpiryDate());
			PublisherDTO publisher = new PublisherDTO(temp.getBook().getPublisher().getId(),
					temp.getBook().getPublisher().getName(), temp.getBook().getPublisher().getAddress());
			ArrayList<CategoryDTO> cats = new ArrayList<>();
			CategoryDTO cat;
			for (int i = 0; i < temp.getBook().getCategories().size(); i++) {
				cat = new CategoryDTO(temp.getBook().getCategories().get(i).getId(),
						temp.getBook().getCategories().get(i).getType());
				cats.add(cat);
			}
			ArrayList<AuthorDTO> auths = new ArrayList<>();
			AuthorDTO auth;
			for (int i = 0; i < temp.getBook().getAuthors().size(); i++) {
				auth = new AuthorDTO(temp.getBook().getAuthors().get(i).getId(),
						temp.getBook().getAuthors().get(i).getName());
				auths.add(auth);
			}
			BookDTO book = new BookDTO(temp.getBook().getId(), temp.getBook().getTitle(), temp.getBook().getDatePublished(),
					publisher, cats, auths);
			TransactionDTO trans = new TransactionDTO(temp.getId(), temp.getIssueDate(), temp.getExpiryDate(), person,
					book);
			transDTOS.add(trans);
		}

		return transDTOS;

	}

	@GetMapping("/transaction/bookid/{id}")
	public TransactionDTO getTransactionByBookId(@PathVariable Long id) throws NoSuchElementException {
		Book bookTemp = bookService.findById(id);
		if (bookTemp == null) {
			throw new NoSuchElementException("No Book Found");
		}
		Transaction temp = transactionService.findByBook(bookTemp);
		PersonDTO person = new PersonDTO(temp.getPerson().getId(), temp.getPerson().getName(),
				temp.getPerson().getEmail(), temp.getPerson().getRollNo(), temp.getPerson().getRegistryDate(),
				temp.getPerson().getExpiryDate());
		PublisherDTO publisher = new PublisherDTO(temp.getBook().getPublisher().getId(),
				temp.getBook().getPublisher().getName(), temp.getBook().getPublisher().getAddress());
		ArrayList<CategoryDTO> cats = new ArrayList<>();
		CategoryDTO cat;
		for (int i = 0; i < temp.getBook().getCategories().size(); i++) {
			cat = new CategoryDTO(temp.getBook().getCategories().get(i).getId(),
					temp.getBook().getCategories().get(i).getType());
			cats.add(cat);
		}
		ArrayList<AuthorDTO> auths = new ArrayList<>();
		AuthorDTO auth;
		for (int i = 0; i < temp.getBook().getAuthors().size(); i++) {
			auth = new AuthorDTO(temp.getBook().getAuthors().get(i).getId(),
					temp.getBook().getAuthors().get(i).getName());
			auths.add(auth);
		}
		BookDTO book = new BookDTO(temp.getBook().getId(), temp.getBook().getTitle(), temp.getBook().getDatePublished(),
				publisher, cats, auths);
		TransactionDTO trans = new TransactionDTO(temp.getId(), temp.getIssueDate(), temp.getExpiryDate(), person,
				book);
		return trans;

	}

	@GetMapping("/transaction/personid/{id}")
	public List<TransactionDTO> getTransactionByPersonId(@PathVariable Long id) throws NoSuchElementException {
		Person personTemp = memberService.findByID(id).get();
		if (personTemp == null) {
			throw new NoSuchElementException("No Book Found");
		}
		List<Transaction> temps = transactionService.findByPerson(personTemp);
		List<TransactionDTO> transDTOS = new ArrayList<TransactionDTO>();
		for (int k = 0; k < temps.size(); k++) {
			Transaction temp = temps.get(k);
			PersonDTO person = new PersonDTO(temp.getPerson().getId(), temp.getPerson().getName(),
					temp.getPerson().getEmail(), temp.getPerson().getRollNo(), temp.getPerson().getRegistryDate(),
					temp.getPerson().getExpiryDate());
			PublisherDTO publisher = new PublisherDTO(temp.getBook().getPublisher().getId(),
					temp.getBook().getPublisher().getName(), temp.getBook().getPublisher().getAddress());
			ArrayList<CategoryDTO> cats = new ArrayList<>();
			CategoryDTO cat;
			for (int i = 0; i < temp.getBook().getCategories().size(); i++) {
				cat = new CategoryDTO(temp.getBook().getCategories().get(i).getId(),
						temp.getBook().getCategories().get(i).getType());
				cats.add(cat);
			}
			ArrayList<AuthorDTO> auths = new ArrayList<>();
			AuthorDTO auth;
			for (int i = 0; i < temp.getBook().getAuthors().size(); i++) {
				auth = new AuthorDTO(temp.getBook().getAuthors().get(i).getId(),
						temp.getBook().getAuthors().get(i).getName());
				auths.add(auth);
			}
			BookDTO book = new BookDTO(temp.getBook().getId(), temp.getBook().getTitle(),
					temp.getBook().getDatePublished(), publisher, cats, auths);
			TransactionDTO trans = new TransactionDTO(temp.getId(), temp.getIssueDate(), temp.getExpiryDate(), person,
					book);
			transDTOS.add(trans);
		}

		return transDTOS;

	}
	@DeleteMapping("/transaction/{id}")
	public ResponseEntity<ErrorMessage> deleteTransactionById(@PathVariable Long id) throws NoSuchElementException {
		Transaction transc=transactionService.findById(id);
//		if (transc == null) {
//			throw new NoSuchElementException("No Record Found");
//		}
//		transactionService.deleteById(id);
		transactionService.delete(transc);
		return new ResponseEntity<ErrorMessage>(new ErrorMessage("200", "Ok"), HttpStatus.OK);

	}
}
