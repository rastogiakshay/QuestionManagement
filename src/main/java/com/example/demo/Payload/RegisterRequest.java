package com.example.demo.Payload;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterRequest {
	
	@JsonProperty
	private String first_name;
	
	@JsonProperty
	private String last_name;
	
	@JsonProperty
	private String email;
	
	@JsonProperty
	private String password;
	
	
	private Set<String> role;

	public String getFirstName() {
		return first_name;
	}

	public void setFirstName(String firstName) {
		this.first_name = firstName;
	}

	public String getLastName() {
		return last_name;
	}

	public void setLastName(String lastName) {
		this.last_name = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getRole() {
		return role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}
	
//	
	
	
	

}
