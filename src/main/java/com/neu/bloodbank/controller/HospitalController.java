package com.neu.bloodbank.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.neu.bloodbank.pojo.Hospital;
import com.neu.bloodbank.pojo.LoginUser;
import com.neu.bloodbank.pojo.SuperRole;
import com.neu.bloodbank.dao.BBStockDao;
import com.neu.bloodbank.dao.BloodBankDao;
import com.neu.bloodbank.dao.DonorDao;
import com.neu.bloodbank.dao.HospitalDao;
import com.neu.bloodbank.dao.RequestBloodDao;
import com.neu.bloodbank.javamail.EmailMessage;
import com.neu.bloodbank.pojo.BloodBank;
import com.neu.bloodbank.pojo.RequestBlood;

@Controller
public class HospitalController {

	private static final Logger logger = LoggerFactory.getLogger(HospitalController.class);

	@Autowired
	private HospitalDao hospitalDao;

	@Autowired
	private DonorDao donorDao;

	@Autowired
	private BloodBankDao bloodDao;

	@Autowired
	private RequestBloodDao requestBloodDao;

	@Autowired
	private BBStockDao stockDao;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	// BLOODREQUEST START

	@RequestMapping(value = "/existingUser/main-hospital/bloodrequest", method = RequestMethod.GET)
	public ModelAndView showBloodReqPage(HttpServletRequest request, HttpServletResponse response, Model model,
			ModelMap modelmap) throws Exception {
		logger.info("Main-Hospital Blood Request GET Invoked HospitalController");
		HttpSession session = request.getSession();
		if (session.getAttribute("userName") == null) {
			logger.info("Session Username NOT FOUND SESSION FAILED");
			request.setAttribute("request", request);

			return new ModelAndView("sessionFailed");
		}

		List<BloodBank> bbListName = new ArrayList<BloodBank>();
		bbListName = bloodDao.getAllBloodBankList();
		RequestBlood rblood = new RequestBlood();
		Date date = new Date();

		rblood.setRequestDate(date);

		request.setAttribute("bbListName", bbListName);

		modelmap.addAttribute("requestBloodForm", rblood);
		logger.info("HospitalController: Blood-Request-form jsp");
		return new ModelAndView("blood-request-form");

	}

	@RequestMapping(value = "/existingUser/main-hospital/bloodrequest", method = RequestMethod.POST)
	public String processBloodReqPage(@Valid @ModelAttribute("requestBloodForm") RequestBlood requestBlood,
			HttpServletRequest request, HttpServletResponse response, BindingResult bindingResult, ModelMap modelmap)
			throws Exception {
		logger.info("HospitalController: Blood-Request-form POST");
		HttpSession session = request.getSession();
		if (session.getAttribute("userName") == null) {
			logger.info("Session Username NOT FOUND");
			request.setAttribute("request", request);

			return "sessionFailed";
		}
		if (bindingResult.hasErrors()) {
			logger.info("bindingResult.hasErrors() is TRUE");
			return "blood-request-form";
		} else {
			logger.info("HospitalController:bindingResult.hasErrors() is FALSE");
			
			Hospital uniqueHospRecord = hospitalDao.getHospitalRecordByName((String) session.getAttribute("userName"));
			System.out.println("Hospital Record: " + uniqueHospRecord);
			System.out.println("BloodBank Name from Model Attribute: " + request.getParameter("bbName"));
			BloodBank bb = bloodDao.getBloodBankByName(request.getParameter("bbName"));

//			Date date = new Date();
//			requestBlood.setDate(date);

			requestBlood.setConfirmation("NotApproved");

			requestBlood.setBloodBank(bb);

			requestBlood.setHospital(uniqueHospRecord);

			requestBloodDao.save(requestBlood);

			EmailMessage.bloodRequest(uniqueHospRecord.getEmail(), bb.getEmail());
			logger.info("Blood Request Saved and Email has been sent!");
			return "redirect:/existingUser/main-hospital";
		}
	}

	// BLOODREQUEST

	// HOSPITAL BB CURRENT STOCK START

	@RequestMapping(value = "/existingUser/main-hospital/currentstock", method = RequestMethod.GET)
	public String showStockfromBB(HttpServletRequest request, HttpServletResponse response, Model model, ModelMap map)
			throws Exception {
		logger.info("HospitalController: CURRENT STOCK GET");
		HttpSession session = request.getSession();
		if (session.getAttribute("userName") == null) {
			logger.info("Session Username NOT FOUND");
			request.setAttribute("request", request);

			return "sessionFailed";
		}
		List<BloodBank> bbListName = new ArrayList<BloodBank>();
		bbListName = bloodDao.getAllBloodBankList();
		System.out.println("\n Blood Bank LIST NAMES" + bbListName);

		request.setAttribute("bbListName", bbListName);

		logger.info("BloodRequest Form Invoked from HospitalController");
		return "bloodbank-current-stock";

	}

	@RequestMapping(value = "/existingUser/main-hospital/bbcurrentstock", method = RequestMethod.GET)
	public String processStockforBB(HttpServletRequest request, HttpServletResponse response, Model model, ModelMap map)
			throws Exception {
		logger.info("HospitalController: CURRENT STOCK POST");
		HttpSession session = request.getSession();
		if (session.getAttribute("userName") == null) {
			logger.info("Session Username NOT FOUND");
			request.setAttribute("request", request);
			// isko karna hai handle
			return "sessionFailed";
		}

		BloodBank bb = bloodDao.getBloodBankByName(request.getParameter("bbName"));

		List<Object[]> bbsingle = stockDao.getBloodBankCurrentStock(bb.getId());

		System.out.println("IN CONTROLLER " + bbsingle);
		request.setAttribute("mybb", bb.getName());
		request.setAttribute("bbsingle", bbsingle);
		logger.info("REACHED HC Single BB");

		return "show-current-stock";

	}

	// HOSPITAL BB CURRENT STOCK END

	// HOSPITAL HISTORY REQUESTS START

	@RequestMapping(value = "/existingUser/main-hospital/bloodhistory", method = RequestMethod.GET)
	public String showHospitalHistory(HttpServletRequest request, HttpServletResponse response, Model model,
			ModelMap map) throws Exception {
		logger.info("HospitalController: BloodHistoery GET");
		List<RequestBlood> bloodhistory = new ArrayList<RequestBlood>();
		HttpSession session = request.getSession();
		if (session.getAttribute("userName") == null) {
			logger.info("Session Username NOT FOUND");
			request.setAttribute("request", request);
			
			return "sessionFailed";
		}

	
		Hospital uniqueHospRecord = hospitalDao.getHospitalRecordByName((String) session.getAttribute("userName"));

		bloodhistory = requestBloodDao.getHospitalRequestHistory(uniqueHospRecord.getId());

		if (!bloodhistory.isEmpty()) {
			logger.info("Blood History FOUND");
			logger.info("Blood History for Hospital found" + bloodhistory);
			request.setAttribute("hospitalhistory", bloodhistory);
		} else {
			logger.info("NO Blood History FOUND");
			request.setAttribute("nohospitalhistory", "No History Found!");
		}

		return "blood-history-hospital";

	}

	@RequestMapping(value = "/existingUser/main-hospital/bloodhistorydelete", method = RequestMethod.GET)
	public String processDeleteRequest(HttpServletRequest request, HttpServletResponse response, Model model,
			ModelMap modelmap) throws Exception {
		logger.info("HospitalController: BloodHistory Delete GET");
		int res = 0;
		List<RequestBlood> bloodhistory = new ArrayList<RequestBlood>();
		HttpSession session = request.getSession();
		if (session.getAttribute("userName") == null) {
			logger.info("Session Username NOT FOUND");
			request.setAttribute("request", request);
			// isko karna hai handle
			return "sessionFailed";
		}

		Hospital uniqueHospRecord = hospitalDao.getHospitalRecordByName((String) session.getAttribute("userName"));
//		System.out.println("Parameter Delete : " + request.getParameter("delete"));

		if (request.getParameter("delete") != null) {
			RequestBlood br = requestBloodDao.getRequestById(Long.parseLong(request.getParameter("reqId")));
			boolean flag = br.getConfirmation().equalsIgnoreCase("NotApproved");

			if (flag == true) {
				System.out.println("\n*****Inside Update logic of controller");
				System.out.println("Delete Value: " + request.getParameter("delete"));
				res = requestBloodDao.deleteRequestById(Long.parseLong(request.getParameter("reqId")));
			}

		}

		//System.out.println("Value of RES : " + res);
		logger.info("BloodRequest Deleted!");
		return "redirect:/existingUser/main-hospital/bloodhistory";

	}

	// HOSPITAL HISTORY REQUESTS END

}
