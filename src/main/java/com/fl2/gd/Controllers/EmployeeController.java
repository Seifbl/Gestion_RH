package com.fl2.gd.Controllers;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import com.fl2.gd.Entities.AppUser;
import com.fl2.gd.Entities.Employee;
import com.fl2.gd.Repositories.UserRepository;
import com.fl2.gd.Services.EmployeeService;
import com.fl2.gd.Services.JwtService;




@RestController
@RequestMapping("/employees")
@CrossOrigin("*")
public class EmployeeController {
	
    @Autowired
    private EmployeeService employeeService;
	 @Autowired
	 private JwtService jwtService;
	 @Autowired
	 UserRepository userRepo ;


    @GetMapping("/getEmployeeByUserId/{userId}")
   public ResponseEntity<Employee> getEmployeeByUserId(@PathVariable Integer userId, @RequestHeader(name = "Authorization") String authorizationHeader) {
    	Employee student = employeeService.getEmployeeByUserId(userId);
       return ResponseEntity.ok(student);
    }
    
	@GetMapping("/getAllEmployees")
	public List<Employee> getAllEmployees(@RequestHeader(name = "Authorization") String authorizationHeader){
		return employeeService.getAllEmployees();
	}
	@GetMapping("/getEmployeeById/{id}")
	public Employee getEmployeeById(@PathVariable int id, @RequestHeader(name = "Authorization") String authorizationHeader){
		return employeeService.getEmployeeById(id);
	}
	
	@GetMapping("/getEmployeesByDepartementId/{id}")
	public List<Employee> getEmployeesByClassId(@PathVariable int id, @RequestHeader(name = "Authorization") String authorizationHeader){
		return employeeService.getEmployeesByDepartement(id);
	}
	
	@PostMapping("/addEmployee")
	public void addEmployee(@RequestBody Employee employee, @RequestHeader(name = "Authorization") String authorizationHeader) {
		employeeService.saveEmployee(employee);
	}
	
	@PutMapping("/editEmployee")
	public void editEmployee(@RequestBody Employee employee, @RequestHeader(name = "Authorization") String authorizationHeader) {
		employeeService.saveEmployee(employee);
	}
	
	@DeleteMapping("/deleteEmployeeById/{id}")
	public void deleteEmployee(@PathVariable int id, @RequestHeader(name = "Authorization") String authorizationHeader) {
		employeeService.deleteEmployeeById(id);
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
	
    @GetMapping("/findWorkingEmployeesInDepartment/{departmentId}/{employeeId}/{startDate}/{endDate}")
    public boolean checkWorkingEmployees(@PathVariable int departmentId,
    		@PathVariable int employeeId,
    		@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
    		@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, 
    		@RequestHeader(name = "Authorization") String authorizationHeader) {
        boolean employeesWorking = employeeService.areEmployeesWorkingInDepartment(departmentId, employeeId, startDate, endDate);  

        return employeesWorking;
  
    }
	
	@GetMapping("/findListOfWorkingEmployeesInTimeOff/{departmentId}/{employeeId}/{startDate}/{endDate}")
	public List<Employee> findListOfWorkingEmployeesInTimeOff(@PathVariable int departmentId,
			@PathVariable int employeeId,
    		@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
    		@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, 
    		@RequestHeader(name = "Authorization") String authorizationHeader){
		return employeeService.findListOfWorkingEmployeesInTimeOff(departmentId, employeeId, startDate, endDate);
	}
	
	@GetMapping("/findEmployeesByJoinDate/{selectedDate}")
	public List<Employee> findEmployeesByJoinDate(
    		@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date selectedDate, 
    		@RequestHeader(name = "Authorization") String authorizationHeader){
		return employeeService.findEmployeesByJoinDate(selectedDate);
	}
	
	@PostMapping(value = "/{employeeId}/uploadProfilePic", 
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE },
            produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> uploadProfilePic(
			@PathVariable int employeeId,
			@RequestPart("file") MultipartFile file,
			@RequestHeader(name = "Authorization") String authorizationHeader) throws IOException {

		Map<String, String> response = new HashMap<>();

		if (file.isEmpty()) {
			response.put("message", "File is empty");
			return ResponseEntity.badRequest().body(response);
		}

		Employee employee = employeeService.getEmployeeById(employeeId);

		if (employee == null) {
			response.put("message", "Employee not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

		employee.setProfilePic(file.getBytes());
		employeeService.saveEmployee(employee);

		response.put("message", "Profile picture uploaded successfully");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/markEmployeeAsAbsent/{id}")
	public int markEmployeeAsAbsent(@PathVariable int id, @RequestHeader(name = "Authorization") String authorizationHeader) {
	 Employee emp = new Employee();
	 emp = employeeService.getEmployeeById(id);
	 if(emp.isOnTimeOff()) {
		 return 1 ; //Employee is on Time Off
	 } else if (emp.isAbsentForToday()){
		 return 2; //Employee Already Marked As Absent
	 }else {
		employeeService.markEmployeeAsAbsent(id);
		return 3; //Marked As Absent
	  }
	}
	
}