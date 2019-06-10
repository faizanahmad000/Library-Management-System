package com.lms.dto;

public class AuthorDTO {
	private Long id;

	private String name;

	public AuthorDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public AuthorDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
