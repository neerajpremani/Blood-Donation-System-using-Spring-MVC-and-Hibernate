package com.neu.bloodbank.pojo;


import java.util.Date;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.neu.bloodbank.validuser.CheckRegisterEmail;
import com.neu.bloodbank.validuser.CheckRegisterUsername;



@Entity
@Table(name="Donor")
public class Donor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Id", unique = true, nullable = false)
	private long id;
	
	@Column(name="firstName")
	@NotNull(message = "* Mandatory")
	private String firstName;
	
	@Column(name="lastName")
	@NotNull(message = "* Mandatory")
	private String lastName;
	
	@Column(name="birthDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past(message="Format yyyy-MM-dd ")
	private Date dateOfBirth;
	
	@Column(name="gender")
	@NotNull(message = "* Mandatory")
	private String gender;
	
	@Column(name="bloodType")
	@NotNull(message = "* Mandatory")
	private String bloodType;
	
	@CheckRegisterEmail
	@Column(name="Email")
	@Pattern(regexp="[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+", message="Invalid Email id; Pattern eg. abc@zmail.com")
	@NotNull(message = "* Mandatory")
	private String email;
	
	@Column(name="Phone")
	@Pattern(regexp="\\d{3}-\\d{3}-\\d{4}", message="Pattern xxx-yyy-zzzz")
	@NotNull(message = "* Mandatory")
	private String phone;
	
	@CheckRegisterUsername
	@Column(name="Username")
	@NotNull(message = "* Mandatory")
	@Size(min=4, max=16, message="Minimum 4 and Maximum 16 characters required")
	private String userName;
	
	@Column(name="Password")
	@NotNull(message = "* Mandatory")
	@Size(min=4, message="Password must of length atleast 4")
	private String password;
	
	@Transient
	private MultipartFile photo;
	
	
	@Column(name="IdProof")
	private String IdProof;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="Role_Fk")
	private SuperRole superRole;
	
	@OneToMany(mappedBy="donor")
	private List<BloodDonationHistory> bloodDonationHistoryList;

	
	public List<BloodDonationHistory> getBloodDonationHistoryList() {
		return bloodDonationHistoryList;
	}

	public void setBloodDonationHistoryList(List<BloodDonationHistory> bloodDonationHistoryList) {
		this.bloodDonationHistoryList = bloodDonationHistoryList;
	}

	public SuperRole getSuperRole() {
		return superRole;
	}

	public void setSuperRole(SuperRole superRole) {
		this.superRole = superRole;
	}

	public Donor() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
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
	
	public MultipartFile getPhoto() {
		return photo;
	}

	public void setPhoto(MultipartFile photo) {
		this.photo = photo;
	}

	public String getIdProof() {
		return IdProof;
	}

	public void setIdProof(String idProof) {
		IdProof = idProof;
	}




	

	@Override
	public String toString() {
		return "id "+id;
	}
	
	
}