package com.fl2.gd.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fl2.gd.DTOs.AuthenticationRequest;
import com.fl2.gd.DTOs.AuthenticationResponse;
import com.fl2.gd.DTOs.RegisterRequest;
import com.fl2.gd.Services.AuthenticationService;



@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {
	   @Autowired
	   private AuthenticationService authService; 
	  
	  @PostMapping("/register")
	  public ResponseEntity<AuthenticationResponse> registerUser(
			  @RequestBody RegisterRequest request
			  ) {
		  return ResponseEntity.ok(authService.registerUser(request));
	  }
	  
	  @PostMapping("/login")
	  public ResponseEntity<AuthenticationResponse> registerUser(
			  @RequestBody AuthenticationRequest request
			  ) {
		  return ResponseEntity.ok(authService.authenticateUser(request));
	  }


}

