package com.fl2.gd.Controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fl2.gd.Entities.DemandeRemboursementAssurance;
import com.fl2.gd.Services.DemandeRemboursementAssuranceService;
import com.fl2.gd.Services.EmployeeService;
import com.fl2.gd.Services.JwtService;


@RestController
@RequestMapping("/demandes-remboursement-assurance")
@CrossOrigin("*")
public class DemandeRemboursementAssuranceController {
	
	@Autowired
	private JwtService jwtService;
	@Autowired
	private DemandeRemboursementAssuranceService demandeRemboursementAssuranceService;
	 @Autowired
	 private EmployeeService  employeeService;
	 
	 private static String dir = System.getProperty("user.home")+"/Downloads";

	
	@GetMapping("/getAllDemandsRemboursementAssurance")
	public List<DemandeRemboursementAssurance> getAllDemandesRemboursementAssurance(@RequestHeader(name = "Authorization") String authorizationHeader){
		return demandeRemboursementAssuranceService.getAllDemandesRemboursementAssurance();
	}
	@GetMapping("/getDemandeRemboursementAssuranceById/{id}")
	public DemandeRemboursementAssurance getDemandeRemboursementAssuranceById(@PathVariable int id, @RequestHeader(name = "Authorization") String authorizationHeader){
		return demandeRemboursementAssuranceService.getDemandeRemboursementAssuranceById(id);
	}
		
	@PostMapping(value = "/addDemandeRemboursementAssurance/{employeeId}", 
			consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE },
           produces = MediaType.APPLICATION_JSON_VALUE)
	public void addDemandeRemboursementAssurance(
	        @PathVariable int employeeId,
	        @RequestPart("demandeRemboursementAssurance") DemandeRemboursementAssurance demandeRemboursementAssurance,
	        @RequestPart("file") MultipartFile file,
	        @RequestHeader(name = "Authorization") String authorizationHeader) throws IOException {
		 
		 Date dateDemande = new Date();
		 String fileName = file.getOriginalFilename();
		 DemandeRemboursementAssurance newDemande = demandeRemboursementAssurance;
		 
		 newDemande.setRequestDate(dateDemande);
		 newDemande.setRequestDocuments(file.getBytes());
		 newDemande.setEmployee(employeeService.getEmployeeById(employeeId));			
		 newDemande.setDocumentName(fileName);
		 
		 demandeRemboursementAssuranceService.saveDemandeRemboursementAssurance(newDemande);

	}
	

	
	@PutMapping("/editDemandeRemboursementAssurance")
	public void editDemandeRemboursementAssurance(@RequestBody DemandeRemboursementAssurance demandeRemboursementAssurance, @RequestHeader(name = "Authorization") String authorizationHeader) {
		demandeRemboursementAssuranceService.saveDemandeRemboursementAssurance(demandeRemboursementAssurance);
	}
	
	@DeleteMapping("/deleteDemandeRemboursementAssuranceById/{id}")
	public void deleteDemandeRemboursementAssurance(@PathVariable int id, @RequestHeader(name = "Authorization") String authorizationHeader) {
		demandeRemboursementAssuranceService.deleteDemandeRemboursementAssurance(id);
	}
	
	@GetMapping("/getAllRecentDemandesRemboursementAssurance")
	public List<DemandeRemboursementAssurance> getAllRecentDemandesRemboursementAssurance(@RequestHeader(name = "Authorization") String authorizationHeader){
		return demandeRemboursementAssuranceService.getAllRecentDemandesRemboursementAssurance();
	}
	
	@GetMapping("/checkIfEmployeeHasUntreatedRequest/{id}")
	public DemandeRemboursementAssurance checkIfEmployeeHasUntreatedRequest(@PathVariable int id, @RequestHeader(name = "Authorization") String authorizationHeader){
		return demandeRemboursementAssuranceService.checkIfEmployeeHasUntreatedRequest(id);
	}

	
	@GetMapping("/findDemandeRemboursementAssuranceByEmployeeId/{employeeId}")
	public List<DemandeRemboursementAssurance> findDemandeRemboursementAssuranceByEmployeeId(@PathVariable int employeeId,@RequestHeader(name = "Authorization") String authorizationHeader){
		return demandeRemboursementAssuranceService.findDemandeRemboursementAssuranceByEmployeeId(employeeId);
	}

	@GetMapping("/findDemandeRemboursementAssuranceByDepartementId/{departementId}")
	public List<DemandeRemboursementAssurance> findDemandeRemboursementAssuranceByDepartementId(@PathVariable int departementId,@RequestHeader(name = "Authorization") String authorizationHeader){
		return demandeRemboursementAssuranceService.findDemandeRemboursementAssuranceByDepartementId(departementId);
	}
	
	@GetMapping("/findDemandeRemboursementAssuranceForChefDepartement/{departementId}")
	public List<DemandeRemboursementAssurance> findDemandeRemboursementAssuranceForChefDepartement(@PathVariable int departementId,@RequestHeader(name = "Authorization") String authorizationHeader){
		return demandeRemboursementAssuranceService.findDemandeRemboursementAssuranceForChefDepartement(departementId);
	}
	

	@GetMapping("/findDemandeRemboursementAssuranceForAdmin/{departementId}")
	public List<DemandeRemboursementAssurance> findDemandeRemboursementAssuranceForAdmin(@PathVariable int departementId,@RequestHeader(name = "Authorization") String authorizationHeader){
		return demandeRemboursementAssuranceService.findDemandeRemboursementAssuranceForAdmin(departementId);
	}
	@GetMapping("/findAllRecentDemandesRemboursementAssuranceForAdmin")
	public List<DemandeRemboursementAssurance> findAllRecentDemandesRemboursementAssuranceForAdmin(@RequestHeader(name = "Authorization") String authorizationHeader){
		return demandeRemboursementAssuranceService.findAllRecentDemandesRemboursementAssuranceForAdmin();
	}
	
	//_______________________________________________________________________
	
	
	@GetMapping("/findFinalisedDemandeRemboursementAssuranceByDepartementId/{departementId}")
	public List<DemandeRemboursementAssurance> findFinalisedDemandeRemboursementAssuranceByDepartementId(@PathVariable int departementId,@RequestHeader(name = "Authorization") String authorizationHeader){
		return demandeRemboursementAssuranceService.findFinalisedDemandeRemboursementAssuranceByDepartementId(departementId);
	}
	@GetMapping("/findFinalisedDemandeRemboursementAssuranceByEmployeeId/{employeeId}")
	public List<DemandeRemboursementAssurance> findFinalisedDemandeRemboursementAssuranceByEmployeeId(@PathVariable int employeeId,@RequestHeader(name = "Authorization") String authorizationHeader){
		return demandeRemboursementAssuranceService.findFinalisedDemandeRemboursementAssuranceByEmployeeId(employeeId);
	}
	@GetMapping("/findAllFinalisedDemandesRemboursementAssurance")
	public List<DemandeRemboursementAssurance> findAllFinalisedDemandesRemboursementAssurance(@RequestHeader(name = "Authorization") String authorizationHeader){
		return demandeRemboursementAssuranceService.findAllFinalisedDemandesRemboursementAssurance();
	}
	@GetMapping("/findDemandeRemboursementAssuranceByFilter/{id}/{date}")
	public List<DemandeRemboursementAssurance> findDemandeRemboursementAssuranceByFilter(@PathVariable(required = false) int id,@PathVariable(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, @RequestHeader(name = "Authorization") String authorizationHeader){
		return demandeRemboursementAssuranceService.findDemandeRemboursementAssuranceByFilter(id, date);
	}

}
