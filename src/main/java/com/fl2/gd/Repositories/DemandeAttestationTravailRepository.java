package com.fl2.gd.Repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fl2.gd.Entities.DemandeAttestationTravail;




public interface DemandeAttestationTravailRepository extends JpaRepository<DemandeAttestationTravail, Integer> {
	@Query("SELECT dat FROM DemandeAttestationTravail dat ORDER BY dat.requestDate DESC")
	  List<DemandeAttestationTravail> findAllRecentDemandesAttestationTravail();
	
	@Query("SELECT dat FROM DemandeAttestationTravail dat WHERE dat.employee.employeeId = :id "
			+ "AND (dat.approvedByCD = false OR dat.approvedByAdmin = false)"
			+ " AND dat.finalised = false")
	DemandeAttestationTravail checkIfEmployeeHasUntreatedRequest(int id);
	
	@Query("SELECT dat FROM DemandeAttestationTravail dat WHERE dat.employee.employeeId = :id")
	List<DemandeAttestationTravail> findDemandeAttestationTravailByEmployeeId(int id);
	
	 @Query("SELECT dat FROM DemandeAttestationTravail dat WHERE dat.employee.departement.departementId = :id")
	 List<DemandeAttestationTravail> findDemandeAttestationTravailByDepartementId(int id);
	 
	 
	 @Query("SELECT dat FROM DemandeAttestationTravail dat WHERE dat.employee.departement.departementId = :id" 
	 +" AND dat.approvedByCD = false AND dat.approvedByAdmin = false AND dat.finalised = false")
	 List<DemandeAttestationTravail> findDemandeAttestationTravailForChefDepartement(int id);
	 
	 @Query("SELECT dat FROM DemandeAttestationTravail dat "
	 + "WHERE dat.employee.departement.departementId = :id" 
	 +" AND dat.approvedByCD = true AND dat.approvedByAdmin = false AND dat.finalised = false")
	 List<DemandeAttestationTravail> findDemandeAttestationTravailForAdmin(int id);
	 
	 @Query("SELECT dat FROM DemandeAttestationTravail dat WHERE dat.approvedByCD = true "
		 		+ "AND dat.approvedByAdmin = false AND dat.finalised = false "
		        + "ORDER BY dat.requestDate DESC ")
	 List<DemandeAttestationTravail> findAllRecentDemandesAttestationTravailForAdmin();
	 
	 //_______________________________________________________________________
	 
	 @Query("SELECT dat FROM DemandeAttestationTravail dat "
		 		+ "WHERE dat.finalised = true")
			List<DemandeAttestationTravail> findFinalisedDemandeAttestationTravail();
	 
	 @Query("SELECT dat FROM DemandeAttestationTravail dat"
	 		+ " WHERE dat.employee.departement.departementId = :id "
	 		+ "AND dat.finalised = true")
	 List<DemandeAttestationTravail> findFinalisedDemandeAttestationTravailByDepartementId(int id);
	 
	 @Query("SELECT dat FROM DemandeAttestationTravail dat "
	 		+ "WHERE dat.employee.employeeId = :id "
	 		+ "AND dat.finalised = true")
		List<DemandeAttestationTravail> findFinalisedDemandeAttestationTravailByEmployeeId(int id);
	 
	 
	 @Query("SELECT dat FROM DemandeAttestationTravail dat "
	         + "WHERE (:id IS NULL OR dat.employee.departement.departementId = :id) "
	         + "AND (:date IS NULL OR dat.requestDate < :date)")
	    List<DemandeAttestationTravail> findDemandeAttestationTravailByFilter(
	        int id, 
	        Date date
	    );
}
