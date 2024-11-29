package com.fl2.gd.Tasks;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fl2.gd.Entities.Employee;
import com.fl2.gd.Repositories.EmployeeRepository;


@Component
public class FicheDePaieTask {
	
	@Autowired
    private  EmployeeRepository employeeRepo;
	
	   @Scheduled(cron = "0 0 0 1 * ?") 
	   public void resetFicheDePaie() {
	       List<Employee> employees = employeeRepo.findAll();
	       for (Employee employee : employees) {
	           boolean ficheDePaieTaken = employee.isFicheDePaieTakenForThisMonth();
	          if(ficheDePaieTaken){
	        	  employee.setFicheDePaieTakenForThisMonth(false);
	       	  }        
	       }
	       employeeRepo.saveAll(employees);
	   }

}
