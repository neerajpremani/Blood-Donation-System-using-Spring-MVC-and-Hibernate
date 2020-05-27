package com.neu.bloodbank.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.neu.bloodbank.dao.BBStockDao;
import com.neu.bloodbank.dao.BloodBankDao;
import com.neu.bloodbank.dao.BloodDonationHistoryDao;
import com.neu.bloodbank.dao.DonorDao;
import com.neu.bloodbank.dao.HospitalDao;
import com.neu.bloodbank.dao.RequestBloodDao;
import com.neu.bloodbank.javamail.EmailMessage;
import com.neu.bloodbank.pojo.BBStock;
import com.neu.bloodbank.pojo.BloodBank;
import com.neu.bloodbank.pojo.RequestBlood;

@Controller
public class BBRequestsController {

	private static final Logger logger = LoggerFactory.getLogger(BBRequestsController.class);

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

	
//	BB BLOOD REQUESTS START
	
	
	@RequestMapping(value = "/existingUser/main-bloodbank/bbrequests", method = RequestMethod.GET)
	public String showBloodBankRequests(HttpServletRequest request, HttpServletResponse response, ModelMap map,
			Model model) throws Exception {

		HttpSession session = request.getSession();
		if (session.getAttribute("userName") == null) {
			logger.info("Session Username NOT FOUND");
			request.setAttribute("request", request);
			// isko karna hai handle
			return "sessionFailed";
		}

		List<RequestBlood> bloodRequests = new ArrayList<RequestBlood>();

		logger.info("Login User: " + (String) session.getAttribute("userName"));
		BloodBank bankRecord = bloodDao.getBloodBankByName((String) session.getAttribute("userName"));

		System.out.println("Fetched Record:  " + bankRecord);
		System.out.println("Fetched Record:  " + bankRecord.getId());

		bloodRequests = requestBloodDao.getBankBloodRequests(bankRecord.getId());

		
		request.setAttribute("bbRequestList", bloodRequests);

		request.setAttribute("updateMsg", "Action Taken Already!");
		logger.info("showBloodBankRequests invoked");
		return "bloodbank-requests";
	}

	@RequestMapping(value = "/existingUser/main-bloodbank/bbrequestupdate", method = RequestMethod.GET)
	public String showBloodRequestUpdate(HttpServletRequest request, HttpServletResponse response, ModelMap map,
			Model model) throws Exception {
		int res = 0,upt=0,rbAmount=0,diffRB=0;
		HttpSession session = request.getSession();
		if (session.getAttribute("userName") == null) {
			logger.info("Session Username NOT FOUND");
			request.setAttribute("request", request);
			// isko karna hai handle
			return "sessionFailed";
		}

		String str = request.getParameter("update");
		
		RequestBlood rb = requestBloodDao.getRequestById(Long.parseLong(request.getParameter("requestId")));

		BloodBank bloodbank =bloodDao.getBloodBankByName((String) session.getAttribute("userName"));
		
		long stock= stockDao.getBBStockSumType(bloodbank.getId(), rb.getBloodType());
		logger.info("showBloodBankRequestsUpdate invoked");
		if (str.equalsIgnoreCase("update")) {
			System.out.println("Ander ka LOGIC!");
			logger.info("showBloodBankRequestsUpdate Update!");
			int bbAmount=0;
			if(stock!=0)
			{
				bbAmount=(int)stock;
			}
			System.out.println("in controller bbAmount " + bbAmount);

			 rbAmount =  rb.getBloodAmount();
			System.out.println("in controller rbAmonunt " + rbAmount);
			
			diffRB = bbAmount - rbAmount;
			System.out.println("in controller Difference " + diffRB);

			if (diffRB < 0) {
				request.setAttribute("nostock", "Insufficient Blood for this BloodType in the BloodBank");
				request.setAttribute("bloodQuantity", bbAmount);
				request.setAttribute("singleBloodRequest", rb);
				return "bloodbank-request-update";
			} else {
				request.setAttribute("yesstock", "Sufficient Blood for this BloodType in the BloodBank");
				request.setAttribute("bloodQuantity", bbAmount);
				request.setAttribute("singleBloodRequest", rb);
				return "bloodbank-request-update";
			}
			


		}
		
		if (str.equalsIgnoreCase("Approved") || str.equalsIgnoreCase("Rejected"))  {
			System.out.println("Approve/Reject ka LOGIC!");
			logger.info("showBloodBankRequestsUpdate Approved/Rejected");
			boolean flag = rb.getConfirmation().equals("NotApproved");
			if (flag == true) {
				System.out.println("Inside Approval Logic");
				System.out.println("Confirmation Value: " + request.getParameter("update"));
				res = requestBloodDao.setRequestStatus(Long.parseLong(request.getParameter("requestId")), str);
				if(res>0)
				{
					if(str.equalsIgnoreCase("Approved"))
					{
						int bbAmount1=0;
						if(stock!=0)
						{
							bbAmount1=(int)stock;
						}
						int rbAmount1 =  rb.getBloodAmount();
						
						int newDiff=bbAmount1-rbAmount1;
						
						upt=stockDao.updateStockById(bloodbank.getId(), rb.getBloodType(), newDiff);
						if(upt>0)
						{	EmailMessage.bloodRequestAction(rb.getHospital().getEmail(), bloodbank.getEmail(),rb.getId(), "Approved");
							return "redirect:/existingUser/main-bloodbank/bbrequests";
						}
					}
					EmailMessage.bloodRequestAction(rb.getHospital().getEmail(), bloodbank.getEmail(),rb.getId(), "Rejected");
				}
				
				System.out.println("UPDATE RESULT RES:  " + res);
			}

		}

		return "redirect:/existingUser/main-bloodbank/bbrequests";
	}
}
