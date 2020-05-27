/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.bloodbank.controller;

import com.neu.bloodbank.dao.BloodBankDao;
import com.neu.bloodbank.dao.DonorDao;
import com.neu.bloodbank.dao.HospitalDao;
import com.neu.bloodbank.javamail.EmailMessage;
import com.neu.bloodbank.pojo.Hospital;
import com.neu.bloodbank.pojo.SuperRole;
import com.neu.bloodbank.pojo.BloodBank;
import com.neu.bloodbank.pojo.Donor;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author neera
 */
@Controller
public class AuthController {

	public static int count = 0;

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private HospitalDao hospitalDao;

	@Autowired
	private DonorDao donorDao;

	@Autowired
	private BloodBankDao bloodDao;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showHome(HttpServletRequest request) {
		request.getSession().invalidate();
		logger.info("Homepage Initiated!");
		return "homepage";
	}

	
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	
	public static String hashPassword(String plainTextPassword){
		return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
	}
	
	public static boolean checkPass(String plainPassword, String hashedPassword) {
		if (BCrypt.checkpw(plainPassword, hashedPassword))
			return true;
		else
			return false;
	}

//	Hospital Register Operations START
	
	@RequestMapping(value = "/register-hospital", method = RequestMethod.GET)
	public ModelAndView showHospitalRegister(HttpServletRequest request, HttpServletResponse response, ModelMap map,
			Model model) {
		//session invalidate and model attribute
		request.getSession().invalidate();
		model.addAttribute("hospital", new Hospital());
		logger.info("register-hospital Initiated!");
		return new ModelAndView("register-hospital");
	}

	@RequestMapping(value = "/register-hospital", method = RequestMethod.POST)
	public String processHospitalRegister(@Valid @ModelAttribute("hospital") Hospital hospital,
			BindingResult bindingResult, HttpServletRequest request, ModelMap modelmap) throws Exception {

		if (bindingResult.hasErrors()) {

			logger.info("bindingResult.hasErrors() is TRUE");
			return "register-hospital";
		} else {

			logger.info("bindingResult.hasErrors() is FALSE");
			
			//created superrole object to save all along with respective tables
			SuperRole superRole = new SuperRole();
			superRole.setUserName((String) request.getAttribute("newusername"));
			superRole.setPassword(hashPassword((String) request.getAttribute("newpass")));
			superRole.setEmail(hospital.getEmail());
			superRole.setSuperRole("Hospital");
			
			//after interceptor sanitizes values saved new values
			hospital.setName((String) request.getAttribute("newname"));
			hospital.setUserName((String) request.getAttribute("newusername"));
			hospital.setPassword(hashPassword((String) request.getAttribute("newpass")));
			
			//cascade wherein FK is stored
			hospital.setSuperRole(superRole);
			
			//use of validator to authenticate! --reduntant method call
//			if (hospitalDao.authenticateHospitalRegistration(hospital)) {
//				return "register-hospital";
//			}
			
			//save object
			hospitalDao.save(hospital);

			//Email Class generalized message for sending a message!
			EmailMessage.registrationEmail(hospital.getUserName(), hospital.getEmail(), "Hospital");

			logger.info("Hospital Object Saved and Email has been sent!");

			return "redirect:/";
		}
	}
	
//	Hospital Register Operations END

	private String generateFileName(MultipartFile multiPart) {
		return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
	}

	public String uploadFile(MultipartFile multipartFile) throws Exception {
		String fileName = generateFileName(multipartFile);
		String uploadDir = "D:/webtool";
		String filePath = uploadDir + fileName;
		try {
			File file = new File(filePath);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(multipartFile.getBytes());
			fos.close();
		} catch (Exception e) {
			throw e;
		}

		return filePath;

	}

	
//	Donor Register Operations START
	
	
	@RequestMapping(value = "/register-donor", method = RequestMethod.GET)
	public ModelAndView showDonorRegister(HttpServletRequest request, HttpServletResponse response, ModelMap map,
			Model model) {
		//session invalidate and model attribute
		request.getSession().invalidate();
		model.addAttribute("donor", new Donor());
		logger.info("register-donor Initiated");
		return new ModelAndView("register-donor");
	}

	@RequestMapping(value = "/register-donor", method = RequestMethod.POST)
	public String processDonorRegister(@Valid @ModelAttribute("donor") Donor donor, BindingResult bindingResult,
			HttpServletRequest request, ModelMap modelmap) throws Exception, ParseException {
		count++;
		System.out.println("COUNT VALUE : " + count);

		if (bindingResult.hasErrors()) {

			logger.info("bindingResult.hasErrors() is TRUE");
			return "register-donor";
		} else {
			logger.info("bindingResult.hasErrors() is FALSE");

			try {
				String localPath = "D:/webtool/";
				String fileName = generateFileName(donor.getPhoto());
				donor.getPhoto().transferTo(new File(localPath, fileName));
				donor.setIdProof(localPath + fileName);

				//created superrole object to save all along with respective tables
				SuperRole superRole = new SuperRole();
				superRole.setUserName((String) request.getAttribute("newusername"));
				superRole.setPassword(hashPassword((String) request.getAttribute("newpass")));
				superRole.setEmail(donor.getEmail());
				superRole.setSuperRole("Donor");

				//after interceptor sanitizes values saved new values
				donor.setUserName((String) request.getAttribute("newusername"));
				donor.setPassword(hashPassword((String) request.getAttribute("newpass")));
				donor.setFirstName((String) request.getAttribute("newfname"));
				donor.setLastName((String) request.getAttribute("newlname"));
				
				//cascade feature to set FK
				donor.setSuperRole(superRole);
//				if (donorDao.authenticateDonorRegistration(donor)) {
//					return "register-donor";
//				}
				
				//save donor

				donorDao.save(donor);
				
				EmailMessage.registrationEmail(donor.getUserName(), donor.getEmail(), "Donor");

				logger.info("Donor Saved and Email has been sent!");
				return "redirect:/";
			} catch (Exception ex) {
				System.out.println("Error Encountered" + ex);
			}
		}
		return "redirect:/";
	}
	
	
	
//	Donor Register Operations END
	
//	BB Register Operations START

	@RequestMapping(value = "/register-bb", method = RequestMethod.GET)
	public ModelAndView showBBRegister(HttpServletRequest request, HttpServletResponse response, ModelMap map,
			Model model) {
		//session invalidate and model attribute
		request.getSession().invalidate();
		model.addAttribute("bb", new BloodBank());
		logger.info("register-bb Initiated");
		return new ModelAndView("register-bb");
	}

	@RequestMapping(value = "/register-bb", method = RequestMethod.POST)
	public String processBBRegister(@Valid @ModelAttribute("bb") BloodBank bloodbank, BindingResult bindingResult,
			HttpServletRequest request, ModelMap modelmap) throws Exception {

		if (bindingResult.hasErrors()) {

			logger.info("bindingResult.hasErrors() is TRUE");
			return "register-bb";
		} else {

			logger.info("bindingResult.hasErrors() is FALSE");
		
			//created superrole object to save all along with respective tables
			SuperRole superRole = new SuperRole();
			superRole.setUserName((String) request.getAttribute("newusername"));
			superRole.setPassword(hashPassword((String) request.getAttribute("newpass")));
			superRole.setEmail(bloodbank.getEmail());
			superRole.setSuperRole("BloodBank");
			
			//after interceptor sanitizes values saved new values
			bloodbank.setUserName((String) request.getAttribute("newusername"));
			bloodbank.setPassword(hashPassword((String) request.getAttribute("newpass")));
			bloodbank.setName((String) request.getAttribute("newname"));

			
			//cascade feature to save FK
			bloodbank.setSuperRole(superRole);
//			if (bloodDao.authenticateBBRegistration(bloodbank)) {
//				return "register-bb";
//			}

			bloodDao.save(bloodbank);
			EmailMessage.registrationEmail(bloodbank.getUserName(), bloodbank.getEmail(), "BloodBank");
			logger.info("BloodBank Saved and Email has been sent!");
			return "redirect:/";
		}
	}
	
	
//	BB Register Operations END

}
