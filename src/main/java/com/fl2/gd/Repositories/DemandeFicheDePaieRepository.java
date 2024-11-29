package com.fl2.gd.Repositories;


import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fl2.gd.Entities.DemandeFicheDePaie;



public interface DemandeFicheDePaieRepository extends JpaRepository<DemandeFicheDePaie,Integer> {

	 @Query("SELECT dfp FROM DemandeFicheDePaie dfp ORDER BY dfp.requestDate DESC")
	 List<DemandeFicheDePaie> findAllRecentDemandesFicheDePaie();
	 
	 @Query("SELECT dfp FROM DemandeFicheDePaie dfp WHERE dfp.employee.employeeId = :id"
	 		+ " AND (dfp.approvedByCD = false OR dfp.approvedByAdmin = false) AND dfp.finalised = false")
	 DemandeFicheDePaie checkIfEmployeeHasUntreatedRequest(int id);
	 	 	 
	 @Query("SELECT dfp FROM DemandeFicheDePaie dfp WHERE dfp.employee.employeeId = :id")
	 List<DemandeFicheDePaie> findDemandeFicheDePaieByEmployeeId(int id);
	 
	 @Query("SELECT dfp FROM DemandeFicheDePaie dfp "
	 		+ "WHERE dfp.employee.departement.departementId = :id")
	 List<DemandeFicheDePaie> findDemandeFicheDePaieByDepartementId(int id);
	 
	 @Query("SELECT dfp FROM DemandeFicheDePaie dfp"
	 		+ " WHERE dfp.employee.departement.departementId = :id" 
	        +" AND dfp.approvedByCD = false AND dfp.approvedByAdmin = false "
	        + "AND dfp.finalised = false")
	 List<DemandeFicheDePaie> findDemandeFicheDePaieForChefDepartement(int id);
	 
	 @Query("SELECT dfp FROM DemandeFicheDePaie dfp"
	 		+ " WHERE dfp.employee.departement.departementId = :id" 
	        +" AND dfp.approvedByCD = true AND dfp.approvedByAdmin = false "
	        + "AND dfp.finalised = false")
	 List<DemandeFicheDePaie> findDemandeFicheDePaieForAdmin(int id);
	 
	 @Query("SELECT dfp FROM DemandeFicheDePaie dfp WHERE dfp.approvedByCD = true "
	 		+ "AND dfp.approvedByAdmin = false AND dfp.finalised = false "
	        + "ORDER BY dfp.requestDate DESC")
	 List<DemandeFicheDePaie> findAllRecentDemandesFicheDePaieForAdmin();
	 
	//_______________________________________________________________________
	 
	 @Query("SELECT dfp FROM DemandeFicheDePaie dfp WHERE dfp.finalised = true")
	 List<DemandeFicheDePaie> findAllFinalisedDemandesFicheDePaie();
	
	 @Query("SELECT dfp FROM DemandeFicheDePaie dfp WHERE dfp.employee.employeeId = :id "
	 		+ "AND dfp.finalised = true")
	 List<DemandeFicheDePaie> findFinalisedDemandeFicheDePaieByEmployeeId(int id);
	 
	 @Query("SELECT dfp FROM DemandeFicheDePaie dfp "
	 		+ "WHERE dfp.employee.departement.departementId = :id "
	 		+ "AND dfp.finalised = true")
	 List<DemandeFicheDePaie> findFinalisedDemandeFicheDePaieByDepartementId(int id);
	 
	 @Query("SELECT dfp FROM DemandeFicheDePaie dfp "
	         + "WHERE (:id IS NULL OR dfp.employee.departement.departementId = :id) "
	         + "AND (:date IS NULL OR dfp.requestDate < :date)")
	    List<DemandeFicheDePaie> findDemandeFicheDePaieByFilter(
	        int id, 
	        Date date
	    );
}
