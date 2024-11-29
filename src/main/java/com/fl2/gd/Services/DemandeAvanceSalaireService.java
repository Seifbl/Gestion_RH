package com.fl2.gd.Services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fl2.gd.Entities.DemandeAvanceSalaire;
import com.fl2.gd.Repositories.DemandeAvanceSalaireRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DemandeAvanceSalaireService {
	
	@Autowired
	private DemandeAvanceSalaireRepository demandeAvanceSalaireRepo;
	
	public List<DemandeAvanceSalaire> getAllDemandesAvanceSalaire(){
		return demandeAvanceSalaireRepo.findAll();
	}
	
	public DemandeAvanceSalaire getDemandeAvanceSalaireById(int id){
		return demandeAvanceSalaireRepo.findById(id).get();
	}
	
	public void saveDemandeAvanceSalaire(DemandeAvanceSalaire demandeAvanceSalaire) {
		demandeAvanceSalaireRepo.save(demandeAvanceSalaire);
	}
	
	public void deleteDemandeAvanceSalaire(int id) {
		demandeAvanceSalaireRepo.deleteById(id);
	}
	
	public List<DemandeAvanceSalaire> getAllRecentDemandesAvanceSalaire(){
		return demandeAvanceSalaireRepo.findAllRecentDemandesAvanceSalaire();
	}

	public DemandeAvanceSalaire checkIfEmployeeHasUntreatedRequest(int id){
		return demandeAvanceSalaireRepo.checkIfEmployeeHasUntreatedRequest(id);
	}
	
	public List<DemandeAvanceSalaire> findDemandeAvanceSalaireByEmployeeId(int id){
		return demandeAvanceSalaireRepo.findDemandeAvanceSalaireByEmployeeId(id);
	}
	
	public List<DemandeAvanceSalaire> findDemandeAvanceSalaireByDepartementId(int id){
		return demandeAvanceSalaireRepo.findDemandeAvanceSalaireByDepartementId(id);
	}
	
	public List<DemandeAvanceSalaire> findDemandeAvanceSalaireForChefDepartement(int id){
		return demandeAvanceSalaireRepo.findDemandeAvanceSalaireForChefDepartement(id);
	}
	
	public List<DemandeAvanceSalaire> findDemandeAvanceSalaireForAdmin(int id){
		return demandeAvanceSalaireRepo.findDemandeAvanceSalaireForAdmin(id);
	}
	public List<DemandeAvanceSalaire> findAllRecentDemandesAvanceSalaireForAdmin(){
		return demandeAvanceSalaireRepo.findAllRecentDemandesAvanceSalaireForAdmin();
	}
	
	 
	 //_______________________________________________________________________

	public List<DemandeAvanceSalaire> findFinalisedDemandeAvanceSalaireByDepartementId(int id){
		return demandeAvanceSalaireRepo.findFinalisedDemandeAvanceSalaireByDepartementId(id);
	}
	public List<DemandeAvanceSalaire> findFinalisedDemandeAvanceSalaireByEmployeeId(int id){
		return demandeAvanceSalaireRepo.findFinalisedDemandeAvanceSalaireByEmployeeId(id);
	}
	public List<DemandeAvanceSalaire> findFinalisedDemandesAvanceSalaire(){
		return demandeAvanceSalaireRepo.findFinalisedDemandesAvanceSalaire();
	}
	
	public List<DemandeAvanceSalaire> findDemandeAvanceSalaireByFilter(int id, Date date){
		return demandeAvanceSalaireRepo.findDemandeAvanceSalaireByFilter(id, date);
	}
	
}
