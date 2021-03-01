package com.example.demo.jwt;



import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.demo.config.CustomUserDetails;
import com.example.demo.config.CustomUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtility {
	
	private Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	@Value("${example.app.jwtSecret}")
	private String jwtSecret;
	
	@Value("${example.app.jwtExpiration}")
	private int jwtExpiration;
	
	@Value("${example.app.jwtRefreshTime}")
	private int jwtRefreshTime;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	
		public String generateJwtToken(CustomUserDetails userPrincpal) {
			
			
			//CustomUserDetails userPrincpal = (CustomUserDetails)userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
			LOG.log(Level.INFO,"User is "+ userPrincpal.getUsername());
		return Jwts.builder().setSubject((userPrincpal.getUsername()))
							.setIssuedAt(new Date())
							.setExpiration(new Date((new Date()).getTime() + jwtExpiration))
							.signWith(SignatureAlgorithm.HS512, jwtSecret)
							.compact();
	}
	
		public String generateJwtRefreshToken(CustomUserDetails userPrincipal) {
		//	CustomUserDetails userPrincipal = (CustomUserDetails)authentication.getPrincipal();
		
		return Jwts.builder().setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtRefreshTime))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	public Date getJwtExpiration(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration();
	}
	
	
	
	public String getEmailFromJwtTokens(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}
	
	
	public boolean isJwtValid(String authToken) {
		try {
	//		LOG.log(Level.INFO, "The value of authTOKEN is "+ authToken);
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		}catch (SignatureException e) {
			LOG.log(LOG.getLevel().INFO,"Signature Exception " + e.getMessage());
		}catch (IllegalArgumentException e) {

			LOG.log(LOG.getLevel().INFO,"IlliegalArgument Exception " + e.getMessage());
		}catch(ExpiredJwtException e) {

			LOG.log(LOG.getLevel().INFO,"Expired JWT Exception " + e.getMessage());
		}catch (MalformedJwtException e) {

			LOG.log(LOG.getLevel().INFO,"Malformed JWT Exception " + e.getMessage());
		}catch (UnsupportedJwtException e) {

			LOG.log(LOG.getLevel().INFO,"Unsupported Exception " + e.getMessage());
		}
		return false;
	}

}
