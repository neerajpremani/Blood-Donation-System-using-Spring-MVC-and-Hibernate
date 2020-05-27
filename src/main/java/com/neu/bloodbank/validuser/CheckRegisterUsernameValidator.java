package com.neu.bloodbank.validuser;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.neu.bloodbank.dao.RoleDao;
import com.neu.bloodbank.pojo.SuperRole;

public class CheckRegisterUsernameValidator implements ConstraintValidator<CheckRegisterUsername, String> {

	@Autowired
	private RoleDao roleDao;



	@Override
	public boolean isValid(String userEnteredValue, ConstraintValidatorContext context) {
		
		
		
		List<SuperRole> roleList= new ArrayList<SuperRole>();
		
		boolean flag= true;
		try {
			roleList = roleDao.getSuperRoles();
			
			for(SuperRole sr: roleList)
			{
				if(sr.getUserName().equalsIgnoreCase(userEnteredValue)) {
					
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