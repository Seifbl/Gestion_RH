package com.fl2.gd.Repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fl2.gd.Entities.DemandeRemboursementAssurance;


public interface DemandeRemboursementAssuranceRepository extends 
JpaRepository<DemandeRemboursementAssurance,Integer> {

	 @Query("SELECT dra FROM DemandeRemboursementAssurance dra ORDER BY dra.requestDate DESC")
	 List<DemandeRemboursementAssurance> findAllRecentDemandesRemboursementAssurance();
	 
	 @Query("SELECT dra FROM DemandeRemboursementAssurance dra"
	 		+ " WHERE dra.employee.employeeId = :id"
	 		+ " AND (dra.approvedByCD = false OR dra.approvedByAdmin = false)"
	 		+ " AND dra.finalised = false")
	 DemandeRemboursementAssurance checkIfEmployeeHasUntreatedRequest(int id);
	 	 	 
	 @Query("SELECT dra FROM DemandeRemboursementAssurance dra"
	 		+ " WHERE dra.employee.employeeId = :id")
	 List<DemandeRemboursementAssurance> findDemandeRemboursementAssuranceByEmployeeId(int id);
	 
	 @Query("SELECT dra FROM DemandeRemboursementAssurance dra"
	 		+ " WHERE dra.employee.departement.departementId = :id")
	 List<DemandeRemboursementAssurance> findDemandeRemboursementAssuranceByDepartementId(int id);
	 
	 @Query("SELECT dra FROM DemandeRemboursementAssurance dra"
	 		+ " WHERE dra.employee.departement.departementId = :id" 
	        +" AND dra.approvedByCD = false AND dra.approvedByAdmin = false "
	        + " AND dra.finalised = false")
	 List<DemandeRemboursementAssurance> findDemandeRemboursementAssuranceForChefDepartement(int id);
	 
	 @Query("SELECT dra FROM DemandeRemboursementAssurance dra"
	 		+ " WHERE dra.employee.departement.departementId = :id" 
	        +" AND dra.approvedByCD = true AND dra.approvedByAdmin = false "
	        + " AND dra.finalised = false")
	 List<DemandeRemboursementAssurance> findDemandeRemboursementAssuranceForAdmin(int id);
	 
	 @Query("SELECT dra FROM DemandeRemboursementAssurance dra WHERE dra.approvedByCD = true "
	 		+ "AND dra.approvedByAdmin = false AND dra.finalised = false "
	        + "ORDER BY dra.requestDate DESC")
	 List<DemandeRemboursementAssurance> findAllRecentDemandesRemboursementAssuranceForAdmin();
	 
		//_______________________________________________________________________
	
	 @Query("SELECT dra FROM DemandeRemboursementAssurance dra WHERE dra.finalised = true")
	 List<DemandeRemboursementAssurance> findAllFinalisedDemandesRemboursementAssurance();
	 @Query("SELECT dra FROM DemandeRemboursementAssurance dra"
		 		+ " WHERE dra.employee.employeeId = :id "
		 		+ "AND dra.finalised = true")
	  List<DemandeRemboursementAssurance> findFinalisedDemandeRemboursementAssuranceByEmployeeId(int id);
		 
	  @Query("SELECT dra FROM DemandeRemboursementAssurance dra"
		 		+ " WHERE dra.employee.departement.departementId = :id "
		 		+ "AND dra.finalised = true")
	  List<DemandeRemboursementAssurance> findFinalisedDemandeRemboursementAssuranceByDepartementId(int id);

		 @Query("SELECT dra FROM DemandeRemboursementAssurance dra "
		         + "WHERE (:id IS NULL OR dra.employee.departement.departementId = :id) "
		         + "AND (:date IS NULL OR dra.requestDate < :date)")
		    List<DemandeRemboursementAssurance> findDemandeRemboursementAssuranceByFilter(
		        int id, 
		        Date date
		    );

}
