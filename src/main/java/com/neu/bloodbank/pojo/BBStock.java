package com.neu.bloodbank.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name="BBStock")
public class BBStock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id", unique = true, nullable = false)
	private long id;

	@Column(name = "bloodType")
	private String bloodType;

	@Column(name = "bloodAmount")
	private int bloodAmount;

	@ManyToOne
	@JoinColumn(name = "bloodbank_id")
	private BloodBank bloodBank;
	
	
	public BBStock() {
		// TODO Auto-generated constructor stub
	}
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public BloodBank getBloodBank() {
		return bloodBank;
	}

	public void setBloodBank(BloodBank bloodBank) {
		this.bloodBank = bloodBank;
	}
	
	public String toString()
	{
		return "BBSTOCK" +id;
	}

}
