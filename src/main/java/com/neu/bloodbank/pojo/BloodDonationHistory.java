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

@Entity
@Table(name = "DonationHistory")
public class BloodDonationHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id", unique = true, nullable = false)
	private long id;

	@Column(name = "date_donated")
	private Date donatedDate;

	@Column(name = "bloodType")
	private String bloodType;

	@Column(name = "bloodAmount")
	private int bloodAmount;

	@ManyToOne
	@JoinColumn(name = "bloodbank_id")
	private BloodBank bloodBank;

	@ManyToOne
	@JoinColumn(name = "donor_id")
	private Donor donor;

	public BloodDonationHistory() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDonatedDate() {
		return donatedDate;
	}

	public void setDonatedDate(Date donatedDate) {
		this.donatedDate = donatedDate;
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

	public Donor getDonor() {
		return donor;
	}

	public void setDonor(Donor donor) {
		this.donor = donor;
	}

	@Override
	public String toString() {

		return "id " + id;
	}

}
