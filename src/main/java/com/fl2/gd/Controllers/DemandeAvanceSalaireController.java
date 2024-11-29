package com.fl2.gd.Controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.fl2.gd.Entities.DemandeAvanceSalaire;
import com.fl2.gd.Entities.Employee;
import com.fl2.gd.Services.DemandeAvanceSalaireService;
import com.fl2.gd.Services.EmployeeService;
import com.fl2.gd.Services.JwtService;


@RestController
@RequestMapping("/demandes-avance-salaire")
@CrossOrigin("*")
public class DemandeAvanceSalaireController {

	@Autowired
	 private JwtService jwtService;
	 @Autowired
	 private DemandeAvanceSalaireService demandeAvanceSalaireService;
	 @Autowired
	 private EmployeeService  employeeService;
	 
	 
		@GetMapping("/getAllDemandesAvanceSalaire")
		public List<DemandeAvanceSalaire> getAllDemandesAvanceSalaire(@RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeAvanceSalaireService.getAllDemandesAvanceSalaire();
		}
		@GetMapping("/getDemandeAvanceSalaireById/{id}")
		public DemandeAvanceSalaire getDemandeAvanceSalaireById(@PathVariable int id, @RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeAvanceSalaireService.getDemandeAvanceSalaireById(id);
		}
		@PostMapping("/addDemandeAvanceSalaire/{employeeId}")
		public void addDemandeAvanceSalaire(@PathVariable int employeeId, @RequestBody DemandeAvanceSalaire demandeAvanceSalaire, @RequestHeader(name = "Authorization") String authorizationHeader) {
			DemandeAvanceSalaire newDemande = demandeAvanceSalaire;
			newDemande.setEmployee(employeeService.getEmployeeById(employeeId));
			Employee requester = employeeService.getEmployeeById(employeeId);
			if(newDemande.isApprovedByAdmin()) {
			requester.setAvanceSalaireTakenForThisMonth(true);
			employeeService.saveEmployee(requester);
			}
			demandeAvanceSalaireService.saveDemandeAvanceSalaire(newDemande);
		}
		
		@PutMapping("/editDemandeAvanceSalaire")
		public void editDemandeAvanceSalaire(@RequestBody DemandeAvanceSalaire demandeAvanceSalaire, @RequestHeader(name = "Authorization") String authorizationHeader) {
			demandeAvanceSalaireService.saveDemandeAvanceSalaire(demandeAvanceSalaire);
		}
		
		@DeleteMapping("/deleteDemandeAvanceSalaireById/{id}")
		public void deleteDemandeAvanceSalaire(@PathVariable int id, @RequestHeader(name = "Authorization") String authorizationHeader) {
			demandeAvanceSalaireService.deleteDemandeAvanceSalaire(id);
		}
		
		@GetMapping("/getAllRecentDemandesAvanceSalaire")
		public List<DemandeAvanceSalaire> getAllRecentDemandesAvanceSalaire(@RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeAvanceSalaireService.getAllRecentDemandesAvanceSalaire();
		}
		
		@GetMapping("/checkIfEmployeeHasUntreatedRequest/{id}")
		public DemandeAvanceSalaire checkIfEmployeeHasUntreatedRequest(@PathVariable int id, @RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeAvanceSalaireService.checkIfEmployeeHasUntreatedRequest(id);
		}
	
		
		@GetMapping("/findDemandeAvanceSalaireByEmployeeId/{employeeId}")
		public List<DemandeAvanceSalaire> findDemandeAvanceSalaireByEmployeeId(@PathVariable int employeeId,@RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeAvanceSalaireService.findDemandeAvanceSalaireByEmployeeId(employeeId);
		}

		@GetMapping("/findDemandeAvanceSalaireByDepartementId/{departementId}")
		public List<DemandeAvanceSalaire> findDemandeAvanceSalaireByDepartementId(@PathVariable int departementId,@RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeAvanceSalaireService.findDemandeAvanceSalaireByDepartementId(departementId);
		}
		
		@GetMapping("/findDemandeAvanceSalaireForChefDepartement/{departementId}")
		public List<DemandeAvanceSalaire> findDemandeAvanceSalaireForChefDepartement(@PathVariable int departementId,@RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeAvanceSalaireService.findDemandeAvanceSalaireForChefDepartement(departementId);
		}
		

		@GetMapping("/findDemandeAvanceSalaireForAdmin/{departementId}")
		public List<DemandeAvanceSalaire> findDemandeAvanceSalaireForAdmin(@PathVariable int departementId,@RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeAvanceSalaireService.findDemandeAvanceSalaireForAdmin(departementId);
		}
		@GetMapping("/findAllRecentDemandesAvanceSalaireForAdmin")
		public List<DemandeAvanceSalaire> findAllRecentDemandesAvanceSalaireForAdmin(@RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeAvanceSalaireService.findAllRecentDemandesAvanceSalaireForAdmin();
		}
		
		 
		 //_______________________________________________________________________
		
		@GetMapping("/findFinalisedDemandeAvanceSalaireByDepartementId/{departementId}")
		public List<DemandeAvanceSalaire> findFinalisedDemandeAvanceSalaireByDepartementId(@PathVariable int departementId,@RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeAvanceSalaireService.findFinalisedDemandeAvanceSalaireByDepartementId(departementId);
		}
		@GetMapping("/findFinalisedDemandeAvanceSalaireByEmployeeId/{employeeId}")
		public List<DemandeAvanceSalaire> findFinalisedDemandeAvanceSalaireByEmployeeId(@PathVariable int employeeId,@RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeAvanceSalaireService.findFinalisedDemandeAvanceSalaireByEmployeeId(employeeId);
		}
		@GetMapping("/findFinalisedDemandesAvanceSalaire")
		public List<DemandeAvanceSalaire> findFinalisedDemandesAvanceSalaire(@RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeAvanceSalaireService.findFinalisedDemandesAvanceSalaire();
		}
		
		@GetMapping("/findDemandeAvanceSalaireByFilter/{id}/{date}")
		public List<DemandeAvanceSalaire> findDemandeAvanceSalaireByFilter(@PathVariable(required = false) int id,@PathVariable(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date, @RequestHeader(name = "Authorization") String authorizationHeader){
			return demandeAvanceSalaireService.findDemandeAvanceSalaireByFilter(id, date);
		}
}
