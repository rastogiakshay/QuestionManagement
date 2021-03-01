package com.example.demo.Model;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="user_master_table")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int user_id;
	
	@JsonProperty
	private String first_name;
	@JsonProperty
	private String last_name;

	private String email;

	private String password;
	

	private Date created_at;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles",
			   joinColumns = @JoinColumn(name = "user_id"),						
			   inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();							// Adding set of Roles, mapped to new table in database
//	
//	private boolean is_active;

	public User() {}

	public User(String first_name, String last_name, String email, String password) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.password = password;
//		this.setUser_created(created_on());
//		this.is_active = true;
	}

	public int getId() {
		return user_id;
	}

	public void setId(int id) {
		this.user_id = id;
	}

	public String getLastName() {
		return last_name;
	}

	public void setLastName(String last_name) {
		this.last_name = last_name;
	}

	public String getFirstName() {
		return first_name;
	}

	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
//	public static String created_on() {
//		
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
//	   LocalDateTime now = LocalDateTime.now();  
//	   return dtf.format(now);  
//	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Date getCreatedAt() {
		return created_at;
	}

	public void setCreatedAt(Date createdAt) {
		this.created_at = createdAt;
	}


	
}
	
	