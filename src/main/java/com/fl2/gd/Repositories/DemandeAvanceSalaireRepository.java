package com.fl2.gd.Repositories;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fl2.gd.Entities.DemandeAvanceSalaire;

public interface DemandeAvanceSalaireRepository extends JpaRepository<DemandeAvanceSalaire,Integer> {
	@Query("SELECT das FROM DemandeAvanceSalaire das ORDER BY das.requestDate DESC")
	  List<DemandeAvanceSalaire> findAllRecentDemandesAvanceSalaire();
	
	 @Query("SELECT das FROM DemandeAvanceSalaire das WHERE das.employee.employeeId = :id "
	 		+ "AND  (das.approvedByCD = false OR das.approvedByAdmin = false)"
	 		+ " AND das.finalised = false")
	 DemandeAvanceSalaire checkIfEmployeeHasUntreatedRequest(int id);
	 
	 @Query("SELECT das FROM DemandeAvanceSalaire das WHERE das.employee.employeeId = :id")
	 List<DemandeAvanceSalaire> findDemandeAvanceSalaireByEmployeeId(int id);
	 
	 @Query("SELECT das FROM DemandeAvanceSalaire das WHERE das.employee.departement.departementId = :id")
	 List<DemandeAvanceSalaire> findDemandeAvanceSalaireByDepartementId(int id);
	 
	 
	 @Query("SELECT das FROM DemandeAvanceSalaire das WHERE das.employee.departement.departementId = :id" 
	 +" AND das.approvedByCD = false AND das.approvedByAdmin = false AND das.finalised = false")
	 List<DemandeAvanceSalaire> findDemandeAvanceSalaireForChefDepartement(int id);
	 
	 @Query("SELECT das FROM DemandeAvanceSalaire das WHERE das.employee.departement.departementId = :id" 
	 +" AND das.approvedByCD = true AND das.approvedByAdmin = false AND das.finalised = false")
	 List<DemandeAvanceSalaire> findDemandeAvanceSalaireForAdmin(int id);
	 
	 @Query("SELECT das FROM DemandeAvanceSalaire das WHERE das.approvedByCD = true "
		 		+ "AND das.approvedByAdmin = false AND das.finalised = false "
		        + "ORDER BY das.requestDate DESC")
	 List<DemandeAvanceSalaire> findAllRecentDemandesAvanceSalaireForAdmin();
	 
	 //_______________________________________________________________________

	 
	@Query("SELECT das FROM DemandeAvanceSalaire das "
			+ "WHERE das.finalised = true")
	List<DemandeAvanceSalaire> findFinalisedDemandesAvanceSalaire();
	 
	 @Query("SELECT das FROM DemandeAvanceSalaire das "
	 		+ "WHERE das.employee.employeeId = :id "
	 		+ "AND das.finalised = true")
	 List<DemandeAvanceSalaire> findFinalisedDemandeAvanceSalaireByEmployeeId(int id);
	 
	 @Query("SELECT das FROM DemandeAvanceSalaire das "
	 		+ "WHERE das.employee.departement.departementId = :id "
	 		+ "AND das.finalised = true")
	 List<DemandeAvanceSalaire> findFinalisedDemandeAvanceSalaireByDepartementId(int id);
	 
	 @Query("SELECT das FROM DemandeAvanceSalaire das "
	         + "WHERE (:id IS NULL OR das.employee.departement.departementId = :id) "
	         + "AND (:date IS NULL OR das.requestDate < :date)")
	    List<DemandeAvanceSalaire> findDemandeAvanceSalaireByFilter(
	        int id, 
	        Date date
	    );
}
