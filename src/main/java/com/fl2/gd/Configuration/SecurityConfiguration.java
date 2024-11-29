package com.fl2.gd.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class SecurityConfiguration{
	
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthFilter;
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@SuppressWarnings("removal")
	@Bean
	 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	         http
	         .csrf()
	 	     .disable()
	         .authorizeHttpRequests()
	 	     .requestMatchers("/auth/**", "/ws/**", "/chat/**")
	 	     .permitAll()
	 	     .anyRequest()
	 	     .authenticated()
	 	     .and()
	 	     .sessionManagement()
	 	     .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	 	     .and()
	  	     .authenticationProvider(authenticationProvider)
	 	     .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

	 	
	 	return http.build();
	 }
		
}

