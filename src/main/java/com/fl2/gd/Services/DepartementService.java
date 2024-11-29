package com.fl2.gd.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fl2.gd.Entities.Departement;
import com.fl2.gd.Repositories.DepartementRepository;

import jakarta.transaction.Transactional;

@Service
public class DepartementService {

	@Autowired
	DepartementRepository departementRepo ;
	
	public List<Departement> getAllDepartements(){
		return departementRepo.findAll();
	}
	
	public Departement getDepartementById(int id) {
		return departementRepo.findById(id).get();
	}
	
	@Transactional
	public void saveDepartement(Departement departement) {
		departementRepo.save(departement);
	}
	
	public void deleteDepartementById(int id) {
		departementRepo.deleteById(id);
	}
}
