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
@Table(name = "demandes-conge")
public class DemandeConge {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer demandeCongeId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "employee_id")
	private Employee employee;
	
	String typeConge;
	Date startDate;
	Date endDate;
	Date returnDate;
	
	@OneToOne
	@JoinColumn(name = "remplacant_employee_id")
	private Employee remplacant = null;
	
	Date requestDate;
	boolean approvedByAdmin;
	boolean approvedByCD;
	String status = "enAttente";
	boolean finalised;

	public DemandeConge() {
		super();
	}

	public DemandeConge(Integer demandeCongeId, Employee employee, String typeConge, Date startDate, Date endDate,
			Date returnDate, Employee remplacant, Date requestDate, boolean approvedByAdmin, 
			boolean approvedByCD, String status,boolean finalised) {
		super();
		this.demandeCongeId = demandeCongeId;
		this.employee = employee;
		this.typeConge = typeConge;
		this.startDate = startDate;
		this.endDate = endDate;
		this.returnDate = returnDate;
		this.remplacant = remplacant;
		this.requestDate = requestDate;
		this.approvedByAdmin = approvedByAdmin;
		this.approvedByCD = approvedByCD;
		this.status = status;
		this.finalised = finalised;
	}

	public Integer getDemandeCongeId() {
		return demandeCongeId;
	}

	public void setDemandeCongeId(Integer demandeCongeId) {
		this.demandeCongeId = demandeCongeId;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getTypeConge() {
		return typeConge;
	}

	public void setTypeConge(String typeConge) {
		this.typeConge = typeConge;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Employee getRemplacant() {
		return remplacant;
	}

	public void setRemplacant(Employee remplacant) {
		this.remplacant = remplacant;
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
