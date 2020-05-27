package com.neu.bloodbank.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.neu.bloodbank.dao.BBStockDao;
import com.neu.bloodbank.dao.BloodBankDao;
import com.neu.bloodbank.dao.BloodDonationHistoryDao;
import com.neu.bloodbank.dao.DonorDao;
import com.neu.bloodbank.dao.HospitalDao;
import com.neu.bloodbank.dao.RequestBloodDao;
import com.neu.bloodbank.pojo.BloodBank;
import com.neu.bloodbank.pojo.BloodDonationHistory;
import com.neu.bloodbank.pojo.RequestBlood;

@Controller
public class BBController {

	private static final Logger logger = LoggerFactory.getLogger(BBController.class);

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
	
	@Autowired
	private BBStockDao stockDao;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	// DONATION HISTORY START
	@RequestMapping(value = "/existingUser/main-bloodbank/donationhistory", method = RequestMethod.GET)
	public String showBloodBankHistoryPage(HttpServletRequest request, HttpServletResponse response, Model mode,
			ModelMap model) throws Exception {

		HttpSession session = request.getSession();
		if (session.getAttribute("userName") == null) {
			logger.info("Session Username NOT FOUND");
			request.setAttribute("request", request);
			// isko karna hai handle
			return "sessionFailed";
		}

		logger.info("Login User: " + (String) session.getAttribute("userName"));
		BloodBank bankRecord = bloodDao.getBloodBankByName((String) session.getAttribute("userName"));

		System.out.println("Fetched Record:  " + bankRecord.getId());

		List<BloodDonationHistory> bloodHistoryList = bloodHistoryDao.getDonationHistoryById(bankRecord.getId());

		request.setAttribute("bloodHistoryList", bloodHistoryList);

		// get donor of the blood bank
		logger.info("Blood Donation History");
		return "bloodbank-donation-history";

	}
	
//	DONATION HISTORY END
	
//BLOOD DONATION DONOR HISTORY START

	@RequestMapping(value = "/existingUser/main-bloodbank/donordetails", method = RequestMethod.GET)
	public String showBloodBankDonorDetails(HttpServletRequest request, HttpServletResponse response, Model mode,
			ModelMap model) throws Exception {

		HttpSession session = request.getSession();
		if (session.getAttribute("userName") == null) {
			logger.info("Session Username NOT FOUND");
			request.setAttribute("request", request);
			
			return "sessionFailed";
		}

		logger.info("Login User: " + (String) session.getAttribute("userName"));
		BloodBank bankRecord = bloodDao.getBloodBankByName((String) session.getAttribute("userName"));

		System.out.println("Fetched Record:  " + bankRecord);

		List<BloodDonationHistory> bloodHistoryList = bloodHistoryDao.getDonationHistoryById(bankRecord.getId());

		request.setAttribute("bloodHistoryList", bloodHistoryList);
		logger.info("Blood Donation Donor Details");
		return "bloodbank-donor-details";

	}
	
	//BLOOD DONATION DONOR HISTORY
	
	//BLOOD DONATED TO BB

	@RequestMapping(value = "/existingUser/main-bloodbank/bbstock", method = RequestMethod.GET)
	public String showBloodBankStocks(HttpServletRequest request, HttpServletResponse response, ModelMap modelmap,
			Model model) throws Exception {

		HttpSession session = request.getSession();

		if (session.getAttribute("userName") == null) {
			logger.info("Session Username NOT FOUND");
			request.setAttribute("request", request);
			// isko karna hai handle
			return "sessionFailed";
		}

		logger.info("Login User: " + (String) session.getAttribute("userName"));
		BloodBank bankRecord = bloodDao.getBloodBankByName((String) session.getAttribute("userName"));
		System.out.println("Fetched Record:  " + bankRecord);

		List<Object[]> bbstocks = bloodHistoryDao.getBloodStockById(bankRecord.getId());

		System.out.println("*****\n\nType||Amount\n");
		System.out.println("vghjkhgfhjk  "+ bbstocks);
//		Map<String, Integer> singleStock = new HashMap<String, Integer>();
//		for (Map<String, Integer> map : bbstocks) {
//			for (String str : map.keySet()) {
//				
//				System.out.println(str);
//				System.out.println(map.get(str));
//				System.out.println("-----");
//				
//
//			}
//		}
//		System.out.println("-x-x-x--x-x-x  "+bbstocks.toString());

		request.setAttribute("bbstocks", bbstocks);
		logger.info("Blood Donation Stock History");
		return "bloodbank-totalstock";
	}
	
//	BLOOD DONATED TO BB END
	
	
//	BLOOD CURRENT STOCK IN BB START
	
	
	
	
	@RequestMapping(value = "/existingUser/main-bloodbank/bbcurrentstock", method = RequestMethod.GET)
	public String showBloodBankCurrentStocks(HttpServletRequest request, HttpServletResponse response, ModelMap modelmap,
			Model model) throws Exception {

		HttpSession session = request.getSession();

		if (session.getAttribute("userName") == null) {
			logger.info("Session Username NOT FOUND");
			request.setAttribute("request", request);
			// isko karna hai handle
			return "sessionFailed";
		}

		logger.info("Login User: " + (String) session.getAttribute("userName"));
		BloodBank bankRecord = bloodDao.getBloodBankByName((String) session.getAttribute("userName"));
		System.out.println("Fetched Record:  " + bankRecord);

		List<Object[]> bbstocks =stockDao.getBloodBankCurrentStock(bankRecord.getId());

		System.out.println("n\nType||Amount\n");
		System.out.println("vghjkhgfhjk  "+ bbstocks);
//		Map<String, Integer> singleStock = new HashMap<String, Integer>();
//		for (Map<String, Integer> map : bbstocks) {
//			for (String str : map.keySet()) {
//				
//				System.out.println(str);
//				System.out.println(map.get(str));
//				System.out.println("-----");
//				
//
//			}
//		}
//		System.out.println("-x-x-x--x-x-x  "+bbstocks.toString());
		
		request.setAttribute("bbstocks", bbstocks);
		logger.info("Blood Current Stock");
		return "bloodbank-refresh-stock";
	}
	
	
//	BLOOD CURRENT STOCK IN BB END
	
	
	
	

}
