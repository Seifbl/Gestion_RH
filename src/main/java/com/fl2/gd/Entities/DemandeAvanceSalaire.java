package com.fl2.gd.Entities;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "demandes-avance-salaire")
public class DemandeAvanceSalaire {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer demandeAvanceSalaireId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "employee_id")
	private Employee employee;
	
	 double montant;
	 Date requestDate;
	 boolean approvedByAdmin;
	 boolean approvedByCD;
	 String status = "enAttente";
	 boolean finalised;
	 
	public DemandeAvanceSalaire() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DemandeAvanceSalaire(Integer demandeAvanceSalaireId, Employee employee, 
			double montant, Date requestDate, boolean approvedByAdmin, boolean approvedByCD,
			String status, boolean finalised) {
		super();
		this.demandeAvanceSalaireId = demandeAvanceSalaireId;
		this.employee = employee;
		this.montant = montant;
		this.requestDate = requestDate;
		this.approvedByAdmin = approvedByAdmin;
		this.approvedByCD = approvedByCD;
		this.status = status;
		this.finalised = finalised;
	}

	public Integer getDemandeAvanceSalaireId() {
		return demandeAvanceSalaireId;
	}

	public void setDemandeAvanceSalaireId(Integer demandeAvanceSalaireId) {
		this.demandeAvanceSalaireId = demandeAvanceSalaireId;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}


	public boolean isApprovedByAdmin() {
		return approvedByAdmin;
	}

	public void setApprovedByAdmin(boolean approvedByAdmin) {
		this.approvedByAdmin = approvedByAdmin;
	}

	public boolean isApprovedByCD() {
		return approvedByCD;
	}

	public void setApprovedByCD(boolean approvedByCD) {
		this.approvedByCD = approvedByCD;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isFinalised() {
		return finalised;
	}

	public void setFinalised(boolean finalised) {
		this.finalised = finalised;
	}
	

}
