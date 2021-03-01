package com.example.demo.Controller;


import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.User;
import com.example.demo.POJO.AuthenticationResponse;
import com.example.demo.Payload.LoginRequest;
import com.example.demo.Payload.RegisterRequest;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
public class UserController {

	
	@Autowired
	UserRepository userRepo;

	@Autowired
	UserService userService;


	private Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


	@GetMapping("/all-user")
	public Iterable<User> geAllUser() {
		return userRepo.findAll();
	}

	/**
	 * This method is used to get particular user.
	 * @param emailId
	 * @return
	 */
	@GetMapping("/user/{emailId}")
	public User getUserByEmail(@PathVariable String emailId) {
		return userRepo.getUserByEmail(emailId);
		}

	/**
	 * This method is use for signing.
	 * @param loginRequest
	 * @return
	 */
	@PostMapping("/signin")
	public ResponseEntity<?> signinUser(@RequestBody LoginRequest loginRequest) {
		return userService.authenticateUser(loginRequest);
	}

	/**
	 * This method is used for registering new user.
	 * @param register
	 * @return
	 */
	
	@PostMapping("/register")
	@Transactional
	public ResponseEntity<?> registerUser(@RequestBody RegisterRequest register) {
		return userService.registerUser(register);
	}

	/**
	 * This method is used for fetching refresh token.
	 * @return
	 */
	@GetMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody String refreshToken) {
		return userService.refreshToken(refreshToken);

	}

}
