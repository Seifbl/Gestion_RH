package com.fl2.gd.Entities;

import java.util.Date;

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
@Table(name = "demandes-fiche-de-paie")
public class DemandeFicheDePaie {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer demandeFicheDePaieId;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "employee_id")
	private Employee employee;
	
	Date requestDate;
	boolean approvedByAdmin;
	boolean approvedByCD;
	String status = "enAttente";
	boolean finalised;
	
	public DemandeFicheDePaie() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DemandeFicheDePaie(Integer demandeFicheDePaieId, Employee employee, Date requestDate,
			boolean approvedByAdmin, boolean approvedByCD,String status, boolean finalised) {
		super();
		this.demandeFicheDePaieId = demandeFicheDePaieId;
		this.employee = employee;
		this.requestDate = requestDate;
		this.approvedByAdmin = approvedByAdmin;
		this.approvedByCD = approvedByCD;
		this.status = status;
		this.finalised = finalised;
	}

	public Integer getDemandeFicheDePaieId() {
		return demandeFicheDePaieId;
	}

	public void setDemandeFicheDePaieId(Integer demandeFicheDePaieId) {
		this.demandeFicheDePaieId = demandeFicheDePaieId;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
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
