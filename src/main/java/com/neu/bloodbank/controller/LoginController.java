/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.bloodbank.controller;

import com.neu.bloodbank.dao.BloodBankDao;
import com.neu.bloodbank.dao.DonorDao;
import com.neu.bloodbank.dao.HospitalDao;
import com.neu.bloodbank.dao.RoleDao;
import com.neu.bloodbank.pojo.BloodBank;
import com.neu.bloodbank.pojo.Donor;
import com.neu.bloodbank.pojo.Hospital;
import com.neu.bloodbank.pojo.LoginUser;
import com.neu.bloodbank.pojo.SuperRole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author neera
 */
@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private HospitalDao hospitalDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private DonorDao donorDao;

	@Autowired
	private BloodBankDao bloodDao;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	private String hashPassword(String plainTextPassword) {
		return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
	}

	private boolean checkPass(String plainPassword, String hashedPassword) {
		if (BCrypt.checkpw(plainPassword, hashedPassword))
			return true;
		else
			return false;
	}

	@RequestMapping(value = "/existingUser", method = RequestMethod.GET)
	public String showUserLoginPage(ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();

		logger.info("Session Attribute ID " + session.getId() + " \n " + (String) session.getAttribute("userName"));
		if (session != null) {
			session.invalidate();
		}
		logger.info("Inside Login Page GET!!");
		model.addAttribute("loginUser", new LoginUser());
		return "user-login";
	}

	@ResponseBody
	@RequestMapping(value = "/checkCredentials", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<String, String> checkCredentialsUser(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("CHECK CREDENTIALS! Jquery POST Call");
		HttpSession session = request.getSession();

		String uname = request.getParameter("uname");
		String urole = request.getParameter("urole");
		String upass = request.getParameter("upass");
		boolean flag = false;
		SuperRole userRole = roleDao.checkUsernameRole(uname, urole);
		if (userRole != null) {
			System.out.println("HASHED PASSWORD  " + userRole.getPassword());
			flag = checkPass(upass, userRole.getPassword());

		} else {
			flag = false;
		}

		// boolean flag = roleDao.checkUserRoleExists(uname, upass, urole);

		HashMap<String, String> map = new HashMap<String, String>();

		if (flag == true) {
			session.setAttribute("userName", uname);
			session.setAttribute("authrole", urole);
//			 session.setAttribute("password", upass);
			logger.info("Username and Password MATCH");
			map.put("key", "1");

			if (urole.equalsIgnoreCase("hospital")) {
				logger.info("USER OF HOSPITAL LOGGED IN");
				map.put("page", "/main-hospital");
			} else if (urole.equalsIgnoreCase("donor")) {
				logger.info("USER OF Donor LOGGED IN");
				map.put("page", "/main-donor");
			} else if (urole.equalsIgnoreCase("bloodbank")) {
				logger.info("USER OF BLOODBANK LOGGED IN");
				map.put("page", "/main-bloodbank");
			}
			logger.info("INSIDE DYNAMIC User Found ");
		} else {
			if (session != null) {
				session.invalidate();
			}
			map.put("key", "2");
			logger.info("USERNAME AND PASSWORD DO NOT MATCH ");

		}

		return map;

	}

	@RequestMapping(value = "/existingUser/main-hospital", method = RequestMethod.GET)
	public String showMainHospital() {

		logger.info("URL Match: main-hospital");
		return "main-hospital";
	}

	@RequestMapping(value = "/existingUser/main-donor", method = RequestMethod.GET)
	public String showMainDonor() {
		logger.info("URL Match: main-donor");
		return "main-donor";
	}

	@RequestMapping(value = "/existingUser/main-bloodbank", method = RequestMethod.GET)
	public String showMainBloodBank() {
		logger.info("URL Match: main-bloodbank");
		return "main-bloodbank";
	}
}
