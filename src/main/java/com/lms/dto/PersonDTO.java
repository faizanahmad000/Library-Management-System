package com.lms.dto;

import com.lms.model.Role;

public class PersonDTO {
	private Long id;
	private String name;
	private String email;
	private Long rollNo;
	private String registryDate;
	private String expiryDate;
	private Role role;
	private String password;

	public PersonDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PersonDTO(Long id, String name, String email, Long rollNo, String registryDate, String expiryDate) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.rollNo = rollNo;
		this.registryDate = registryDate;
		this.expiryDate = expiryDate;
	}

	public PersonDTO(Long id, String name, String email, Long rollNo, String registryDate, String expiryDate, Role role,
			String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.rollNo = rollNo;
		this.registryDate = registryDate;
		this.expiryDate = expiryDate;
		this.role = role;
		this.password = password;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getRollNo() {
		return rollNo;
	}

	public void setRollNo(Long rollNo) {
		this.rollNo = rollNo;
	}

	public String getRegistryDate() {
		return registryDate;
	}

	public void setRegistryDate(String registryDate) {
		this.registryDate = registryDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
