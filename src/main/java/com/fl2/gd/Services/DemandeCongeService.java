package com.fl2.gd.Services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fl2.gd.Entities.DemandeConge;
import com.fl2.gd.Repositories.DemandeCongeRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DemandeCongeService {
	@Autowired
	DemandeCongeRepository demandeCongeRepo;
	
	public List<DemandeConge> getAllDemandesConge(){
		return demandeCongeRepo.findAll();
	}
	
	public DemandeConge getDemandeCongeById(int id){
		return demandeCongeRepo.findById(id).get();
	}
	
	public void saveDemandeConge(DemandeConge demandeConge) {
		demandeCongeRepo.save(demandeConge);
	}
	
	public void deleteDemandeConge(int id) {
		demandeCongeRepo.deleteById(id);
	}
	
	public List<DemandeConge> getAllRecentDemandesConge(){
		return demandeCongeRepo.findAllRecentDemandesConge();
	}

	public DemandeConge checkIfEmployeeHasUntreatedRequest(int id){
		return demandeCongeRepo.checkIfEmployeeHasUntreatedRequest(id);
	}
	
	public List<DemandeConge> findDemandeCongeByEmployeeId(int id){
		return demandeCongeRepo.findDemandeCongeByEmployeeId(id);
	}
	
	public List<DemandeConge> findDemandeCongeByDepartementId(int id){
		return demandeCongeRepo.findDemandeCongeByDepartementId(id);
	}
	
	public List<DemandeConge> findDemandeCongeForChefDepartement(int id){
		return demandeCongeRepo.findDemandeCongeForChefDepartement(id);
	}
	
	public List<DemandeConge> findDemandeCongeForAdmin(int id){
		return demandeCongeRepo.findDemandeCongeForAdmin(id);
	}
	public List<DemandeConge> findAllRecentDemandesCongeForAdmin(){
		return demandeCongeRepo.findAllRecentDemandesCongeForAdmin();
	}
		 
	 //_______________________________________________________________________
	 
	public List<DemandeConge> findFinalisedDemandeCongeByDepartementId(int id){
		return demandeCongeRepo.findFinalisedDemandeCongeByDepartementId(id);
	}
	public List<DemandeConge> findFinalisedDemandeCongeByEmployeeId(int id){
		return demandeCongeRepo.findFinalisedDemandeCongeByEmployeeId(id);
	}
	public List<DemandeConge> findAllFinalisedDemandesConge(){
		return demandeCongeRepo.findAllFinalisedDemandesConge();
	}
	
	public List<DemandeConge> findDemandeCongeByFilter(int id, Date date){
		return demandeCongeRepo.findDemandeCongeByFilter(id, date);
	}
	
}
