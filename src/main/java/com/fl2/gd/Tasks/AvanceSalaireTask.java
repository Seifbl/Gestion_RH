package com.fl2.gd.Tasks;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fl2.gd.Entities.Employee;
import com.fl2.gd.Repositories.EmployeeRepository;


@Component
public class AvanceSalaireTask {

	@Autowired
    private  EmployeeRepository employeeRepo;
	
	 @Scheduled(cron = "0 0 0 1 * ?") 
	// @Scheduled(cron = "0/30 * * * * *") // Run every 30 seconds
   public void resetAvanceSalaire() {
       List<Employee> employees = employeeRepo.findAll();
       for (Employee employee : employees) {
           boolean avanceSalaireTaken = employee.isAvanceSalaireTakenForThisMonth();
          if(avanceSalaireTaken){
        	  employee.setAvanceSalaireTakenForThisMonth(false);
       	  }        
       }
       employeeRepo.saveAll(employees);
   }
}
