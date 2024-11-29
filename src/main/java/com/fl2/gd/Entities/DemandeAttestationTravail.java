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
@Table(name = "demandes-attestation-travail")
public class DemandeAttestationTravail {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer demandeAttestationTravailId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "employee_id")
	private Employee employee;
	
	String companyName;
	String generalDirectorName;
	Date requestDate;
	String demandeReason;
	boolean approvedByAdmin;
	boolean approvedByCD;
	String status = "enAttente";
	boolean finalised;
	
	public DemandeAttestationTravail() {
		super();
		// TODO Auto-generated constructor stub
	}


	public DemandeAttestationTravail(Integer demandeAttestationTravailId, Employee employee, String companyName,
			String generalDirectorName, Date requestDate, String demandeReason, 
			boolean approvedByAdmin,boolean approvedByCD, String status,boolean finalised) {
		super();
		this.demandeAttestationTravailId = demandeAttestationTravailId;
		this.employee = employee;
		this.companyName = companyName;
		this.generalDirectorName = generalDirectorName;
		this.requestDate = requestDate;
		this.demandeReason = demandeReason;
		this.approvedByAdmin = approvedByAdmin;
		this.approvedByCD = approvedByCD;
		this.status = status;
		this.finalised = finalised;
	}


	public Integer getDemandeAttestationTravailId() {
		return demandeAttestationTravailId;
	}

	public void setDemandeAttestationTravailId(Integer demandeAttestationTravailId) {
		this.demandeAttestationTravailId = demandeAttestationTravailId;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getGeneralDirectorName() {
		return generalDirectorName;
	}

	public void setGeneralDirectorName(String generalDirectorName) {
		this.generalDirectorName = generalDirectorName;
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


	public String getDemandeReason() {
		return demandeReason;
	}


	public void setDemandeReason(String demandeReason) {
		this.demandeReason = demandeReason;
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
