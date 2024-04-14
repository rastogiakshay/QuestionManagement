package com.qm.jwt;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationEntryPoint implements AuthenticationEntryPoint {

	private Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	/**
	 * Entry point of the server request.
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
						 AuthenticationException authException) throws IOException, ServletException {
		System.out.println("request.getRequestURI() = " + request.getRequestURI());
		if (request.getRequestURI().contains("/swagger-ui")){
			response.setStatus(HttpServletResponse.SC_OK);
		}else{

		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "ERROR: UnAuthorized Response");
		}

	}

}
