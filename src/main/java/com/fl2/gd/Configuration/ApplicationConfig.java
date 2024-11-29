package com.fl2.gd.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fl2.gd.Repositories.UserRepository;

@Configuration
public class ApplicationConfig {

	  @Autowired
	  private UserRepository userRepo;
	  
	  @Bean
	  public UserDetailsService userDetailsService() throws UsernameNotFoundException {
		return username -> userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Invalid Username: " + username));
    }
	  @Bean
	  public AuthenticationProvider authenticationProvider(){
	   DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	   authProvider.setUserDetailsService(userDetailsService());
	   authProvider.setPasswordEncoder(passwordEncoder());
	   return authProvider;
	  }
	  
		 @Bean
		  public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
		     return config.getAuthenticationManager();
		 }

	  @Bean
	  public PasswordEncoder passwordEncoder(){
	  return new BCryptPasswordEncoder();
	  }

}
