package com.fl2.gd.Tasks;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fl2.gd.Entities.DemandeConge;
import com.fl2.gd.Entities.Employee;
import com.fl2.gd.Repositories.DemandeCongeRepository;
import com.fl2.gd.Repositories.EmployeeRepository;


@Component
public class TimeOffTasks {

	@Autowired
    private  EmployeeRepository employeeRepo;
	@Autowired
	private DemandeCongeRepository demandeCongeRepository;

	
	
	  @Scheduled(cron = "0 0 0 1 * ?") 
	 //@Scheduled(cron = "0/30 * * * * *") // Run every 30 seconds
    public void incrementTimeOffSalary() {
        List<Employee> employees = employeeRepo.findAll();
        for (Employee employee : employees) {
            double currentTimeOffDays = employee.getTimeOffDays();
            double newTimeOffDays = currentTimeOffDays + 2.5; 
            employee.setTimeOffDays(newTimeOffDays);
        }
        employeeRepo.saveAll(employees);
    }
	 

	    @Scheduled(fixedRate = 24 * 60 * 60 * 1000) // Run every 24 hours
	    public void removeTimeOffStatus() {
	        Date currentDate = new Date();
	        List<DemandeConge> timeOffRequests = demandeCongeRepository.findByReturnDate(currentDate);

	        for (DemandeConge demandeConge : timeOffRequests) {
	            Employee employee = demandeConge.getEmployee();
	            employee.setOnTimeOff(false);
	            employeeRepo.save(employee);
	        }
	    }
	    
	    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
	    public void setTimeOffStatus() {
	        Date currentDate = new Date();
	        List<DemandeConge> timeOffRequests = demandeCongeRepository.findByStartDate(currentDate);

	        for (DemandeConge demandeConge : timeOffRequests) {
	            Employee employee = demandeConge.getEmployee();
	            employee.setOnTimeOff(true);
	            employeeRepo.save(employee);
	        }
	    }
	 
	    @Scheduled(fixedRate = 24 * 60 * 60 * 1000) // Run every 24 hours
	    public void resetNextTimeOfDate() {
	        Date currentDate = new Date();
	        List<Employee> employeeList = employeeRepo.findEmployeesByNextTimeOffDate(currentDate);

	        for (Employee employee : employeeList) {
	            employee.setNextTimeOffDate(null);
	            employeeRepo.save(employee);
	        }
	    }
	    
	    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
	    public void unblockUsersFromDemandeConge() {
	    	Date currentDate = new Date();
	    	 List<Employee> employeeList = employeeRepo
	    			 .findEmployeesEndOfDemandeCongeBlock(currentDate);

	         for (Employee employee : employeeList) {
		            employee.setBlockedFromDemandeCongeUntil(null);
		            employeeRepo.save(employee);	        }
	    }
	    
}
