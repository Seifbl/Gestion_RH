package com.fl2.gd.Entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@Table(name = "demandes-remboursement-assurance")
public class DemandeRemboursementAssurance {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer demandeRemboursementAssuranceId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "employee_id")
	private Employee employee;
	
	@Lob
	@Column(length = 52428800)
	private byte[] requestDocuments;
	
	String documentName;
	
	Date requestDate;
	boolean approvedByAdmin;
	boolean approvedByCD;
	String status = "enAttente";
	boolean finalised;
	
	public DemandeRemboursementAssurance() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DemandeRemboursementAssurance(Integer demandeRemboursementAssuranceId, Employee employee,
			byte[] requestDocuments, Date requestDate, boolean approvedByAdmin, 
			boolean approvedByCD, String status, String documentName, boolean finalised) {
		super();
		this.demandeRemboursementAssuranceId = demandeRemboursementAssuranceId;
		this.employee = employee;
		this.requestDocuments = requestDocuments;
		this.requestDate = requestDate;
		this.approvedByAdmin = approvedByAdmin;
		this.approvedByCD = approvedByCD;
		this.status = status;
		this.documentName = documentName;
		this.finalised = finalised;
	}

	public Integer getDemandeRemboursementAssuranceId() {
		return demandeRemboursementAssuranceId;
	}

	public void setDemandeRemboursementAssuranceId(Integer demandeRemboursementAssuranceId) {
		this.demandeRemboursementAssuranceId = demandeRemboursementAssuranceId;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public byte[] getRequestDocuments() {
		return requestDocuments;
	}

	public void setRequestDocuments(byte[] requestDocuments) {
		this.requestDocuments = requestDocuments;
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

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public boolean isFinalised() {
		return finalised;
	}

	public void setFinalised(boolean finalised) {
		this.finalised = finalised;
	}
	
}
