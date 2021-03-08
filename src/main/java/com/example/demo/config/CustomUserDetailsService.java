package com.example.demo.config;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Autowired
	UserRepository userRepo;

	/**
	 * Getting user by their email.
	 */
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.getUserByEmail(email);
		if (user == null) {
			LOG.log(Level.INFO, "the user is not found");
			throw new UsernameNotFoundException("User Not Found");
		}
		return CustomUserDetails.buildIt(user);
	}

	/**
	 * looking for that email taken.
	 * 
	 * @param Email
	 * @return
	 */
	public boolean isUsersEmailTaken(String Email) {
		User user = userRepo.getUserByEmail(Email);
		if (user != null) {
			return true;
		}
		return false;
	}
}
