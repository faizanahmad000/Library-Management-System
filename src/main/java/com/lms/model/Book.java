package com.lms.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(scope = Person.class,
generator = ObjectIdGenerators.PropertyGenerator.class,
property = "id")

public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String title;
	private String datePublished;

//	@ManyToOne(cascade = CascadeType.ALL)
	/*
	 * if i use cascase all then suppose if  i update book and while 
	 * updating i change publisher from pearson to pearson2 then if  i am using
	 * cascadeall on manytoone then pearson2 will be updated in all references in db 
	 * if i dont use cascasdeall here the whereever i wrote on publisher
	 * it will pick pulisher from id from publisher etity
	 * although it will return update publisher json pyaload
	 * but in actual it will not change publisher is cascase all is not used.
	 */
	@ManyToOne
//	@JsonBackReference(value="book_pub")
	private Publisher publisher;

	@NotNull
	@ManyToMany
//	@JsonBackReference(value="book_auth")
	private List<Author> authors;

	@NotNull
	@ManyToMany
//	@JsonBackReference(value="book_cat")
	private List<Category> categories;
	
//	@JsonManagedReference
//	@OneToOne(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	/*
	 * if use cascade all in OnetoOne it dont delete transaction 
	 * then
	 * ref link:https://stackoverflow.com/questions/16898085/jpa-hibernate-remove-entity-sometimes-not-working
	 */
	@OneToOne(mappedBy = "book", fetch = FetchType.LAZY)
	private Transaction transactions;

	public List<Author> getAuthors()  {
		return authors;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public String getDatePublished() {
		return datePublished;
	}

	public void setDatePublished(String datePublished) {
		this.datePublished = datePublished;
	}

	public Long getId() {
		return id;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public String getTitle() {
		return title;
	}

	public Transaction getTransactions() {
		return transactions;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTransactions(Transaction transactions) {
		this.transactions = transactions;
	}

}
