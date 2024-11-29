package com.fl2.gd.Services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fl2.gd.Entities.DemandeFicheDePaie;
import com.fl2.gd.Repositories.DemandeFicheDePaieRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DemandeFicheDePaieService {

	@Autowired
	private DemandeFicheDePaieRepository demandeFicheDePaieRepo ;
	
	public List<DemandeFicheDePaie> getAllDemandesFicheDePaie(){
		return demandeFicheDePaieRepo.findAll();
	}
	
	public DemandeFicheDePaie getDemandeFicheDePaieById(int id){
		return demandeFicheDePaieRepo.findById(id).get();
	}
	
	public void saveDemandeFicheDePaie(DemandeFicheDePaie demandeFicheDePaie) {
		demandeFicheDePaieRepo.save(demandeFicheDePaie);
	}
	
	public void deleteDemandeFicheDePaie(int id) {
		demandeFicheDePaieRepo.deleteById(id);
	}
	
	public List<DemandeFicheDePaie> getAllRecentDemandesFicheDePaie(){
		return demandeFicheDePaieRepo.findAllRecentDemandesFicheDePaie();
	}

	public DemandeFicheDePaie checkIfEmployeeHasUntreatedRequest(int id){
		return demandeFicheDePaieRepo.checkIfEmployeeHasUntreatedRequest(id);
	}
	
	public List<DemandeFicheDePaie> findDemandeFicheDePaieByEmployeeId(int id){
		return demandeFicheDePaieRepo.findDemandeFicheDePaieByEmployeeId(id);
	}
	
	public List<DemandeFicheDePaie> findDemandeFicheDePaieByDepartementId(int id){
		return demandeFicheDePaieRepo.findDemandeFicheDePaieByDepartementId(id);
	}
	
	public List<DemandeFicheDePaie> findDemandeFicheDePaieForChefDepartement(int id){
		return demandeFicheDePaieRepo.findDemandeFicheDePaieForChefDepartement(id);
	}
	
	public List<DemandeFicheDePaie> findDemandeFicheDePaieForAdmin(int id){
		return demandeFicheDePaieRepo.findDemandeFicheDePaieForAdmin(id);
	}
	public List<DemandeFicheDePaie> findAllRecentDemandesFicheDePaieForAdmin(){
		return demandeFicheDePaieRepo.findAllRecentDemandesFicheDePaieForAdmin();
	}
	
	//_______________________________________________________________________
	
	public List<DemandeFicheDePaie> findFinalisedDemandeFicheDePaieByEmployeeId(int id){
		return demandeFicheDePaieRepo.findFinalisedDemandeFicheDePaieByEmployeeId(id);
	}
	public List<DemandeFicheDePaie> findFinalisedDemandeFicheDePaieByDepartementId(int id){
		return demandeFicheDePaieRepo.findFinalisedDemandeFicheDePaieByDepartementId(id);
	}
	public List<DemandeFicheDePaie> findAllFinalisedDemandesFicheDePaie(){
		return demandeFicheDePaieRepo.findAllFinalisedDemandesFicheDePaie();
	}
	public List<DemandeFicheDePaie> findDemandeFicheDePaieByFilter(int id, Date date){
		return demandeFicheDePaieRepo.findDemandeFicheDePaieByFilter(id, date);
	}
	
	
	
}
