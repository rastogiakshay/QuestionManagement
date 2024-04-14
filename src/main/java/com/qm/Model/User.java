package com.qm.Model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_master_table")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;

	@JsonProperty
	private String firstName;
	@JsonProperty
	private String lastName;

	private String email;

	private String password;

	private Date created_at;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>(); // Adding set of Roles, mapped to new table in database

	public User() {
	}

	public User(String first_name, String last_name, String email, String password) {
		super();
		this.firstName = first_name;
		this.lastName = last_name;
		this.email = email;
		this.password = password;

	}

//	public int getId() {
//		return user_id;
//	}
//
//	public void setId(int id) {
//		this.user_id = id;
//	}
//
//	public String getLastName() {
//		return last_name;
//	}
//
//	public void setLastName(String last_name) {
//		this.last_name = last_name;
//	}
//
//	public String getFirstName() {
//		return first_name;
//	}
//
//	public void setFirstName(String first_name) {
//		this.first_name = first_name;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	@JsonIgnore
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public Set<Role> getRoles() {
//		return roles;
//	}
//
//	public void setRoles(Set<Role> roles) {
//		this.roles = roles;
//	}
//
//	public Date getCreatedAt() {
//		return created_at;
//	}
//
//	public void setCreatedAt(Date createdAt) {
//		this.created_at = createdAt;
//	}

}
