package com.neu.bloodbank.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.neu.bloodbank.validuser.LoginUsername;


public class LoginUser {
	

	@Column(name="Username")
	@NotNull(message="* Mandatory")
	@Size(min=4, max=16, message="Username length min=4 max=16")
	
	private String userName;
	
	@Column(name="Password")
	@NotNull(message="* Mandatory")
	@Size(min=4, message="Should be of min length 4")
	private String password;
	


	public LoginUser() {
		
	}

	public LoginUser(String userName, String password, String email) {
		this.userName = userName;
		this.password = password;

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

	@Override
	public String toString() {
		return "LoginUser [userName=" + userName + ", password=" + password + "]";
	}

}
