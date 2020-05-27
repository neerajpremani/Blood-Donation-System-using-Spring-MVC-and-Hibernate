package com.neu.bloodbank.pojo;


import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;


@Entity
@Table(name="RequestBlood")
public class RequestBlood {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Id", unique = true, nullable = false)
	private long id;
	
	@Column(name="date_requested")
	private Date requestDate;
	
	@Column(name="blood_type")
	private String bloodType;
	
	@Column(name="bloodRequestAmount")
	private int bloodAmount;
	
	@Column(name="status")
	private String confirmation;
	
	@ManyToOne
	@JoinColumn(name="bloodbank_id")
	private BloodBank bloodBank;
	
	@ManyToOne
	@JoinColumn(name="hospital_id")
	private Hospital hospital;

	public RequestBlood() {
		
		
	}

	public RequestBlood(long id, Date requestDate, String bloodType, int bloodAmount, String confirmation, BloodBank bloodBank,
			Hospital hospital) {
		super();
		this.id = id;
		this.requestDate = requestDate;
		this.bloodType = bloodType;
		this.bloodAmount = bloodAmount;
		this.confirmation = confirmation;
		this.bloodBank = bloodBank;
		this.hospital = hospital;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public int getBloodAmount() {
		return bloodAmount;
	}

	public void setBloodAmount(int bloodAmount) {
		this.bloodAmount = bloodAmount;
	}

	public String getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}

	public BloodBank getBloodBank() {
		return bloodBank;
	}

	public void setBloodBank(BloodBank bloodBank) {
		this.bloodBank = bloodBank;
	}

	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	@Override
	public String toString() {
		return "BloodRequest [id=" + id + ", requestDate=" + requestDate + ", bloodType=" + bloodType + ", bloodAmount=" + bloodAmount
				+ ", confirmation=" + confirmation + ", bloodBank=" + bloodBank + ", hospital=" + hospital + "]";
	}

}
