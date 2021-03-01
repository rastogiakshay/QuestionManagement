package com.example.demo.Payload;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String token;
	
	private String refreshToken;
	private int id;
	
	private String tokenType = "Bearer ";
	
	private String email;
	
	private String first_name;
	
	private String last_name;
	
	private Date expireTime;
	

	private List<String> role;

	public JwtResponse(String token, String refreshToken, int id, String email, String first_name, String last_name,
			Date expireTime, List<String> role) {
		super();
		this.token = token;
		this.setRefreshToken(refreshToken);
		this.id = id;
		this.email = email;
		this.first_name = first_name;
		this.last_name = last_name;
		this.expireTime = expireTime;
		this.role = role;
	}


	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getRole() {
		return role;
	}

	public void setRole(List<String> role) {
		this.role = role;
	}
	
	
	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}


	public String getRefreshToken() {
		return refreshToken;
	}


	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	
	
	
	
}
