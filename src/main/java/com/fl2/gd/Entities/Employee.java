package com.fl2.gd.Entities;


import java.util.Date;

import jakarta.persistence.CascadeType;
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

@Entity
@Table(name = "employees")
public class Employee {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;
	
	@Column(unique = true)
	String cin;
	
	String name;
	String lastName;
	String function;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "departement_id")
	private Departement departement;
	  
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private AppUser appUser ;
	
	double salary;
	double timeOffDays  = 0;
	Date joinDate;
	String gender;
	@Lob
	@Column(length = 52428800)
	private byte[] profilePic;
	
	Date nextTimeOffDate;
	int takenTimeOffDays = 0;
	boolean onTimeOff;
	boolean avanceSalaireTakenForThisMonth;
	boolean ficheDePaieTakenForThisMonth;
	Date blockedFromDemandeCongeUntil;
	
	int absencesThisMonth = 0;
	boolean absentForToday = false;

	public Employee() {
		super();
	}

	public Employee(Integer employeeId,String cin, 
			String name, String lastName, String function, 
			Departement departement, AppUser appUser) {
		super();
		this.cin = cin;
		this.employeeId = employeeId;
		this.name = name;
		this.lastName = lastName;
		this.function = function;
		this.departement = departement;
		this.appUser = appUser;
	}

   

	public Employee(Integer employeeId, String cin, String name, String lastName, String function,
			Departement departement, AppUser appUser, double salary, double timeOffDays, 
			Date joinDate, String gender, byte[] profilePic, Date nextTimeOffDate, 
			int takenTimeOffDays, boolean onTimeOff, boolean avanceSalaireTakenForThisMonth, 
			boolean ficheDePaieTakenForThisMonth, Date blockedFromDemandeCongeUntil, int absencesThisMonth,
			boolean absentForToday) {
		super();
		this.employeeId = employeeId;
		this.cin = cin;
		this.name = name;
		this.lastName = lastName;
		this.function = function;
		this.departement = departement;
		this.appUser = appUser;
		this.salary = salary;
		this.timeOffDays = timeOffDays;
		this.joinDate = joinDate;
		this.gender = gender;
		this.profilePic = profilePic;
		this.nextTimeOffDate = nextTimeOffDate;
		this.takenTimeOffDays = takenTimeOffDays;
		this.onTimeOff = onTimeOff;
		this.avanceSalaireTakenForThisMonth = avanceSalaireTakenForThisMonth;
		this.ficheDePaieTakenForThisMonth = ficheDePaieTakenForThisMonth;
		this.blockedFromDemandeCongeUntil = blockedFromDemandeCongeUntil;
		this.absencesThisMonth = absencesThisMonth;
		this.absentForToday = absentForToday;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(Departement departement) {
		this.departement = departement;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}




	public double getTimeOffDays() {
		return timeOffDays;
	}

	public void setTimeOffDays(double timeOffDays) {
		this.timeOffDays = timeOffDays;
	}

	public int getTakenTimeOffDays() {
		return takenTimeOffDays;
	}

	public void setTakenTimeOffDays(int takenTimeOffDays) {
		this.takenTimeOffDays = takenTimeOffDays;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public boolean isOnTimeOff() {
		return onTimeOff;
	}

	public void setOnTimeOff(boolean onTimeOff) {
		this.onTimeOff = onTimeOff;
	}

	public Date getNextTimeOffDate() {
		return nextTimeOffDate;
	}

	public void setNextTimeOffDate(Date nextTimeOffDate) {
		this.nextTimeOffDate = nextTimeOffDate;
	}

	public boolean isAvanceSalaireTakenForThisMonth() {
		return avanceSalaireTakenForThisMonth;
	}

	public void setAvanceSalaireTakenForThisMonth(boolean avanceSalaireTakenForThisMonth) {
		this.avanceSalaireTakenForThisMonth = avanceSalaireTakenForThisMonth;
	}

	public boolean isFicheDePaieTakenForThisMonth() {
		return ficheDePaieTakenForThisMonth;
	}

	public void setFicheDePaieTakenForThisMonth(boolean ficheDePaieTakenForThisMonth) {
		this.ficheDePaieTakenForThisMonth = ficheDePaieTakenForThisMonth;
	}

	public Date getBlockedFromDemandeCongeUntil() {
		return blockedFromDemandeCongeUntil;
	}

	public void setBlockedFromDemandeCongeUntil(Date blockedFromDemandeCongeUntil) {
		this.blockedFromDemandeCongeUntil = blockedFromDemandeCongeUntil;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public byte[] getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(byte[] profilePic) {
		this.profilePic = profilePic;
	}

	public int getAbsencesThisMonth() {
		return absencesThisMonth;
	}

	public void setAbsencesThisMonth(int absencesThisMonth) {
		this.absencesThisMonth = absencesThisMonth;
	}

	public boolean isAbsentForToday() {
		return absentForToday;
	}

	public void setAbsentForToday(boolean absentForToday) {
		this.absentForToday = absentForToday;
	}

	

	
	

}
