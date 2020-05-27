package com.neu.bloodbank.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.neu.bloodbank.dao.BloodBankDao;
import com.neu.bloodbank.dao.BloodDonationHistoryDao;
import com.neu.bloodbank.dao.DonorDao;
import com.neu.bloodbank.dao.HospitalDao;
import com.neu.bloodbank.dao.RequestBloodDao;
import com.neu.bloodbank.pojo.BloodDonationHistory;
import com.neu.bloodbank.pojo.Donor;

@Controller
public class DonorController {

	private static final Logger logger = LoggerFactory.getLogger(DonorController.class);

	@Autowired
	private HospitalDao hospitalDao;

	@Autowired
	private DonorDao donorDao;

	@Autowired
	private BloodBankDao bloodDao;

	@Autowired
	private RequestBloodDao requestBloodDao;

	@Autowired
	private BloodDonationHistoryDao bloodHistoryDao;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@RequestMapping(value = "/existingUser/main-donor/mydonatehistory", method = RequestMethod.GET)
	public String getMyDonorHistory(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		if (session.getAttribute("userName") == null) {
			logger.info("Session Username NOT FOUND");
			request.setAttribute("request", request);
			
			return "sessionFailed";
		}

		Donor donor = donorDao.getDonorDetailByName((String) session.getAttribute("userName"));
		System.out.println(donor);

		List<BloodDonationHistory> bbDonorHistory = bloodHistoryDao.getDonorHistoryById(donor.getId());
		
		System.out.println("Donor History : "+ bbDonorHistory);

		request.setAttribute("bbDonorHistory", bbDonorHistory);
		logger.info("getMyDonorHistory Update!");
		return "donor-history";

	}

}
