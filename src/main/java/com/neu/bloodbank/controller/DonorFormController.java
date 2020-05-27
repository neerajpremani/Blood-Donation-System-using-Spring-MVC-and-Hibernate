package com.neu.bloodbank.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.MediaType;
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
import org.springframework.web.servlet.ModelAndView;

import com.neu.bloodbank.dao.BBStockDao;
import com.neu.bloodbank.dao.BloodBankDao;
import com.neu.bloodbank.dao.BloodDonationHistoryDao;
import com.neu.bloodbank.dao.DonorDao;
import com.neu.bloodbank.dao.HospitalDao;
import com.neu.bloodbank.dao.RequestBloodDao;
import com.neu.bloodbank.pojo.BBStock;
import com.neu.bloodbank.pojo.BloodBank;
import com.neu.bloodbank.pojo.BloodDonationHistory;
import com.neu.bloodbank.pojo.Donor;
import com.neu.bloodbank.pojo.Hospital;
import com.neu.bloodbank.pojo.LoginUser;
import com.neu.bloodbank.pojo.RequestBlood;

@Controller
public class DonorFormController {

	private static final Logger logger = LoggerFactory.getLogger(DonorFormController.class);

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

	@RequestMapping(value = "/existingUser/main-bloodbank/blooddonateform", method = RequestMethod.GET)
	public ModelAndView showDonorForm(HttpServletRequest request, HttpServletResponse response, Model mode,
			ModelMap model) throws Exception {

		HttpSession session = request.getSession();
		if (session.getAttribute("userName") == null) {
			logger.info("Session Username NOT FOUND");
			request.setAttribute("request", request);
			// isko karna hai handle
			return new ModelAndView("sessionFailed");
		}

		BloodDonationHistory bhistory = new BloodDonationHistory();
		Date date = new Date();
		bhistory.setDonatedDate(date);
		// request.setAttribute("donatedate", date);
		logger.info("Logged in user:" + (String) session.getAttribute("userName"));
		model.addAttribute("donationObj", bhistory);
		logger.info("Donation History Attribute Modeled and passed from GET Method");
		return new ModelAndView("blood-donate-form");

	}

	@RequestMapping(value = "/existingUser/main-bloodbank/blooddonateform", method = RequestMethod.POST)
	public String processDonorForm(@Valid @ModelAttribute("donationObj") BloodDonationHistory bloodhistory,
			HttpServletRequest request, HttpServletResponse response, BindingResult bindingResult, ModelMap modelmap)
			throws Exception {
		int res = 0;
		HttpSession session = request.getSession();
		if (session.getAttribute("userName") == null) {
			logger.info("Session Username NOT FOUND");
			request.setAttribute("request", request);
			// isko karna hai handle
			return "sessionFailed";
		}
		if (bindingResult.hasErrors()) {
			logger.info("bindingResult.hasErrors() is TRUE");
			return "blood-donate-form";
		} else {
			logger.info("bindingResult.hasErrors() is FALSE");
			bloodhistory = new BloodDonationHistory();
			String donorEmail = request.getParameter("donoremail");
			String donorUsername = request.getParameter("donarusername");
			String donorName = request.getParameter("donorname");
			String bloodAmount = request.getParameter("bloodAmount");
			String donateDate = request.getParameter("donatedDate");
			SimpleDateFormat sdf3 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

			// Parsing the given String to Date object
			Date myDate = sdf3.parse(donateDate);

			Donor donor = donorDao.verifyDonorDetail(donorName, donorEmail);

			BloodBank bloodBank = bloodDao.getBloodBankByName((String) session.getAttribute("userName"));
			System.out.println("BloodBank Record: " + bloodBank);
			System.out.println("Blood Amount--x-x-x-x- " + request.getParameter("bloodAmount"));

			bloodhistory.setBloodType(donor.getBloodType());

			// bloodhistory.setBloodAmount(100);

			System.out.println("-x-x-x-x-x--x-x-x-   Date Setted" + bloodhistory.getDonatedDate());
			System.out.println("-x-x-x-x-x--x-x-x-   Blood Amount" + bloodhistory.getBloodAmount());
//				System.out.println("-x-x-x-x-x--x-x-x-   Blood Bank ID" +  );
//
//				System.out.println("-x-x-x-x-x--x-x-x-   Donor ID" + bloodhistory.getBloodAmount());
			bloodhistory.setBloodBank(bloodBank);

			bloodhistory.setDonor(donor);

			bloodhistory.setBloodAmount(Integer.parseInt(bloodAmount));
			bloodhistory.setDonatedDate(myDate);

			BBStock bbstocknew = stockDao.getBBStockRecord(bloodBank.getId(), bloodhistory.getBloodType());

			if (bbstocknew != null) {
				int bAmount = bbstocknew.getBloodAmount() + bloodhistory.getBloodAmount();

				res = stockDao.updateStockById(bloodBank.getId(), donor.getBloodType(), bAmount);

			}
			System.out.println("RESS xxxxx" + res);
			if (res <= 0) {
				BBStock bbnew = new BBStock();
				bbnew.setBloodAmount(bloodhistory.getBloodAmount());
				bbnew.setBloodType(donor.getBloodType());
				bbnew.setBloodBank(bloodBank);
				stockDao.save(bbnew);
			}

			// stockDao.save(bbnew);
			bloodHistoryDao.save(bloodhistory);
			return "main-bloodbank";

		}
	}

	@ResponseBody
	@RequestMapping(value = "/existingUser/main-bloodbank/checkDonor", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<String, String> dynamicSearchAdmin(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HashMap<String, String> map = new HashMap<String, String>();
		HttpSession session = request.getSession();

		String duser = request.getParameter("duser");
		String demail = request.getParameter("demail");

		logger.info("donor in controller -x-x-x-x-- " + duser + "   " + demail);

		Donor donor = donorDao.verifyDonorDetail(duser, demail);
		if (donor != null) {

			logger.info("SESSION ID -x-x-x--x-x" + session.getId() + "  " + session.getAttribute("userName"));
			map.put("key", "1");

		} else {

			map.put("key", "2");
			logger.info("INSIDE DYNAMIC DONOR NOT Found ");
//			SuperRole userLog=roleDao.getLoggedInUserDetail(username, password);
		}

		logger.info("INSIDE DYNAMIC " + duser + "  " + demail);
		return map;

	}
}
