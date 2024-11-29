package com.fl2.gd.Controllers;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fl2.gd.DTOs.PasswordChangeRequest;
import com.fl2.gd.Entities.AppUser;
import com.fl2.gd.Entities.Employee;
import com.fl2.gd.Entities.Role;
import com.fl2.gd.Repositories.RoleRepository;
import com.fl2.gd.Repositories.UserRepository;
import com.fl2.gd.Services.JwtService;



@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepo;
	
	
	@GetMapping("/getUserProperties")
	public Optional<AppUser> getUserRoles(@RequestHeader(name = "Authorization") String authorizationHeader) {
	    String token = authorizationHeader.substring(7);
	    String userName = jwtService.extractUsername(token);
	    Optional<AppUser> appUser = userRepo.findByUsername(userName);	    
	    return appUser ; 
	}
	
	@PostMapping("/addUser/{selectedRole}")
	public AppUser addUser(@PathVariable String selectedRole,@RequestBody AppUser appUser,@RequestHeader(name = "Authorization") String authorizationHeader) {
	    String token = authorizationHeader.substring(7);
	    String userName = jwtService.extractUsername(token);
	    AppUser requestHolder = userRepo.findByUsername(userName).get();
	   
	    String authority = requestHolder.getAuthorities().iterator().next().getAuthority();
	    
	    if(authority.equals("ADMIN") || authority.equals("CHEF DEPARTEMENT")) {
	    	String encodedPassword = passwordEncoder.encode(appUser.getPassword());
	    	Role userRole;
	    	Set<Role> authorities = new HashSet<>(); 

	    	if(selectedRole.equals("USER")) {
		    	 userRole = roleRepo.findByAuthority("USER")
	                    .orElseThrow(() -> new RuntimeException("Default role not found"));		    	
		    	 authorities.add(userRole); 
	    	} else if(selectedRole.equals("ADMIN")) {
	    		userRole = roleRepo.findByAuthority("ADMIN")
	                    .orElseThrow(() -> new RuntimeException("Default role not found"));		    	
		    	 authorities.add(userRole); 
	    	} else if (selectedRole.equals("CHEF DEPARTEMENT")) {
	    		userRole = roleRepo.findByAuthority("CHEF DEPARTEMENT")
	                    .orElseThrow(() -> new RuntimeException("Default role not found"));		    	
		    	 authorities.add(userRole); 
	    	} 
		   
		    AppUser user = new AppUser(
		    		appUser.getUsername(), 
		    		encodedPassword, 
		    		authorities); 
		    userRepo.save(user);
		    return user;
	    } else {
	    	return null;
	    }
	}
	
	@PutMapping("/changePassword")
	public boolean changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest, @RequestHeader(name = "Authorization") String authorizationHeader) {
	    String token = authorizationHeader.substring(7);
	    String userName = jwtService.extractUsername(token);
	    AppUser requestHolder = userRepo.findByUsername(userName).get();
	    if (passwordEncoder.matches(passwordChangeRequest.getOldPassword(), requestHolder.getPassword())) {
	        requestHolder.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
	        userRepo.save(requestHolder);
	        return true;
	    } else {
	        return false;
	    }
	}
	

	
	@GetMapping("/getUserInfo/{id}")
	public Employee getUserInfo(@PathVariable int id,
			@RequestHeader(name = "Authorization") String authorizationHeader) {
		return userRepo.getEmployeeInfoFromUser(id);
	}
	
	
//	@GetMapping("/addUser")
//	public String addUser(@RequestHeader(name = "Authorization") String authorizationHeader) {
//	    String token = authorizationHeader.substring(7);
//	    String userName = jwtService.extractUsername(token);
//	   
//	    String userRole = requestHolder.getAuthorities().iterator().next().getAuthority();
//	    return "USER ROLE IS : --------- " + userRole + " ------------";
//	}

}
