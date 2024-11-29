package com.fl2.gd.Services;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fl2.gd.Entities.DemandeAttestationTravail;
import com.fl2.gd.Repositories.DemandeAttestationTravailRepository;

import jakarta.transaction.Transactional;




@Service
@Transactional
public class DemandeAttestationTravailService {
	
	@Autowired
	private DemandeAttestationTravailRepository DemandeAttestationTravailRepo;
	
	public List<DemandeAttestationTravail> getAllDemandesAttestationTravail(){
		return DemandeAttestationTravailRepo.findAll();
	}
	
	public DemandeAttestationTravail getDemandeAttestationTravailById(int id){
		return DemandeAttestationTravailRepo.findById(id).get();
	}
	
	public void saveDemandeAttestationTravail(DemandeAttestationTravail demandeAttestationTravail) {
		DemandeAttestationTravailRepo.save(demandeAttestationTravail);
	}
	
	public void deleteDemandeAttestationTravail(int id) {
		DemandeAttestationTravailRepo.deleteById(id);
	}
	
	public List<DemandeAttestationTravail> getAllRecentDemandesAttestationTravail(){
		return DemandeAttestationTravailRepo.findAllRecentDemandesAttestationTravail();
	}
	
	public DemandeAttestationTravail checkIfEmployeeHasUntreatedRequest(int id){
		return DemandeAttestationTravailRepo.checkIfEmployeeHasUntreatedRequest(id);
	}
	
	public List<DemandeAttestationTravail> findDemandeAttestationTravailByEmployeeId(int id){
		return DemandeAttestationTravailRepo.findDemandeAttestationTravailByEmployeeId(id);
	}
	
	public List<DemandeAttestationTravail> findDemandeAttestationTravailByDepartementId(int id){
		return DemandeAttestationTravailRepo.findDemandeAttestationTravailByDepartementId(id);
	}
	
	public List<DemandeAttestationTravail> findDemandeAttestationTravailForChefDepartement(int id){
		return DemandeAttestationTravailRepo.findDemandeAttestationTravailForChefDepartement(id);
	}
	
	public List<DemandeAttestationTravail> findDemandeAttestationTravailForAdmin(int id){
		return DemandeAttestationTravailRepo.findDemandeAttestationTravailForAdmin(id);
	}
	public List<DemandeAttestationTravail> findAllRecentDemandesAttestationTravailForAdmin(){
		return DemandeAttestationTravailRepo.findAllRecentDemandesAttestationTravailForAdmin();
	}

	 //_______________________________________________________________________
	
	public List<DemandeAttestationTravail> findFinalisedDemandeAttestationTravailByDepartementId(int id){
		return DemandeAttestationTravailRepo.findFinalisedDemandeAttestationTravailByDepartementId(id);
	}
	public List<DemandeAttestationTravail> findFinalisedDemandeAttestationTravailByEmployeeId(int id){
		return DemandeAttestationTravailRepo.findFinalisedDemandeAttestationTravailByEmployeeId(id);
	}
	public List<DemandeAttestationTravail> findFinalisedDemandeAttestationTravail(){
		return DemandeAttestationTravailRepo.findFinalisedDemandeAttestationTravail();
	}
	public List<DemandeAttestationTravail> findDemandeAttestationTravailByFilter(int id, Date date){
		return DemandeAttestationTravailRepo.findDemandeAttestationTravailByFilter(id, date);
	}
 
}
