package com.qm.jwt;

import java.util.Date;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.qm.config.CustomUserDetails;
import com.qm.config.CustomUserDetailsService;

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

	/**
	 * this method will generate access token.
	 * 
	 * @param userPrincpal
	 * @return
	 */
	public String generateJwtToken(CustomUserDetails userPrincpal) {

		return Jwts.builder().setSubject((userPrincpal.getUsername())).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpiration))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	/**
	 * This method will generate refresh token.
	 * 
	 * @param userPrincipal
	 * @return
	 */
	public String generateJwtRefreshToken(CustomUserDetails userPrincipal) {
		return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtRefreshTime))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	/**
	 * This will extract expiration time from generated token.
	 * 
	 * @param token
	 * @return
	 */
	public Date getJwtExpiration(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration();
	}

	/**
	 * This method will extract email from token
	 * 
	 * @param token
	 * @return
	 */
	public String getEmailFromJwtTokens(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	/**
	 * This will check the validation of token.
	 * 
	 * @param authToken
	 * @return
	 */
	public boolean isJwtValid(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			LOG.log(LOG.getLevel().INFO, "Signature Exception " + e.getMessage());
		} catch (IllegalArgumentException e) {

			LOG.log(LOG.getLevel().INFO, "IlliegalArgument Exception " + e.getMessage());
		} catch (ExpiredJwtException e) {

			LOG.log(LOG.getLevel().INFO, "Expired JWT Exception " + e.getMessage());
		} catch (MalformedJwtException e) {

			LOG.log(LOG.getLevel().INFO, "Malformed JWT Exception " + e.getMessage());
		} catch (UnsupportedJwtException e) {

			LOG.log(LOG.getLevel().INFO, "Unsupported Exception " + e.getMessage());
		}
		return false;
	}

}
