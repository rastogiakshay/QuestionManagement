package com.example.demo.jwt;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationEntryPoint implements AuthenticationEntryPoint {

	private Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
	//	LOG.log(Level.INFO , "This is the commencement");
		
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "ERROR: UnAuthorized Response");
		
	}

}
