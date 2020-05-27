package com.neu.bloodbank.dao;

import com.neu.bloodbank.pojo.Hospital;
import com.neu.bloodbank.pojo.SuperRole;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;




public class HospitalDao extends DAO {
	private static final Logger logger = LoggerFactory.getLogger(HospitalDao.class);
	
	private RoleDao roleDao;
	
	public HospitalDao() {
		roleDao = new RoleDao();
		
	}
	
	public void save(Hospital hospital) throws Exception {
		try {
			begin();
			logger.info("Transaction begin and Before Save HospitalDao");
			getSession().save(hospital);
			logger.info("Transaction After Save HospitalDao");
			commit();
			logger.info("After Commit Hospital");
		}catch(Exception e) {
			logger.info("Before RollBack Hospital");
			rollback();
			logger.info("Before RollBack Hospital");
			System.out.println("---------------------------------------\n\n"+e.getLocalizedMessage()+" "+e.getMessage()+"\n");
			throw new Exception("Exception while registering the hospital: "+e.getMessage());	
		}finally {
			close();
		}
		
	}
	

	public List<Hospital> getAllHospitalsList() throws Exception {
		List<Hospital> hospitals = new ArrayList<Hospital>();
		try {
			logger.info("Transaction Begin getAllHospitalsList() ");
			begin();
			logger.info("Query Begin getAllHospitalsList()");
			Query q = getSession().createQuery("from Hospital");
			hospitals = q.list();
			commit();
			logger.info("Commit After getAllHospitalsList()");
			return hospitals;
		} catch (HibernateException e) {
			logger.info("Rollback started getAllHospitalsList()");
			rollback();
			logger.info("Rollback complete getAllHospitalsList()");
			throw new Exception("Could not get hospital " + hospitals, e);
		}finally {
			close();
		}
	}

	
	public Hospital getUniqueHospital(long id) throws Exception {
		try {
			logger.info("Before begin getUniqueHospital(id) ");
			begin();
			logger.info("After begin getUniqueHospital(id)");
			Query q = getSession().createQuery("from Hospital where Id = :id");
			q.setLong("id", id);
			Hospital hospital = (Hospital) q.uniqueResult();
			logger.info("Query fetched getUniqueHospital(id)");
			commit();
			logger.info("After Commit getUniqueHospital(id)");
			return hospital;
		} catch (HibernateException e) {
			logger.info("Before Rollback getUniqueHospital(id)");
			rollback();
			logger.info("After Rollback getUniqueHospital(id)");
			throw new Exception("Could not get hospital " + id, e);
		}finally {
			close();
		}
	}
	
	
	public Hospital getHospitalRecord(String userName, String password) throws Exception {
		try {
			logger.info("Before Begin getHospitalRecord(name, pass)");
			begin();
			logger.info("After Begin getHospitalRecord(name, pass)");
			Query q = getSession().createQuery("from Hospital where userName = :userName AND password = :password");
			q.setString("userName", userName);
			q.setString("password", password);
			logger.info("Query for getHospitalRecord(name, pass)");
			Hospital hospital = (Hospital) q.uniqueResult();
			System.out.println("-x-x-x-x-x-x-x--x-x-x-x-x-x-x--x-x-x"+ hospital);
			logger.info("Query finished getHospitalRecord(name, pass)");
			commit();
			logger.info("After commit getHospitalRecord(name, pass)");
			return hospital;
		} catch (HibernateException e) {
			logger.info("Before Rollback getHospitalRecord(name, pass)");
			rollback();
			logger.info("After Rollback getHospitalRecord(name, pass)");
			throw new Exception("Could not get hospital " + userName, e);
		}finally {
			close();
		}
	}
	
	public Hospital getHospitalRecordByName(String userName) throws Exception {
		try {
			logger.info("Before Begin getHospitalRecordByName(name)");
			begin();
			logger.info("After Begin getHospitalRecordByName(name)");
			Query q = getSession().createQuery("from Hospital where userName = :userName");
			q.setString("userName", userName);
			
			logger.info("Query for getHospitalRecordByName(name)");
			Hospital hospital = (Hospital) q.uniqueResult();
			System.out.println("-x-x-x-x-x-x-x--x-x-x-x-x-x-x--x-x-x"+ hospital);
			logger.info("Query finished getHospitalRecordByName(name)");
			commit();
			logger.info("After commit getHospitalRecordByName(name)");
			return hospital;
		} catch (HibernateException e) {
			logger.info("Before Rollback getHospitalRecordByName(name)");
			rollback();
			logger.info("After Rollback getHospitalRecordByName(name)");
			throw new Exception("Could not get hospital " + userName, e);
		}finally {
			close();
		}
	}
	
	//redundant method --USED CONSTRAINT VALIDATOR BACK-END
	public boolean authenticateHospitalRegistration(Hospital hospital) {
		for(SuperRole superRole: roleDao.getSuperRoles()) {
			if( superRole.getEmail().equals(hospital.getEmail())) {
				return true;
			}else if(superRole.getUserName().equals(hospital.getUserName())) {
				return true;
			}
		}
		
		return false;
		
	}
	
	
	}
	


