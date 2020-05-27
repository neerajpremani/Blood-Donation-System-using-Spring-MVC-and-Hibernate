package com.neu.bloodbank.pdfview;

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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.neu.bloodbank.controller.BBRequestsController;
import com.neu.bloodbank.dao.BBStockDao;
import com.neu.bloodbank.dao.BloodBankDao;
import com.neu.bloodbank.dao.BloodDonationHistoryDao;
import com.neu.bloodbank.dao.DonorDao;
import com.neu.bloodbank.dao.HospitalDao;
import com.neu.bloodbank.dao.RequestBloodDao;
import com.neu.bloodbank.pojo.BloodBank;
import com.neu.bloodbank.pojo.RequestBlood;

@Controller
public class PDFGenerator {

	private static final Logger logger = LoggerFactory.getLogger(PDFGenerator.class);

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

	@RequestMapping(value = "/existingUser/main-bloodbank/downloadPDF", method = RequestMethod.GET)
	public ModelAndView downloadPDFBB(HttpServletRequest request, HttpServletResponse response, ModelMap map,
			Model model) throws Exception {

		HttpSession session = request.getSession();
		if (session.getAttribute("userName") == null) {
			logger.info("Session Username NOT FOUND");
			request.setAttribute("request", request);
			// isko karna hai handle
			return new ModelAndView("sessionFailed");
		}

		logger.info("Login User: " + (String) session.getAttribute("userName"));
		BloodBank bankRecord = bloodDao.getBloodBankByName((String) session.getAttribute("userName"));
		System.out.println("Fetched Record:  " + bankRecord);

		List<Object[]> bbstocks = bloodHistoryDao.getBloodStockById(bankRecord.getId());


		return new ModelAndView("pdfView", "bbstocks", bbstocks);
	}

	@RequestMapping(value = "/existingUser/main-hospital/downloadPDF/{record}", method = RequestMethod.GET)
	public @ResponseBody ModelAndView downloadPDFHospital(@PathVariable String record, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		if (session.getAttribute("userName") == null) {
			logger.info("Session Username NOT FOUND");
			request.setAttribute("request", request);
			// isko karna hai handle
			return new ModelAndView("sessionFailed");
		}

		logger.info("Login User: " + (String) session.getAttribute("userName"));

		BloodBank bankRecord = bloodDao.getBloodBankByName(record);

		List<Object[]> hospstocks = stockDao.getBloodBankCurrentStock(bankRecord.getId());

//
//		request.setAttribute("bbstocks", bbstocks);

		return new ModelAndView("pdfView", "bbstocks", hospstocks);
	}

	@RequestMapping(value = "/existingUser/main-bloodbank/downloadPDF/{record}", method = RequestMethod.GET)
	public @ResponseBody ModelAndView downloadPDFBloodBank(@PathVariable String record, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		if (session.getAttribute("userName") == null) {
			logger.info("Session Username NOT FOUND");
			request.setAttribute("request", request);
			// isko karna hai handle
			return new ModelAndView("BURNCODE");
		}

		logger.info("Login User: " + (String) session.getAttribute("userName"));

		System.out.println("-x-x-x--x-x-x-x-" + record);

		BloodBank bankRecord = bloodDao.getBloodBankByName(record);

		List<Object[]> hospstocks = stockDao.getBloodBankCurrentStock(bankRecord.getId());

		return new ModelAndView("pdfView", "bbstocks", hospstocks);
	}

}
