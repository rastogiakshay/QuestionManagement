//package com.qm.config;
//
//
////import com.estal.authenciation.JwtAuthenticationEntryPoint;
////import com.estal.authenciation.JwtRequestFilter;
////import com.estal.src.serviceImpl.AdminLoginServiceImpl;
//import com.qm.jwt.JwtUtility;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.util.matcher.*;
//
//@Configuration
//@EnableWebSecurity
//public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(
//            new AntPathRequestMatcher("/${business}/public/admin/**")
//    );
//
//    static final RequestMatcher PROTECT_URL = new NegatedRequestMatcher(PUBLIC_URLS);
//
////    @Autowired
////    private AdminLoginServiceImpl adminLoginServiceImpl;
////
////    @Autowired
////    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//
//    @Autowired
//    private JwtUtility jwtRequestFilter;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//
//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth.userDetailsService(adminLoginServiceImpl).passwordEncoder(passwordEncoder());
//    }
//
//    @Override
//    public void configure(final WebSecurity webSecurity) {
//        webSecurity.ignoring().requestMatchers(PUBLIC_URLS);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.csrf().disable();
////                .authorizeRequests().antMatchers("/estal/admin/public/**").anonymous()
////                .anyRequest()
////                .authenticated().and()
////                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
////                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
////
////        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//
//    }
//
//}
