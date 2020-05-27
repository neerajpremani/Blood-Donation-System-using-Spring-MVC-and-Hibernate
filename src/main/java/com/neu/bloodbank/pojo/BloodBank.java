package com.neu.bloodbank.pojo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.neu.bloodbank.validuser.CheckRegisterEmail;
import com.neu.bloodbank.validuser.CheckRegisterUsername;




@Entity
@Table(name="BloodBank")
public class BloodBank {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Id", unique = true, nullable = false)
	private long id;
	
	@Column(name="Name")
	@NotNull(message="* Mandatory")
	private String name;
	
	@CheckRegisterEmail
	@Column(name="Email")
	@Pattern(regexp="[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+", message="Invalid Email id Pattern aaa@ggg.cccc")
	@NotNull(message="* Mandatory")
	private String email;
	
	@Column(name="Phone")
	@Pattern(regexp="\\d{3}-\\d{3}-\\d{4}", message="Pattern must be xxx-yyy-zzzz")
	@NotNull(message="* Mandatory")
	private String phone;
	
	@CheckRegisterUsername
	@Column(name="Username")
	@NotNull(message="* Mandatory")
	@Size(min=4, max=16, message="Minimum 4 and Maximum 16 characters required")
	private String userName;
	
	@Column(name="Password")
	@NotNull(message="* Mandatory")
	@Size(min=4, message="Password must be atleast 4 characters!")
	private String password;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="Role_Fk")
	private SuperRole superRole;
	
	@OneToMany(mappedBy="bloodBank")
	private List<BloodDonationHistory> bloodDonationHistoryList;
	
	@OneToMany(mappedBy="bloodBank")
	private List<RequestBlood> bloodRequestList;
	
	
	@OneToMany(mappedBy="bloodBank")
	private List<BBStock> bbstock;
	
	public BloodBank() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public List<BBStock> getBbstock() {
		return bbstock;
	}

	public void setBbstock(List<BBStock> bbstock) {
		this.bbstock = bbstock;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public SuperRole getSuperRole() {
		return superRole;
	}

	public void setSuperRole(SuperRole superRole) {
		this.superRole = superRole;
	}

	public List<BloodDonationHistory> getBloodDonationHistoryList() {
		return bloodDonationHistoryList;
	}

	public void setBloodDonationHistoryList(List<BloodDonationHistory> bloodDonationHistoryList) {
		this.bloodDonationHistoryList = bloodDonationHistoryList;
	}

	public List<RequestBlood> getBloodRequestList() {
		return bloodRequestList;
	}

	public void setBloodRequestList(List<RequestBlood> bloodRequestList) {
		this.bloodRequestList = bloodRequestList;
	}
	
	@Override
	public String toString() {
		return "id "+ id;
	}
	
}
