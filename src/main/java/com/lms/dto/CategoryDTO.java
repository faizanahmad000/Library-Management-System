package com.lms.dto;

public class CategoryDTO {
	private Long id;

	private String type;

	public CategoryDTO(Long id, String type) {
		super();
		this.id = id;
		this.type = type;
	}

	public CategoryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	

	
}
