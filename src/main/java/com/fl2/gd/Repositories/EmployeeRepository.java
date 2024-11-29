package com.fl2.gd.Repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fl2.gd.Entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	boolean existsByCin(String CIN);
	Optional<Employee> findByAppUser_UserId(Integer userId);
	Employee findByCin(String CIN);
	List<Employee> findByDepartement_departementId(Integer classId);
	
	@Query("SELECT COUNT(emp) FROM Employee emp " +
		       "WHERE emp.departement.departementId = :deptId " +
		       "AND emp.employeeId != :empId " +
		       "AND emp.onTimeOff = false " +
		       "AND (emp.nextTimeOffDate IS NULL OR " +
		       "     NOT (emp.nextTimeOffDate BETWEEN :startDate AND :endDate))")
	    int countWorkingEmployeesInDepartment(@Param("deptId") int deptId, 
	    									  @Param("empId") int empId, 
	    									  @Param("startDate") Date startDate, 
	    									  @Param("endDate") Date endDate);	
	 
	@Query("SELECT emp FROM Employee emp " +
		       "WHERE emp.departement.departementId = :deptId " +
		       "AND emp.employeeId != :empId " +
		       "AND emp.onTimeOff = false " +
		       "AND (emp.nextTimeOffDate IS NULL OR " +
		       "     NOT (emp.nextTimeOffDate BETWEEN :startDate AND :endDate))")
		List<Employee> findListOfWorkingEmployeesInTimeOff( @Param("deptId") int deptId, 
															@Param("empId") int empId,				 
															@Param("startDate") Date startDate, 
															@Param("endDate") Date endDate);
	 
	 @Query("SELECT emp FROM Employee emp WHERE emp.nextTimeOffDate = :currentDate")
	 List<Employee> findEmployeesByNextTimeOffDate(@Param("currentDate") Date currentDate);
	 
	 @Query("SELECT emp FROM Employee emp WHERE emp.blockedFromDemandeCongeUntil = :currentDate")
	 List<Employee> findEmployeesEndOfDemandeCongeBlock(@Param("currentDate") Date currentDate);
	 
	 @Query("SELECT emp FROM Employee emp WHERE emp.joinDate <= :selectedDate")
	 List<Employee> findEmployeesByJoinDate(@Param("selectedDate") Date selectedDate);
	 

	 
}
