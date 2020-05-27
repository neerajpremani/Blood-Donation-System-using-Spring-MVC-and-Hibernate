package com.neu.bloodbank.controller;

import java.io.File;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.neu.bloodbank.dao.BloodBankDao;
import com.neu.bloodbank.dao.BloodDonationHistoryDao;
import com.neu.bloodbank.dao.DonorDao;
import com.neu.bloodbank.dao.HospitalDao;
import com.neu.bloodbank.dao.RequestBloodDao;
import com.neu.bloodbank.dao.RoleDao;
import com.neu.bloodbank.javamail.EmailMessage;
import com.neu.bloodbank.pojo.Donor;
import com.neu.bloodbank.pojo.LoginUser;
import com.neu.bloodbank.pojo.SuperRole;

@Controller
public class UpdateController {
	
	
	
	private static final Logger logger = LoggerFactory.getLogger(UpdateController.class);
	
	@Autowired
	private HospitalDao hospitalDao;

	@Autowired
	private DonorDao donorDao;

	@Autowired
	private BloodBankDao bloodDao;
	
	@Autowired
	private RoleDao roleDao;

	@Autowired
	private RequestBloodDao requestBloodDao;

	@Autowired
	private BloodDonationHistoryDao bloodHistoryDao;
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	
	@RequestMapping(value = "/existingUser/main-donor/updatedonor", method = RequestMethod.GET)
	public ModelAndView showUserLoginPage(ModelMap model, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		
		if (session.getAttribute("userName") == null) {
			logger.info("Session Username NOT FOUND");
			request.setAttribute("request", request);
			// isko karna hai handle
			return new ModelAndView("sessionFailed");
		}

		logger.info("Session Attribute ID " + session.getId() + " \n " + (String) session.getAttribute("userName"));
		
		logger.info("Inside Update Donor");
		
		Donor updateDonor = donorDao.getDonorDetailByName((String) session.getAttribute("userName"));
		
//		request.setAttribute("donorBT", updateDonor.getBloodType());
//		request.setAttribute("donorDB", updateDonor.getDateOfBirth().toString());
		request.setAttribute("donorEmail", updateDonor.getEmail());
//		request.setAttribute("donorFirst", updateDonor.getFirstName());
//		request.setAttribute("donorLast", updateDonor.getLastName());
//		request.setAttribute("donorGender", updateDonor.getGender());
//		request.setAttribute("donorPass", updateDonor.getPassword());
		request.setAttribute("donorUser", updateDonor.getUserName());
//		request.setAttribute("donorphone", updateDonor.getPhone());
		
		model.addAttribute("updateDonor", updateDonor);
		return new ModelAndView("update-donor");
	}
	
	
	
	@RequestMapping(value = "/existingUser/main-donor/updatedonor", method = RequestMethod.POST)
	public String processDonorRegister(@Valid @ModelAttribute("updateDonor") Donor donor, BindingResult bindingResult,
			HttpServletRequest request, ModelMap modelmap) throws Exception, ParseException {
	
		if (bindingResult.hasErrors()) {

			logger.info("bindingResult.hasErrors() is TRUE");
			return "update-donor";
		} else {
			logger.info("bindingResult.hasErrors() is FALSE");

			try {
				

				//created superrole object to save all along with respective tables
				SuperRole userRole=roleDao.checkUsernameRole((String) request.getAttribute("newusername"), "donor");
				if(userRole!=null)
				{
				userRole.setPassword(AuthController.hashPassword((String) request.getAttribute("newpass")));
				//after interceptor sanitizes values saved new values
				donor.setUserName((String) request.getAttribute("newusername"));
				donor.setPassword(AuthController.hashPassword((String) request.getAttribute("newpass")));
				donor.setFirstName((String) request.getAttribute("newfname"));
				donor.setLastName((String) request.getAttribute("newlname"));
				
				}
				logger.info("Donor Saved and Email has been sent!");
				return "redirect:/";
			} catch (Exception ex) {
				System.out.println("Error Encountered" + ex);
			}
		}
		return "redirect:/";
	}


}
