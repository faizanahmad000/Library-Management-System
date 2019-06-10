package com.lms.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(scope = Transaction.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String issueDate;
	@NotNull
	private String expiryDate;
	@NotNull
	@ManyToOne
//	@JsonBackReference(value="trans_person")
	private Person person;
	
//	@JsonBackReference(value="trans_book")
	@NotNull
	@OneToOne
	private Book book;

	public Book getBook() {
		return book;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public Long getId() {
		return id;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public Person getPerson() {
		return person;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}
