package com.neu.bloodbank.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.neu.bloodbank.pojo.BloodBank;
import com.neu.bloodbank.pojo.Donor;
import com.neu.bloodbank.pojo.Hospital;
import com.neu.bloodbank.pojo.SuperRole;

public class BloodBankDao extends DAO {
private static final Logger logger = LoggerFactory.getLogger(BloodBankDao.class);
	
	private RoleDao roleDao;
	
     public BloodBankDao() {
		
		roleDao = new RoleDao();
	}
     
     public void save(BloodBank bloodbank) throws Exception {
 		try {
 			begin();
 			logger.info("Begin Transaction BloodBank");
 			getSession().save(bloodbank);
 			logger.info("Save Transaction BloodBankDAO Class Save BloodBank");
 			commit();
 			logger.info("Commit BloodBank");
 		}catch(Exception e) {
 			logger.info("Inside Exception....Beginning to Rollback!");
 			rollback();
 			System.out.println("-----------------------------------------\n\n"+e.getLocalizedMessage()+" "+e.getMessage()+"\n");
 			throw new Exception("Exception while registering the donor: "+e.getMessage());	
 		}finally {
 			close();
 		}
 		
 	}
     
     
      	public List<BloodBank> getAllBloodBankList() throws Exception {
 		List<BloodBank> bbList = new ArrayList<BloodBank>();
 		try {
 			logger.info("Transaction Begin getAllBloodBankList() ");
 			begin();
 			logger.info("Query Begin getAllBloodBankList()");
 			Criteria crit = getSession().createCriteria(BloodBank.class);
 			bbList=crit.list();
 			commit();
 			logger.info("BloodBankList" + bbList );
 			logger.info("Commit After getAllBloodBankList()");
 			return bbList;
 		} catch (HibernateException e) {
 			logger.info("Rollback started getAllBloodBankList()");
 			rollback();
 			logger.info("Rollback complete getAllBloodBankList()");
 			throw new Exception("Could not get bloodbank " + bbList, e);
 		}finally {
 			close();
 		}
 	}
      	
    	public BloodBank getBloodBankRecord(String userName, String password) throws Exception {
    		try {
    			logger.info("Before Begin getBloodBankRecord(name, pass)");
    			begin();
    			logger.info("After Begin getBloodBankRecord(name, pass)");
    			Query q = getSession().createQuery("from BloodBank where userName = :userName AND password = :password");
    			q.setString("userName", userName);
    			q.setString("password", password);
    			logger.info("Query for getBloodBankRecord(name, pass)");
    			BloodBank uniqueBank = (BloodBank) q.uniqueResult();
    			logger.info("Query finished getBloodBankRecord(name, pass)");
    			commit();
    			logger.info("After commit getBloodBankRecord(name, pass)");
    			return uniqueBank;
    		} catch (HibernateException e) {
    			logger.info("Before Rollback getBloodBankRecord(name, pass)");
    			rollback();
    			logger.info("After Rollback getBloodBankRecord(name, pass)");
    			
				throw new Exception("Could not get bloodbank " + userName, e);
    		}finally {
    			close();
    		}
    	}
    	
    	
    	public BloodBank getBloodBankByName(String bloodbank) throws Exception {
    		
    		try {
    			logger.info("Before Begin getBloodBankByName(name)");
    			begin();
    			logger.info("After Begin getBloodBankByName(name)");
    			Query q = getSession().createQuery("from BloodBank where name = :name");
    			q.setString("name", bloodbank);
    			BloodBank bbRecord = (BloodBank) q.uniqueResult();
    			commit();
    			logger.info("After COMMIT getBloodBankByName(name)");
    			return bbRecord;
    			
    		} catch (HibernateException e) {
    			logger.info("Before Rollback getBloodBankByName(name)");
    			rollback();
    			logger.info("Before Rollback getBloodBankByName(name)");
    			throw new Exception("Could not get BloodBankRecord " + bloodbank, e);
    		}finally {
    			close();
    		}
    	}
    	
    	
    	public int updateBloodAmountById(long bid, String type, int amount) {
    		int res = 0;
    		try {

    			begin();

    			Query query = getSession().createQuery("Update BloodBank FROM RequestBlood WHERE id= :bid");
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

    	
    
     
     
     
     public boolean authenticateBBRegistration(BloodBank bloodbank) {
 		for(SuperRole superRole: roleDao.getSuperRoles()) {
 			if( superRole.getEmail().equals(bloodbank.getEmail())) {
 				return true;
 			}else if(superRole.getUserName().equals(bloodbank.getUserName())) {
 				return true;
 			}
 		}
 		
 		return false;
 		
 	}
}
