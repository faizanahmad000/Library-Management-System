package com.lms.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;



@Entity
@JsonIdentityInfo(scope = Person.class,
	    generator = ObjectIdGenerators.PropertyGenerator.class,
	    property = "id")
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message= "Enter Valid Name")
	private String name;
	@Email
	@Column(unique=true)
	private String email;
	@NotNull
	@Size(min = 8, max = 30)
	private String password;
	@NotNull
	private Long rollNo;
	private String registryDate;
	private String expiryDate;
	@NotNull(message="User Role cannot be empty")
	@Embedded
	private Role role;
//	@JsonManagedReference
//	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OneToMany(mappedBy = "person",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Transaction> transactions;
	public String getEmail() {
		return email;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}
	public String getRegistryDate() {
		return registryDate;
	}
	public Role getRole() {
		return role;
	}
	public Long getRollNo() {
		return rollNo;
	}
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setRegistryDate(String registryDate) {
		this.registryDate = registryDate;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public void setRollNo(Long rollNo) {
		this.rollNo = rollNo;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	

	

	
}