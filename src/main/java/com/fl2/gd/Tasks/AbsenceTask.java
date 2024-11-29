package com.fl2.gd.Tasks;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fl2.gd.Repositories.UserRepository;
import com.fl2.gd.Entities.Employee;
import com.fl2.gd.Repositories.EmployeeRepository;

@Component
public class AbsenceTask {

	@Autowired
    private  EmployeeRepository employeeRepo;
	@Autowired
    private  UserRepository userRepo;
	
	@Scheduled(cron = "0 0 0 * * ?")
    public void resetDailyAbsences() {
        List<Employee> employees = employeeRepo.findAll();

        for (Employee employee : employees) {
            
            if (employee.isAbsentForToday()) {
                employee.setAbsencesThisMonth(employee.getAbsencesThisMonth() + 1);
            }
            // Reset the absentForToday flag
            employee.setAbsentForToday(false);
            // Save the employee's state
            employeeRepo.save(employee);
        }
    }
	@Scheduled(cron = "0 0 0 1 * ?")  
	public void handleAbsences() {
	    List<Employee> employees = employeeRepo.findAll();

	    for (Employee employee : employees) {
	        if(employee.getAbsencesThisMonth() >= 10) {
	        	this.userRepo.deleteAppUserByEmployeeId(employee.getEmployeeId());
	        	this.employeeRepo.deleteById(employee.getEmployeeId());
	        }
	    }
	}
	@Scheduled(cron = "0 0 0 1 * ?")  
	public void resetMonthlyAbsences() {
	    List<Employee> employees = employeeRepo.findAll();

	    for (Employee employee : employees) {
	        employee.setAbsencesThisMonth(0);
	        employeeRepo.save(employee);
	    }
	}
}
