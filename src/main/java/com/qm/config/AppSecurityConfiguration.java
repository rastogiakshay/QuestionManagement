package com.qm.config;

import java.util.logging.Logger;

import com.qm.jwt.AuthorizationEntryPoint;
import com.qm.jwt.AuthorizationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfiguration {
	
	private Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	@Autowired
	private CustomUserDetailsService service;
	
//	@Autowired
//	private AuthorizationEntryPoint authorzationHandler;
	
	@Bean
	public AuthorizationTokenFilter authTokenFilter() {
		
		return new AuthorizationTokenFilter();
	}
	@Bean
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service).passwordEncoder(passwordEncoder());
	}
	
	  @Bean
	public static PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	  	  
	  @Bean
	protected AuthenticationManager authenticationManager() throws Exception {

		return authenticationManager();
	}
//	@Bean
//	protected AuthenticationManager authenticationManager(BaseLdapPathContextSource ) throws Exception {
//
//		return super.authenticationManager();
//	}

	  /**
	   * This method helps in implementing the security.
	   */
//	  @Bean
//	  public void configure(HttpSecurity http) throws Exception{
//
//		http.cors(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable)
////		.exceptionHandling(exc ->{
////			exc.authenticationEntryPoint(authorzationHandler);
////		})
//		.sessionManagement(sec ->{
//			sec.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		}).authorizeRequests().requestMatchers("/api/auth/**").permitAll()
//		.requestMatchers("/api/user/**").permitAll()
//		.anyRequest().authenticated()
//		.and().formLogin(Customizer.withDefaults());
//
//		http.addFilterAfter(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//
//	  }

//	@Bean
//	public InMemoryUserDetailsManager userDetailsService() {
//		UserDetails user = User.withDefaultPasswordEncoder()
//				.username("user")
//				.password("password")
//				.roles("USER")
//				.build();
//		return new InMemoryUserDetailsManager(user);
//	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http
				.csrf(AbstractHttpConfigurer::disable)
				.cors(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> {
					auth.requestMatchers("/api/auth/**").permitAll();
					auth.requestMatchers("/api/user/**").permitAll();
					auth.anyRequest().authenticated();
				})
				.oauth2Login(oath2 -> {
					oath2.loginPage("/login").permitAll();
//					oath2.successHandler(oAuth2LoginSuccessHandler);
				})
				.build();
	}
	  
}