package com.neu.bloodbank.validuser;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.neu.bloodbank.dao.DonorDao;
import com.neu.bloodbank.dao.RoleDao;
import com.neu.bloodbank.pojo.Donor;
import com.neu.bloodbank.pojo.SuperRole;

public class RequestBloodUserValidator implements ConstraintValidator<CheckRegisterEmail, String>{
	
	
	@Autowired
	private DonorDao donorDao;

	@Override
	public boolean isValid(String userEnteredValue, ConstraintValidatorContext context) {
		
		List<Donor> donorList= new ArrayList<Donor>();
		
		boolean flag= false;
		try {
			donorList = donorDao.getDonorsList();
		
			for(Donor don: donorList)
			{
				if(don.getEmail().equalsIgnoreCase(userEnteredValue)) {
					flag = true ;
					break;
				}else {
					flag = false;
				}
			}
			
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
			
		}
		
		
		return flag;
	}

}