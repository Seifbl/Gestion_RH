package com.fl2.gd.Services;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fl2.gd.Entities.DemandeRemboursementAssurance;
import com.fl2.gd.Repositories.DemandeRemboursementAssuranceRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class DemandeRemboursementAssuranceService {
	
	@Autowired
	private DemandeRemboursementAssuranceRepository demandeRemboursementAssuranceRepo;
	@Autowired
	private EntityManager entityManager;
	
	public List<DemandeRemboursementAssurance> getAllDemandesRemboursementAssurance(){
		return demandeRemboursementAssuranceRepo.findAll();
	}
	
	public DemandeRemboursementAssurance getDemandeRemboursementAssuranceById(int id){
		return demandeRemboursementAssuranceRepo.findById(id).get();
	}
	
	public void saveDemandeRemboursementAssurance(DemandeRemboursementAssurance demandeRemboursementAssurance) {
		demandeRemboursementAssuranceRepo.save(demandeRemboursementAssurance);
	}
	
	public void deleteDemandeRemboursementAssurance(int id) {
		demandeRemboursementAssuranceRepo.deleteById(id);
	}
	
	public List<DemandeRemboursementAssurance> getAllRecentDemandesRemboursementAssurance(){
		return demandeRemboursementAssuranceRepo.findAllRecentDemandesRemboursementAssurance();
	}

	public DemandeRemboursementAssurance checkIfEmployeeHasUntreatedRequest(int id){
		return demandeRemboursementAssuranceRepo.checkIfEmployeeHasUntreatedRequest(id);
	}
	
	public List<DemandeRemboursementAssurance> findDemandeRemboursementAssuranceByEmployeeId(int id){
		return demandeRemboursementAssuranceRepo.findDemandeRemboursementAssuranceByEmployeeId(id);
	}
	
	public List<DemandeRemboursementAssurance> findDemandeRemboursementAssuranceByDepartementId(int id){
		return demandeRemboursementAssuranceRepo.findDemandeRemboursementAssuranceByDepartementId(id);
	}
	
	public List<DemandeRemboursementAssurance> findDemandeRemboursementAssuranceForChefDepartement(int id){
		return demandeRemboursementAssuranceRepo.findDemandeRemboursementAssuranceForChefDepartement(id);
	}
	
	public List<DemandeRemboursementAssurance> findDemandeRemboursementAssuranceForAdmin(int id){
		return demandeRemboursementAssuranceRepo.findDemandeRemboursementAssuranceForAdmin(id);
	}
	public List<DemandeRemboursementAssurance> findAllRecentDemandesRemboursementAssuranceForAdmin(){
		return demandeRemboursementAssuranceRepo.findAllRecentDemandesRemboursementAssuranceForAdmin();
	}
	
	//_______________________________________________________________________
	
	public List<DemandeRemboursementAssurance> findFinalisedDemandeRemboursementAssuranceByDepartementId(int id){
		return demandeRemboursementAssuranceRepo.findFinalisedDemandeRemboursementAssuranceByDepartementId(id);
	}
	public List<DemandeRemboursementAssurance> findFinalisedDemandeRemboursementAssuranceByEmployeeId(int id){
		return demandeRemboursementAssuranceRepo.findFinalisedDemandeRemboursementAssuranceByEmployeeId(id);
	}
	public List<DemandeRemboursementAssurance> findAllFinalisedDemandesRemboursementAssurance(){
		return demandeRemboursementAssuranceRepo.findAllFinalisedDemandesRemboursementAssurance();
	}
	public List<DemandeRemboursementAssurance> findDemandeRemboursementAssuranceByFilter(int id, Date date){
		return demandeRemboursementAssuranceRepo.findDemandeRemboursementAssuranceByFilter(id, date);
	}

}
