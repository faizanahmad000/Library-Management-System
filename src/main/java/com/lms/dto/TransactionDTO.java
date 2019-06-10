package com.lms.dto;

public class TransactionDTO {
	private Long id;
	private String issueDate;
	private String expiryDate;
	private PersonDTO person;
	private BookDTO book;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public PersonDTO getPerson() {
		return person;
	}
	public void setPerson(PersonDTO person) {
		this.person = person;
	}
	public BookDTO getBook() {
		return book;
	}
	public void setBook(BookDTO book) {
		this.book = book;
	}
	public TransactionDTO(Long id, String issueDate, String expiryDate, PersonDTO person, BookDTO book) {
		super();
		this.id = id;
		this.issueDate = issueDate;
		this.expiryDate = expiryDate;
		this.person = person;
		this.book = book;
	}
	public TransactionDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
