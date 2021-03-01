package com.example.demo.jwt;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.config.CustomUserDetailsService;

public class AuthorizationTokenFilter extends OncePerRequestFilter{
	
	private Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	@Autowired
	private JwtUtility jwtUtils;
	
	@Autowired
	private CustomUserDetailsService userService;
	
	
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
				LOG.log(Level.INFO, "the request is " + request);
		try {
			String jwtAuthToken = parseJwt(request);
			
			if(jwtAuthToken != null && jwtUtils.isJwtValid(jwtAuthToken)) {
				String email = jwtUtils.getEmailFromJwtTokens(jwtAuthToken);

				UserDetails userDetails = userService.loadUserByUsername(email);
				
				UsernamePasswordAuthenticationToken authToken =  new UsernamePasswordAuthenticationToken(email,null, userDetails.getAuthorities());
				
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authToken);
				
			}			
			
		}catch (Exception e) {

		}
		
		
		filterChain.doFilter(request, response);
		
		
	}

	public String parseJwt(HttpServletRequest requestToParse) {
		String requestedHeaderAuth = requestToParse.getHeader("Authorization");
		
		if(StringUtils.hasText(requestedHeaderAuth) && requestedHeaderAuth.startsWith("Bearer ")) {
			return requestedHeaderAuth.substring(7, requestedHeaderAuth.length());
		}
		return null;
	}
	
	
	
}
