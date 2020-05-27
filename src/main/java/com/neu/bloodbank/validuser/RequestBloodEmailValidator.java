package com.neu.bloodbank.validuser;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.neu.bloodbank.dao.DonorDao;
import com.neu.bloodbank.pojo.Donor;

public class RequestBloodEmailValidator implements ConstraintValidator<CheckRegisterEmail, String>{
	
	
	@Autowired
	private DonorDao donorDao;

	@Override
	public boolean isValid(String str, ConstraintValidatorContext context) {
		
		List<Donor> donorList= new ArrayList<Donor>();
		
		boolean flag= false;
		try {
			donorList = donorDao.getDonorsList();
			
			for(Donor don: donorList)
			{
				if(don.getUserName().equals(str)) {
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