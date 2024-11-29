package com.fl2.gd.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fl2.gd.Entities.AppUser;
import com.fl2.gd.Entities.Departement;
import com.fl2.gd.Repositories.UserRepository;
import com.fl2.gd.Services.DepartementService;
import com.fl2.gd.Services.JwtService;



@RestController
@RequestMapping("/departements")
@CrossOrigin("*")
public class DepartementController {

	 @Autowired
	 private DepartementService departementService;
	 @Autowired
	 private JwtService jwtService;
	 @Autowired
	 UserRepository userRepo ;
	 
	 
		@GetMapping("/getAllDepartements")
		public List<Departement> getAllDepartements(@RequestHeader(name = "Authorization") String authorizationHeader){
			return departementService.getAllDepartements();
		}
		@GetMapping("/getDepartementById/{id}")
		public Departement getDepartementById(@PathVariable int id, @RequestHeader(name = "Authorization") String authorizationHeader){
			return departementService.getDepartementById(id);
		}
		@PostMapping("/addDepartement")
		public void addDepartement(@RequestBody Departement departement, @RequestHeader(name = "Authorization") String authorizationHeader) {
			departementService.saveDepartement(departement);
		}
		
		@PutMapping("/editDepartement")
		public void editDepartement(@RequestBody Departement departement, @RequestHeader(name = "Authorization") String authorizationHeader) {
			departementService.saveDepartement(departement);
		}
		
		@DeleteMapping("/deleteDepartementById/{id}")
		public void deleteDepartement(@PathVariable int id, @RequestHeader(name = "Authorization") String authorizationHeader) {
			departementService.deleteDepartementById(id);
		}
		
		boolean roleCheck(String Header) {
			String token = Header.substring(7);
		    String userName = jwtService.extractUsername(token);
		    AppUser requestHolder = userRepo.findByUsername(userName).get();
		   
		    String userRole = requestHolder.getAuthorities().iterator().next().getAuthority();
		    
		    if(userRole.equals("ADMIN") || userRole.equals("CHEF DEPARTEMENT")) {
		    	return true;
		    } else {
		    	return false;
		    }
		}
}
