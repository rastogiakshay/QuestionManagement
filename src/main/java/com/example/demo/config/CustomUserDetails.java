package com.example.demo.config;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.Model.User;

import io.jsonwebtoken.lang.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 1L;


	private int user_id;
	private String first_name;
	private String last_name;
	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	public CustomUserDetails(int id, String firstName, String lastName, String Email,String password,
			Collection<?extends GrantedAuthority> auth) {
	
		this.user_id = id;
		this.first_name= firstName;
		this.last_name=lastName;
		this.email = Email;
		this.password = password;
		this.authorities = auth;
	}
	
	// Extracting role of every login.
	public static CustomUserDetails buildIt(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());
		
		return new CustomUserDetails(user.getId(),
				user.getFirstName(),
				user.getLastName(),
				user.getEmail(),
				user.getPassword(),
				authorities);
		
	}
	
	public int getUser_id() {
		return user_id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	@Override
	public String getPassword() {

		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj){
			return true;
		}if(obj == null || getClass() != obj.getClass()) {
			return false;
		}
		
		CustomUserDetails user = (CustomUserDetails) obj;
		return java.util.Objects.equals(user_id, user.user_id);
	}

}
