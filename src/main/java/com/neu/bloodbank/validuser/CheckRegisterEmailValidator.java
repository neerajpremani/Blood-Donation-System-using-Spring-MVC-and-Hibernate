package com.neu.bloodbank.validuser;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.neu.bloodbank.dao.DonorDao;
import com.neu.bloodbank.dao.RoleDao;
import com.neu.bloodbank.pojo.Donor;
import com.neu.bloodbank.pojo.SuperRole;



public class CheckRegisterEmailValidator implements ConstraintValidator<CheckRegisterEmail, String>{
	
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private HttpSession session;


	@Override
	public boolean isValid(String userEnteredValue, ConstraintValidatorContext context) {
		
		List<SuperRole> roleList= new ArrayList<SuperRole>();
		
		boolean flag= true;
		try {
			roleList = roleDao.getSuperRoles();
			
			for(SuperRole sr: roleList)
			{
				if(sr.getEmail().equalsIgnoreCase(userEnteredValue)) {
					flag = false ;
					break;
				}else {
					flag = true;
				}
			}
			
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
			
		}
		
		
		return flag;
	}

}