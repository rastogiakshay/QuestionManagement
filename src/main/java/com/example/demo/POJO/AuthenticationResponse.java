package com.example.demo.POJO;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse implements Serializable{
	public String getAccessToken() {
		return accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String accessToken;
	private final String refreshToken;
	public AuthenticationResponse(String accessToken, String refreshToken) {
		super();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
	
}
