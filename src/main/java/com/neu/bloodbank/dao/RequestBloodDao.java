package com.neu.bloodbank.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.neu.bloodbank.pojo.BloodBank;
import com.neu.bloodbank.pojo.RequestBlood;

public class RequestBloodDao extends DAO {

	private static final Logger logger = LoggerFactory.getLogger(BloodBankDao.class);

	private RoleDao roleDao;

	public RequestBloodDao() {

		roleDao = new RoleDao();
	}

	public void save(RequestBlood requestBlood) throws Exception {
		try {
			begin();
			logger.info("Begin Transaction RequestBlood");
			getSession().save(requestBlood);
			logger.info("Save Transaction RequestBlood Class Save RequestBlood");
			commit();
			logger.info("Commit RequestBlood");
		} catch (Exception e) {
			logger.info("Before Rollback RequestBlood");
			rollback();
			logger.info("After Rollback RequestBlood");
			System.out.println(e.getLocalizedMessage() + "  " + e.getMessage() + "\n");
			throw new Exception("Exception while creating a blood request: " + e.getMessage());
		} finally {
			close();
		}

	}

	public List<RequestBlood> getHospitalRequestHistory(long hid) throws Exception {
		List<RequestBlood> hospHistory = new ArrayList<RequestBlood>();
		try {
			logger.info("Before begin Transaction getHospitalRequestHistory(id)");
			begin();

			// String q="From RequestBlood where hospital=:hid";
			Query query = getSession().createQuery("From RequestBlood where hospital=:hid");
			query.setLong("hid", hid);
			hospHistory = query.list();

//			@SuppressWarnings("deprecation")
//			Criteria bloodRequestCrit = getSession().createCriteria(BloodRequest.class);
//			Criteria hospitalCrit = bloodRequestCrit.createCriteria("hospital");
//			hospitalCrit.add(Restrictions.eq("id",hid));
//			bloodRequests = bloodRequestCrit.list();
			commit();
			logger.info("After Commit Transaction getHospitalRequestHistory(id)");
			return hospHistory;
		} catch (HibernateException e) {
			logger.info("Before Rollback getHospitalRequestHistory(id)");
			rollback();
			logger.info("After Rollback getHospitalRequestHistory(id)");
			throw new Exception("Could not get bloodrequest " + hid, e);
		} finally {
			close();
		}
	}

	public RequestBlood getRequestById(long bid) throws Exception {
		try {
			begin();
			Query query = getSession().createQuery("FROM RequestBlood WHERE id= :bid");
			query.setLong("bid", bid);

			RequestBlood bloodRequest = (RequestBlood) query.uniqueResult();

			commit();
			return bloodRequest;

		} catch (Exception e) {
			rollback();
			System.out.println("Unable to fetch confirmation value: " + e.getMessage());
			throw new Exception("Couldn't Fetch Record for request " + bid, e);
		} finally {
			close();
		}

	}

	public int deleteRequestById(long bid) {
		int res = 0;
		try {

			begin();

			Query query = getSession().createQuery("DELETE FROM RequestBlood WHERE id= :bid");
			query.setLong("bid", bid);
			res = query.executeUpdate();
			System.out.println("Rows Deleted are " + res);
			commit();

			return res;
		} catch (HibernateException e) {
			rollback();
			System.out.println("Cannot Delete the bloodrequest" + bid + "\n" + e.getMessage());

		} catch (Exception e) {
			rollback();
			System.out.println("Cannot Delete the bloodrequest" + bid + "\n" + e.getMessage());
		} finally {
			close();
		}
		return res;

	}

	public int setRequestStatus(long bid, String str) {
		int res = 0;
		System.out.println("INSIDE UPDATE STATUS DAO");
		try {
			begin();
			String upd = "UPDATE RequestBlood SET confirmation = :str " + "WHERE id= :bid";
			Query q = getSession().createQuery(upd);
			q.setString("str", str);
			q.setLong("bid", bid);
			res = q.executeUpdate();
			System.out.println("Rows Update: " + res);
			commit();
		} catch (Exception e) {
			rollback();
			System.out.println("Cannot Update the bloodrequest" + bid + "\n" + e.getMessage());

		} finally {
			close();
		}
		return res;
	}

	public List<RequestBlood> getBankBloodRequests(long bid) throws Exception {
		List<RequestBlood> requests = new ArrayList<RequestBlood>();
		try {
			begin();
			Query q = getSession().createQuery("FROM RequestBlood where bloodBank=: bid");
			q.setLong("bid", bid);
			requests = q.list();
			commit();
			System.out.println("afsgfdfghgfds---------------------" + requests);
			return requests;
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Cannot fetch bloodRequests for Bank " + bid, e);
		} finally {
			close();
		}
	}

	public List<Object[]> getCurrentStock() {
		try {
			begin();
			String sql = "SELECT bb.Name, dh.blood_type, SUM(dh.amount)-(SELECT IFNULL(SUM(br.amount_requested),0) FROM BloodBankManagement.blood_request as br WHERE br.confirmation=\"approve\" AND dh.blood_type=br.blood_type )\n"
					+ "FROM Donation_History as dh\n" + "RIGHT JOIN blood_bank as bb\n"
					+ "ON bb.Id = dh.Blood_Bank_Id \n" + "GROUP BY bb.Name, dh.blood_type;";
			SQLQuery query = getSession().createSQLQuery(sql);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List results = query.list();// returns an list of Maps and one single map contains [keys:{Query,Name,
										// BloodType}, Values:{Query Result,Name, BloodType}]
			System.out.println("**********CurrentStock\n" + results.get(0));
			System.out.println("resulsss full "+ results);
			commit();
			return results;
		} catch (Exception e) {
			System.out.println("Exception in current stock");
			rollback();
			System.out.println("Can not fetch current stocks: " + e.getMessage());
			return null;
		} finally {
			close();
		}

	}

}
