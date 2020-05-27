package com.neu.bloodbank.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.neu.bloodbank.pojo.BloodBank;
import com.neu.bloodbank.pojo.BloodDonationHistory;

public class BloodDonationHistoryDao extends DAO {
	private static final Logger logger = LoggerFactory.getLogger(BloodDonationHistoryDao.class);

	private RoleDao roleDao;

	public BloodDonationHistoryDao() {

		roleDao = new RoleDao();
	}

	public void save(BloodDonationHistory bloodDonation) throws Exception {
		try {
			begin();
			logger.info("Begin Transaction BloodDonationHistory");
			getSession().save(bloodDonation);
			logger.info("Save Transaction BloodDonationHistory Class Save BloodDonationHistory");
			commit();
			logger.info("Commit BloodDonationHistory");
		} catch (Exception e) {
			logger.info("Inside Exception....Beginning to Rollback!");
			rollback();
			System.out.println("-----------------------------------------\n\n" + e.getLocalizedMessage() + " "
					+ e.getMessage() + "\n");
			throw new Exception("Exception while registering the bloodDonation History: " + e.getMessage());
		} finally {
			close();
		}
	}

	public List<BloodDonationHistory> getDonationHistoryById(long id) throws Exception {
		List<BloodDonationHistory> bbDonationHistory = new ArrayList<BloodDonationHistory>();
		try {
			begin();
			Query q = getSession().createQuery("from BloodDonationHistory where bloodBank = :id");
			q.setLong("id", id);
			bbDonationHistory = q.list();
			System.out.println("FETCHED RECORDS IN DAO :" + bbDonationHistory);
			commit();
			return bbDonationHistory;
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not get Donation History");
		} finally {
			close();
		}

	}
	
	public List<Long> getBloodAmountById(long bid, String btype) throws Exception {
		List<Long> bloodAmountbyType = new ArrayList<Long>();
		try {
			begin();
			Query q = getSession().createQuery("select sum(bloodAmount) as total from BloodDonationHistory where bloodBank = :bid and bloodType=:btype");
			q.setLong("bid", bid);
			q.setString("btype", btype);
			bloodAmountbyType = q.list();
			System.out.println("FETCHED RECORDS IN DAO :" + bloodAmountbyType);
			for(Object ob: bloodAmountbyType)
			{
				System.out.println("x--x-x-x-x-x-x-x-x "+ ob );
			}
			
			commit();
			return bloodAmountbyType;
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not get Donation History");
		} finally {
			close();
		}

	}
	
	public List<BloodDonationHistory> getDonorHistoryById(long id) throws Exception {
		List<BloodDonationHistory> bbDonorHistory = new ArrayList<BloodDonationHistory>();
		try {
			begin();
			Query q = getSession().createQuery("from BloodDonationHistory where donor = :id");
			q.setLong("id", id);
			bbDonorHistory = q.list();
			System.out.println("FETCHED RECORDS IN DAO :" + bbDonorHistory);
			commit();
			return bbDonorHistory;
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not get Donation History");
		} finally {
			close();
		}

	}

	public List<Object[]> getBloodStockById(long bid) throws Exception {
		// List<HashMap<String, Integer>> bbstocks=new ArrayList<HashMap<String,
		// Integer>>();
		try {
			begin();
			String query = "select bloodType as type, sum(bloodAmount) as total from BloodDonationHistory where bloodBank= : bid\r\n"
					+ "group by bloodType order by bloodType asc";
			Query q = getSession().createQuery(query);
			q.setLong("bid", bid);
			List<Object[]> bbstocks = q.list();

			for (Object obj[] : bbstocks) {
				System.out.println("---- : yeh1  " + obj[0]);
				System.out.println("---- : yeh2  " + obj[1]);
			}

		//	System.out.println("-----------------ininin-------------" + bbstocks);
			System.out.println("*****\n\nInside try::DonationHistoryDao.getBloodBankStock() ");
			commit();
			return bbstocks;

		} catch (HibernateException e) {
			System.out.println("*****\n\nInside catch::DonationHistoryDao.getBloodBankStock() ");
			rollback();
			throw new Exception("Could not get blood bank stock");

		} finally {
			close();
		}
	}

}
