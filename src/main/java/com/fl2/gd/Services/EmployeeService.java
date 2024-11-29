package com.fl2.gd.Services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fl2.gd.Entities.Employee;
import com.fl2.gd.Repositories.EmployeeRepository;

import jakarta.transaction.Transactional;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepo ;

	 public Employee getEmployeeByUserId(Integer userId) {
	      return employeeRepo.findByAppUser_UserId(userId)
	    		 .orElseThrow(() -> new RuntimeException("Student not found for userId: " + userId));
	    }
	
	public List<Employee> getAllEmployees(){
		return employeeRepo.findAll();
	}
	
	public Employee getEmployeeById(int id) {
		return employeeRepo.findById(id).get();
	}
	
	@Transactional
	public void saveEmployee(Employee employee) {
		employeeRepo.save(employee);
	}
	
	public void deleteEmployeeById(int id) {
		employeeRepo.deleteById(id);
	}
	
	
	public boolean isCINExist(String CIN) {
	     return employeeRepo.existsByCin(CIN);
	}
	
	
	public List<Employee> getEmployeesByDepartement(int id){
		return employeeRepo.findByDepartement_departementId(id);
	}
	
	public boolean areEmployeesWorkingInDepartment(int departmentId,int employeeId, Date startDate, Date endDate) {
		 int count = employeeRepo.countWorkingEmployeesInDepartment(departmentId, employeeId, startDate, endDate);
		    List<Employee> employeesInDept = employeeRepo.findByDepartement_departementId(departmentId);
		    int totalEmployeeCount = employeesInDept.size();
		    return count >= totalEmployeeCount / 2;
    }
	
	public List<Employee> findListOfWorkingEmployeesInTimeOff(int departementId,int employeeId, Date startDate, Date endDate){
		return employeeRepo.findListOfWorkingEmployeesInTimeOff(departementId, employeeId, startDate, endDate);
	}
	
	public List<Employee> findEmployeesByJoinDate(Date joinDate){
		return employeeRepo.findEmployeesByJoinDate(joinDate);
	}
	
    public void markEmployeeAsAbsent(Integer employeeId) {
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + employeeId));

        employee.setAbsentForToday(true);
        employee.setAbsencesThisMonth(employee.getAbsencesThisMonth()+1);
        employeeRepo.save(employee);
    }
	
	
}
