package com.fl2.gd.Entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="departements")
public class Departement {

	@Id
	@GeneratedValue 
	private Integer departementId;
	private String departementName;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "employee_id")
	private Employee chefDepartement;
	
	public Departement() {
		super();
	}

	public Departement(Integer departementId, String departementName) {
		super();
		this.departementId = departementId;
		this.departementName = departementName;
	}

	
	
	public Departement(Integer departementId, String departementName, Employee chefDepartement) {
		super();
		this.departementId = departementId;
		this.departementName = departementName;
		this.chefDepartement = chefDepartement;
	}

	public Integer getDepartementId() {
		return departementId;
	}

	public void setDepartementId(Integer departementId) {
		this.departementId = departementId;
	}

	public String getDepartementName() {
		return departementName;
	}

	public void setDepartementName(String departementName) {
		this.departementName = departementName;
	}

	public Employee getChefDepartement() {
		return chefDepartement;
	}

	public void setChefDepartement(Employee chefDepartement) {
		this.chefDepartement = chefDepartement;
	}
	
	
	
}
