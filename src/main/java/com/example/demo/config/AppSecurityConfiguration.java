package com.example.demo.config;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.jwt.AuthorizationEntryPoint;
import com.example.demo.jwt.AuthorizationTokenFilter;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	private Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	@Autowired
	private CustomUserDetailsService service;
	
	@Autowired
	private AuthorizationEntryPoint authorzationHandler;
	
	@Bean
	public AuthorizationTokenFilter authTokenFilter() {
		
		return new AuthorizationTokenFilter();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service).passwordEncoder(passwordEncoder());
	}
	
	  @Bean
	   public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	  	  
	  @Bean
	  @Override
	protected AuthenticationManager authenticationManager() throws Exception {

		return super.authenticationManager();
	}

	  /**
	   * This method helps in implementing the security.
	   */
	  @Override
	  public void configure(HttpSecurity http) throws Exception{

		http.cors().and().csrf().disable()
		.exceptionHandling().authenticationEntryPoint(authorzationHandler).and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.authorizeRequests().antMatchers("/api/auth/**").permitAll()
		.antMatchers("/api/user/**").permitAll()
		.anyRequest().authenticated()
		.and().formLogin().disable();
		
		http.addFilterAfter(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		  
	  }
	  
}