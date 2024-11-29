package com.fl2.gd.Repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fl2.gd.Entities.DemandeConge;


public interface DemandeCongeRepository extends JpaRepository<DemandeConge,Integer> {
	
	 @Query("SELECT dc FROM DemandeConge dc ORDER BY dc.requestDate DESC")
	 List<DemandeConge> findAllRecentDemandesConge();
	 
	 @Query("SELECT dc FROM DemandeConge dc WHERE dc.employee.employeeId = :id"
	 		+ " AND (dc.approvedByCD = false OR dc.approvedByAdmin = false)"
	 		+ " AND dc.finalised = false")
	 DemandeConge checkIfEmployeeHasUntreatedRequest(int id);
	 
	 @Query("SELECT dc FROM DemandeConge dc WHERE dc.returnDate = :currentDate")
	 List<DemandeConge> findByReturnDate(@Param("currentDate") Date currentDate);
	 
	 @Query("SELECT dc FROM DemandeConge dc WHERE dc.startDate = :currentDate")
	 List<DemandeConge> findByStartDate(@Param("currentDate") Date currentDate);
	 	 
	 @Query("SELECT dc FROM DemandeConge dc WHERE dc.employee.employeeId = :id")
	 List<DemandeConge> findDemandeCongeByEmployeeId(int id);
	 
	 @Query("SELECT dc FROM DemandeConge dc WHERE dc.employee.departement.departementId = :id")
	 List<DemandeConge> findDemandeCongeByDepartementId(int id);
	 
	 @Query("SELECT dc FROM DemandeConge dc WHERE dc.employee.departement.departementId = :id" 
	 +" AND dc.approvedByCD = false AND dc.approvedByAdmin = false AND dc.finalised = false")
	 List<DemandeConge> findDemandeCongeForChefDepartement(int id);
	 
	 @Query("SELECT dc FROM DemandeConge dc WHERE dc.employee.departement.departementId = :id" 
	 +" AND dc.approvedByCD = true AND dc.approvedByAdmin = false AND dc.finalised = false")
	 List<DemandeConge> findDemandeCongeForAdmin(int id);
	 
	 @Query("SELECT dc FROM DemandeConge dc WHERE dc.approvedByCD = true "
	 		+ "AND dc.approvedByAdmin = false AND dc.finalised = false "
	        + "ORDER BY dc.requestDate DESC")
	 List<DemandeConge> findAllRecentDemandesCongeForAdmin();
	 
	 //_______________________________________________________________________
	 
	 @Query("SELECT dc FROM DemandeConge dc WHERE dc.finalised = true")
	 List<DemandeConge> findAllFinalisedDemandesConge();
	 
	 @Query("SELECT dc FROM DemandeConge dc WHERE dc.employee.employeeId = :id"
	 		+ " AND dc.finalised = true")
	 List<DemandeConge> findFinalisedDemandeCongeByEmployeeId(int id);
	 
	 @Query("SELECT dc FROM DemandeConge dc WHERE dc.employee.departement.departementId = :id "
	 		+ "AND dc.finalised = true")
	 List<DemandeConge> findFinalisedDemandeCongeByDepartementId(int id);
	 
	 @Query("SELECT dc FROM DemandeConge dc "
	         + "WHERE (:id IS NULL OR dc.employee.departement.departementId = :id) "
	         + "AND (:date IS NULL OR dc.requestDate < :date)")
	    List<DemandeConge> findDemandeCongeByFilter(
	        int id, 
	        Date date
	    );
	 
	 
}
