package com.fl2.gd.Services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fl2.gd.DTOs.AuthenticationRequest;
import com.fl2.gd.DTOs.AuthenticationResponse;
import com.fl2.gd.DTOs.RegisterRequest;
import com.fl2.gd.Entities.AppUser;
import com.fl2.gd.Entities.Role;
import com.fl2.gd.Repositories.RoleRepository;
import com.fl2.gd.Repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthenticationService {
	
	   @Autowired
	   private UserRepository userRepo;
	   
	   @Autowired
	   private RoleRepository roleRepo;  
	   
	   @Autowired
	   private PasswordEncoder passwordEncoder;
	   
	   @Autowired
	   private AuthenticationManager authenticationManager;
	   
	   @Autowired
	   private JwtService jwtService;
	   


	   
	   public AuthenticationResponse registerUser(RegisterRequest request){	

		     String encodedPassword = passwordEncoder.encode(request.getPassword());
		     Role userRole = roleRepo.findByAuthority("USER")
                     .orElseThrow(() -> new RuntimeException("Default role not found"));		   
		     Set<Role> authorities = new HashSet<>(); 
		     authorities.add(userRole); 
		     AppUser user = new AppUser(
			    		request.getUsername(), 
			    		encodedPassword, 
			    		authorities); 
		     userRepo.save(user);

		     var jwtToken = jwtService.generateToken(user);
		     AuthenticationResponse authResp = new AuthenticationResponse(jwtToken);
		     return authResp;
		}


	   public AuthenticationResponse authenticateUser(AuthenticationRequest request) {
		   authenticationManager.authenticate(
		     new UsernamePasswordAuthenticationToken(
		       request.getUsername(),
		       request.getPassword()
		     )
		   );
		   AppUser user = userRepo.findByUsername(request.getUsername())
				   .orElseThrow(() -> new RuntimeException("Username Not Found"));
		     var jwtToken = jwtService.generateToken(user);
		     AuthenticationResponse authResp = new AuthenticationResponse(jwtToken);
		     return authResp;
		 }

} 


