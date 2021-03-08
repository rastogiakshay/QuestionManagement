package com.example.demo.Service;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Model.EAuthRoles;
import com.example.demo.Model.Role;
import com.example.demo.Model.User;
import com.example.demo.POJO.AuthenticationResponse;
import com.example.demo.Payload.JwtResponse;
import com.example.demo.Payload.LoginRequest;
import com.example.demo.Payload.RegisterRequest;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.config.CustomUserDetails;
import com.example.demo.config.CustomUserDetailsService;
import com.example.demo.jwt.JwtUtility;

import Utility.GlobalConstants;
import io.jsonwebtoken.ExpiredJwtException;

@Service
public class UserService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	RoleRepository roleRepo;

	@Autowired
	JwtUtility jwtUtils;

	@Autowired
	AuthenticationManager authManager;

	@Autowired
	CustomUserDetailsService service;

	private Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public Iterable<User> getAllUser() {
		return userRepo.findAll();
	}

	public User getUserByEmail(String emailId) {
		return userRepo.getUserByEmail(emailId);
	}

	/**
	 * This method is to save/register new user.
	 * 
	 * @param register
	 * @return
	 */
	public ResponseEntity<?> registerUser(RegisterRequest register) {

		User tempUser = userRepo.getUserByEmail(register.getEmail());
		if (tempUser != null) {
			return ResponseEntity.badRequest().body(GlobalConstants.EMAIL_ALREADY_EXIST);
		}
		if (!(register.getFirstName().matches(GlobalConstants.ONLY_ALPHABET_REGEX)
				&& register.getLastName().matches(GlobalConstants.ONLY_ALPHABET_REGEX))) {
			return ResponseEntity.badRequest().body(GlobalConstants.ONLY_ALPHABET);

		}
		User user = new User(register.getFirstName().replaceAll(GlobalConstants.TRIM_SPACES_REGEX, ""),
				register.getLastName().replaceAll(GlobalConstants.TRIM_SPACES_REGEX, ""),
				register.getEmail().replaceAll(GlobalConstants.TRIM_SPACES_REGEX, ""),
				encoder.encode(register.getPassword()));

		Set<String> striRole = register.getRole();

		Set<Role> roleSet = new HashSet<>();

		if (striRole == null) {

			Role userRole = roleRepo.findByName(EAuthRoles.ROLE_USER)
					.orElseThrow(() -> new RuntimeException(GlobalConstants.NO_ROLE_FOUND));

			roleSet.add(userRole);

		} else {
			striRole.forEach(role -> {
				if (role == "admin") {

					Role adminRole = roleRepo.findByName(EAuthRoles.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("No Admin Role is Found"));
					roleSet.add(adminRole);
					LOG.log(Level.INFO, "This is the buffer ");

				} else {

					Role userRole = roleRepo.findByName(EAuthRoles.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("No User Role is found"));
					LOG.log(Level.INFO, "This is the USER_ROLE");
					roleSet.add(userRole);
				}
			});
		}
		user.setRoles(roleSet);

		long millis = System.currentTimeMillis();

		Date date = new Date(millis);

		user.setCreated_at(date);

		userRepo.save(user);

		return ResponseEntity.ok(GlobalConstants.REGISTER_SUCCESS);
	}

	/**
	 * This method is used to authenticate user.
	 * 
	 * @param loginRequest
	 * @return
	 */
	public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
		UsernamePasswordAuthenticationToken userPassAuth = new UsernamePasswordAuthenticationToken(
				loginRequest.getEmail(), loginRequest.getPassword());
		Authentication authentication;
		try {
			authentication = authManager.authenticate(userPassAuth);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
			String jwtAccessToken = jwtUtils.generateJwtToken(userDetails);
			String jwtRefreshToken = jwtUtils.generateJwtRefreshToken(userDetails);
			java.util.Date jwtExpiration = jwtUtils.getJwtExpiration(jwtAccessToken);
			List<String> roles = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
					.collect(Collectors.toList());

			return ResponseEntity.ok(new JwtResponse(jwtAccessToken, jwtRefreshToken, userDetails.getUser_id(),
					userDetails.getFirst_name(), userDetails.getLast_name(), userDetails.getEmail(), jwtExpiration,
					roles));

		} catch (BadCredentialsException e) {
			User user = userRepo.getUserByEmail(loginRequest.getEmail());
			if (user == null) {
				return ResponseEntity.badRequest().body(GlobalConstants.EMAIL_NOT_FOUND);
			} else {
				return ResponseEntity.badRequest().body(GlobalConstants.WRONG_CREDENTIALS);
			}
		}
	}

	/**
	 * This method is to get refresh tokens.
	 * 
	 * @param requestToken
	 * @return
	 */
	public ResponseEntity<?> refreshToken() {
		String accessToken = null;
		String refreshToken = null;
		AuthenticationResponse authResponse = null;
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails user = (CustomUserDetails) service.loadUserByUsername(authentication.getName());
			Authentication newAuthentication = new UsernamePasswordAuthenticationToken(user.getEmail(),
					authentication.getCredentials(), user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(newAuthentication);
			accessToken = jwtUtils.generateJwtToken(user);
			refreshToken = jwtUtils.generateJwtRefreshToken(user);
			authResponse = new AuthenticationResponse(accessToken, refreshToken);
		} catch (ExpiredJwtException e) {
			ResponseEntity.badRequest().body("Token Expired");
		}
		return ResponseEntity.ok(authResponse);
	}
}
