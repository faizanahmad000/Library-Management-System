package com.lms.dto;

import java.util.ArrayList;

public class BookDTO {
	private Long id;
	private String title;
	private String datePublished;

	private PublisherDTO publisher;
	private ArrayList<CategoryDTO> categories;

	private ArrayList<AuthorDTO> authors;

	public BookDTO() {

	}

	public BookDTO(Long id, String title, String datePublished, PublisherDTO publisher,
			ArrayList<CategoryDTO> categories, ArrayList<AuthorDTO> authors) {

		this.id = id;
		this.title = title;
		this.datePublished = datePublished;
		this.publisher = publisher;
		this.categories = categories;
		this.authors = authors;
	}

	public ArrayList<CategoryDTO> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<CategoryDTO> categories) {
		this.categories = categories;
	}

	public ArrayList<AuthorDTO> getAuthors() {
		return authors;
	}

	public String getDatePublished() {
		return datePublished;
	}

	public Long getId() {
		return id;
	}

	public PublisherDTO getPublisher() {
		return publisher;
	}

	public String getTitle() {
		return title;
	}

	public void setAuthors(ArrayList<AuthorDTO> authors) {
		this.authors = authors;
	}

	public void setDatePublished(String datePublished) {
		this.datePublished = datePublished;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPublisher(PublisherDTO publisher) {
		this.publisher = publisher;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
