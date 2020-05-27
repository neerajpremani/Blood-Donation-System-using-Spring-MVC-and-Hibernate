package com.neu.bloodbank.pojo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="SuperRole")
public class SuperRole {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Id", unique = true, nullable = false)
	private long id;
	
	@Column(name="Username")
	private String userName;
	
	@Column(name="Password")
	private String password;
	
	@Column(name="SuperRole")
	private String superRole;
	
	@Column(name="Email")
	private String email;
	
	@OneToOne(mappedBy="superRole", cascade = CascadeType.ALL)
	private Hospital hospital;
	
	@OneToOne(mappedBy="superRole", cascade = CascadeType.ALL)
	private Donor donor;
	
	@OneToOne(mappedBy="superRole", cascade = CascadeType.ALL)
	private BloodBank bloodbank;
	
	public BloodBank getBloodbank() {
		return bloodbank;
	}

	public void setBloodbank(BloodBank bloodbank) {
		this.bloodbank = bloodbank;
	}

	public SuperRole() {
		
	}
	
	public SuperRole(long id, String userName, String password, String superRole, String email) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.superRole = superRole;
		this.email = email;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSuperRole() {
		return superRole;
	}

	public void setSuperRole(String superRole) {
		this.superRole = superRole;
	}

	public Hospital getHospital() {
		return hospital;
	}


	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}
	
	public Donor getDonor() {
		return donor;
	}

	public void setDonor(Donor donor) {
		this.donor = donor;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	@Override
	public String toString() {
		return "SuperRole [id=" + id + ", userName=" + userName + ", password=" + password + ", superRole=" + superRole + ", email="
				+ email + "]";
	}

	

}
