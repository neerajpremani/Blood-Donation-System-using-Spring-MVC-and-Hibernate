package com.neu.bloodbank.excelview;

import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.neu.bloodbank.controller.DonorFormController;
import com.neu.bloodbank.dao.BBStockDao;
import com.neu.bloodbank.dao.BloodBankDao;
import com.neu.bloodbank.dao.BloodDonationHistoryDao;
import com.neu.bloodbank.dao.DonorDao;
import com.neu.bloodbank.dao.HospitalDao;
import com.neu.bloodbank.dao.RequestBloodDao;
import com.neu.bloodbank.pojo.BloodBank;
import com.neu.bloodbank.pojo.RequestBlood;

@Controller
public class ExcelController {

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

	@RequestMapping(value = "/existingUser/main-bloodbank/downloadExcel", method = RequestMethod.GET)
	public ModelAndView downloadExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// create some sample data

		HttpSession session = request.getSession();
		if (session.getAttribute("userName") == null) {
			logger.info("Session Username NOT FOUND");
			request.setAttribute("request", request);
			// isko karna hai handle
			return new ModelAndView("sessionFailed");
		}
		List<RequestBlood> bloodRequests = new ArrayList<RequestBlood>();
		BloodBank bankRecord = bloodDao.getBloodBankByName((String) session.getAttribute("userName"));

		System.out.println("Fetched Record:  " + bankRecord);
		System.out.println("Fetched Record:  " + bankRecord.getId());

		bloodRequests = requestBloodDao.getBankBloodRequests(bankRecord.getId());

		System.out.println("BR-------x-x--xS  " + bloodRequests);
		request.setAttribute("bloodRequestsList", bloodRequests);

		// return a view which will be resolved by an excel view resolver
		return new ModelAndView("excelView", "bloodRequests", bloodRequests);
	}

}
