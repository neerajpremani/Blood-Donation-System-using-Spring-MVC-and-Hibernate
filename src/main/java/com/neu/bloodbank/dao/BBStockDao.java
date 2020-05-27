package com.neu.bloodbank.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.neu.bloodbank.exception.BBStockException;
import com.neu.bloodbank.pojo.BBStock;
import com.neu.bloodbank.pojo.BloodBank;

public class BBStockDao extends DAO {
	private static final Logger logger = LoggerFactory.getLogger(BloodBankDao.class);

	private RoleDao roleDao;

	public BBStockDao() {

		roleDao = new RoleDao();
	}

	public void save(BBStock bbstock) throws BBStockException {
		try {
			begin();
			logger.info("Begin Transaction BBStock");
			getSession().save(bbstock);
			logger.info("Save Transaction BBStock Class Save BBStock");
			commit();
			logger.info("Commit BBStock");
		} catch (Exception e) {
			logger.info("Inside Exception....Beginning to Rollback!");
			rollback();
			System.out.println("-----------------------------------------\n\n" + e.getLocalizedMessage() + " "
					+ e.getMessage() + "\n");
			throw new BBStockException("Exception while registering the BBStock: " + e.getMessage());
		} finally {
			close();
		}

	}

	public int updateStockById(long bid, String type, int amount) {
		int res = 0;
		try {

			begin();

			Query query = getSession()
					.createQuery("Update BBStock Set bloodAmount=:amount WHERE bloodBank= :bid and bloodType=:type");
			query.setLong("bid", bid);
			query.setInteger("amount", amount);
			query.setString("type", type);
			res = query.executeUpdate();
			System.out.println("Rows Updated are " + res);
			commit();

			return res;
		} catch (HibernateException e) {
			rollback();
			System.out.println("Cannot Update the BBSTock" + bid + "\n" + e.getMessage());
			logger.info("HibernateException updateStockById!");
		} catch (Exception e) {
			rollback();
			logger.info("Exception updateStockById!");
			System.out.println("Cannot Delete the bloodrequest" + bid + "\n" + e.getMessage());
		} finally {
			close();
		}
		return res;

	}

	public BBStock getBBStockRecord(long bid, String bloodType) throws BBStockException {
		try {
			begin();
			logger.info("getBBStockRecord BEGIN!");
			Query q = getSession().createQuery("From BBStock where bloodBank = :bid and bloodType=:bloodType");
			q.setLong("bid", bid);
			q.setString("bloodType", bloodType);
			BBStock bbstock = (BBStock) q.uniqueResult();
			System.out.println("FETCHED RECORDS IN DAO :" + bbstock);

			commit();
			return bbstock;
		} catch (HibernateException e) {
			logger.info("getBBStockRecord EXCEPTION");
			rollback();
			throw new BBStockException("Could not get BBSTOCK History");
		} finally {
			close();
		}
	}
	
	public long getBBStockSumType(long bid, String bloodType) throws BBStockException {
		try {
			begin();
			logger.info("getBBStockSumType BEGIN!");
			Query q = getSession().createQuery("select sum(bloodAmount) as total from BBStock where bloodBank = :bid and bloodType=:bloodType");
			q.setLong("bid", bid);
			q.setString("bloodType", bloodType);
			List<Long> bbstock = q.list();
			
			
			System.out.println("FETCHED RECORDS IN DAO :" + bbstock);

			commit();
			if(bbstock.get(0)==null)
			{
				return 0L;
			}
			return bbstock.get(0);
		} catch (HibernateException e) {
			logger.info("getBBStockSumType EXCEPTION!");
			rollback();
			throw new BBStockException("Could not get BBSTOCK History");
		} finally {
			close();
		}
	}

	public List<Object[]> getBloodBankCurrentStock(long bid) throws BBStockException {
		List<Object[]> currentStock = new ArrayList<Object[]>();
		try {
			begin();
			logger.info("getBloodBankCurrentStock BEGIN!");
			Query q = getSession().createQuery(
					"select bloodType as type, sum(bloodAmount) as total from BBStock where bloodBank=:bid group by bloodType");
			q.setLong("bid", bid);

			currentStock = q.list();
			System.out.println("FETCHED RECORDS IN DAO :" + currentStock);
			for (Object ob[] : currentStock) {
				System.out.println("x--x-x-x-x-x-x-x-x " + ob[0] + "  " + ob[1]);
			}

			commit();
			return currentStock;
		} catch (HibernateException e) {
			logger.info("getBloodBankCurrentStock EXCEPTION!");
			rollback();
			throw new BBStockException("Could not fetch Results for current stocK BB");
		} finally {
			close();
		}

	}

}