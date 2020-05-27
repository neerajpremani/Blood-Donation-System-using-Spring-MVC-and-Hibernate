package com.neu.bloodbank.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.neu.bloodbank.HomeController;
import com.neu.bloodbank.pojo.Donor;
import com.neu.bloodbank.pojo.SuperRole;




public class DonorDao extends DAO {
	private static final Logger logger = LoggerFactory.getLogger(DonorDao.class);
	
	private RoleDao roleDao;
	
	public DonorDao() {
		roleDao = new RoleDao();
		
	}
	
	public void save(Donor donor) throws Exception {
		try {
			begin();
			logger.info("Begin Transaction");
			getSession().save(donor);
			logger.info("Save Transaction DonorDao Class Save Donor");
			commit();
			logger.info("Commit Donor");
		}catch(Exception e) {
			logger.info("Inside Exception....Beginning to Rollback!");
			rollback();
			System.out.println("-----------------------------------------\n\n"+e.getLocalizedMessage()+" "+e.getMessage()+"\n");
			throw new Exception("Exception while registering the donor: "+e.getMessage());	
		}finally {
			close();
		}
		
	}
	
	public Donor getUniqueDonor(long id) throws Exception {
		try {
			begin();
			logger.info("Begin Transaction getUniqueDonor(id)");
			Query q = getSession().createQuery("from Donor where Id = :id");
			q.setLong("id", id);
			Donor donor = (Donor) q.uniqueResult();
			logger.info("Unique Result Found!");
			commit();
			logger.info("Commit getUniqueDonor(id)");
			return donor;
		} catch (HibernateException e) {
			logger.info("Exception in getUniqueDonor(id)");
			rollback();
			logger.info("Rollback getUniqueDonor(id)");
			throw new Exception("Could not getUniqueDonor " + id, e);
		}finally {
			close();
		}
	}
	
	
	public List<Donor> getDonorsList() throws Exception {
		List<Donor> donors =new ArrayList<Donor>();
		try {
			begin();
			logger.info("Begin Transaction getDonorsList()");
			Query q = getSession().createQuery("from Donor");
			 donors = q.list();
			 logger.info("Query processed getDonorsList()");
			commit();
			 logger.info("Query Commit getDonorsList()");
			return donors;
		} catch (HibernateException e) {
			logger.info("Rollback start getDonorsList()");
			rollback();
			
			logger.info("Rollback end getDonorsList()");
			throw new Exception("Could not get donor " +donors, e);
		}finally {
			close();
		}
	}
	
	public Donor getDonorDetail(String userName, String password) throws Exception {
		try {
			logger.info("Transaction Before begin getDonorDetail(name,pass)");
			begin();
			logger.info("Transaction After begin getDonorDetail(name,pass)");
			Query q = getSession().createQuery("from Donor where userName = :userName AND password= :password");
			q.setString("userName", userName);
			q.setString("password", password);
			Donor donor = (Donor) q.uniqueResult();
			logger.info("Results Fetch getDonorDetail(name,pass) & Commit Start");
			commit();
			logger.info("After Commit getDonorDetail(name,pass)");
			return donor;
		} catch (HibernateException e) {
			logger.info("Before Rollback getDonotDetail");
			rollback();
			logger.info("After Rollback getDonotDetail");
			throw new Exception("Could not get donor " + userName, e);
		}finally {
			close();
		}
	}
	
	public Donor getDonorDetailByName(String userName) throws Exception {
		try {
			logger.info("Transaction Before begin getDonorDetail(name,pass)");
			begin();
			logger.info("Transaction After begin getDonorDetail(name,pass)");
			Query q = getSession().createQuery("from Donor where userName = :userName");
			q.setString("userName", userName);
			
			Donor donor = (Donor) q.uniqueResult();
			logger.info("Results Fetch getDonorDetail(name,pass) & Commit Start");
			commit();
			logger.info("After Commit getDonorDetail(name,pass)");
			return donor;
		} catch (HibernateException e) {
			logger.info("Before Rollback getDonotDetail");
			rollback();
			logger.info("After Rollback getDonotDetail");
			throw new Exception("Could not get donor " + userName, e);
		}finally {
			close();
		}
	}
	
	public Donor verifyDonorDetail(String userName, String email) throws Exception {
		try {
			logger.info("Transaction Before begin verifyDonorDetail(name,pass)");
			begin();
			logger.info("Transaction After begin verifyDonorDetail(name,pass)");
			Query q = getSession().createQuery("from Donor where userName = :userName AND email= :email");
			q.setString("userName", userName);
			q.setString("email", email);
			Donor donor = (Donor) q.uniqueResult();
			logger.info("Results Fetch verifyDonorDetail(name,pass) & Commit Start");
			commit();
			logger.info("After Commit verifyDonorDetail(name,pass)");
			return donor;
		} catch (HibernateException e) {
			logger.info("Before Rollback verifyDonorDetail");
			rollback();
			logger.info("After Rollback verifyDonorDetail");
			throw new Exception("Could not get donor " + userName, e);
		}finally {
			close();
		}
	}
	
	
	public boolean authenticateDonorRegistration(Donor donor) {
		for(SuperRole superRole: roleDao.getSuperRoles()) {
			if( superRole.getEmail().equals(donor.getEmail())) {
				return true;
			}else if(superRole.getUserName().equals(donor.getUserName())) {
				return true;
			}
		}
		
		return false;
		
	}
	
	

}