package com.neu.bloodbank.dao;

import com.neu.bloodbank.pojo.SuperRole;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

public class RoleDao extends DAO {
	private static final Logger logger = LoggerFactory.getLogger(RoleDao.class);

	public RoleDao() {

	}

	public void save(SuperRole superRole) throws Exception {
		try {
			logger.info("Before Begin Save SuperRole");
			begin();
			logger.info("After Begin Save SuperROle");
			getSession().save(superRole);
			logger.info("After Save SuperRole");
			commit();
			logger.info("After Commit SAve SuperRole");
		} catch (Exception e) {
			logger.info("Before RollBack  SAve SuperRole");
			rollback();
			logger.info("After Rollback SAve SuperRole");
			throw new Exception("Exception while registering the hospital: " + e.getMessage());
		} finally {
			close();
		}

	}

	public ArrayList<SuperRole> getSuperRoles() {
		ArrayList<SuperRole> superRoles = new ArrayList<SuperRole>();
		try {
			logger.info("Before Begin getSuperRoles()");
			begin();
			logger.info("After BEgin getSuperRoles()");
			Query q = getSession().createQuery("FROM SuperRole");
			logger.info("After Quering DB getSuperRoles()");
			superRoles = (ArrayList) q.list();
			
		} catch (Exception e) {
			logger.info("Before Rollback getSuperRoles()");
			rollback();
			logger.info("After RollBack getSuperRoles()");
		} finally {
			close();
		}

		return superRoles;

	}


	public SuperRole getLoggedInUserDetail(String userName, String password) {
		SuperRole loggedInUser = new SuperRole();

		try {
			logger.info("Before Begin getLoggedInUserDetail(n,p)");
			begin();
			Query q = getSession().createQuery("FROM SuperRole WHERE userName=:userName AND password=:password");
			q.setString("userName", userName);
			q.setString("password", password);
			loggedInUser = (SuperRole) q.uniqueResult();
			logger.info("After Begin getLoggedInUserDetail(n,p)");
			commit();
			logger.info("After Commit getLoggedInUserDetail(n,p)");
		} catch (HibernateException e) {
			logger.info("Before RollBack getLoggedInUserDetail(n,p)");
			rollback();
			logger.info("After RollBack getLoggedInUserDetail(n,p)");
			System.out.println("\n********Exception in checking the user " + e.getMessage());

		} finally {
			close();

		}
		return loggedInUser;

	}
	
	public boolean checkUserRoleExists(String userName, String password, String superRole) {
		SuperRole loginUser = new SuperRole();

		try {
			logger.info("Before Begin checkLoginUserDetail(n,p,r)");
			begin();
			Query q = getSession().createQuery("FROM SuperRole WHERE userName=:userName AND password=:password AND superRole=:superRole");
			q.setString("userName", userName);
			q.setString("password", password);
			q.setString("superRole", superRole);
			
			loginUser = (SuperRole) q.uniqueResult();
			logger.info("After Query checkLoginUserDetail(n,p,r)");
			System.out.println("Inside RoleDao.checkLoginUser" + loginUser);
			commit();
			logger.info("After Commit checkLoginUserDetail(n,p,r)");
		} catch (NonUniqueResultException e) {
			logger.info("Exception checkLoginUserDetail(n,p,r) ");
			return false;
		} catch (HibernateException e) {
			logger.info("Before RollBack checkLoginUserDetail(n,p,r)");
			rollback();
			logger.info("After RollBack checkLoginUserDetail(n,p,r)");
			System.out.println("\n********Exception in checking the user " + e.getMessage());

		} finally {
			close();
		}

		return loginUser != null ? true : false;

	}
	
	
	public SuperRole checkUsernameRole(String userName, String superRole) {
		SuperRole loginUser = new SuperRole();

		try {
			logger.info("Before Begin checkUsernameRole(n,p)");
			begin();
			Query q = getSession().createQuery("FROM SuperRole WHERE userName=:userName  AND superRole=:superRole");
			q.setString("userName", userName);
			
			q.setString("superRole", superRole);
			
			loginUser = (SuperRole) q.uniqueResult();
			logger.info("After Query checkUsernameRole(n,p,r)");
			System.out.println("Inside RoleDao.checkLoginUser" + loginUser);
			commit();
			logger.info("After Commit checkUsernameRole(n,p,r)");
		} catch (NonUniqueResultException e) {
			logger.info("Exception checkUsernameRole(n,p,r) ");
			
		} catch (HibernateException e) {
			logger.info("Before RollBack checkUsernameRole(n,p,r)");
			rollback();
			logger.info("After RollBack checkUsernameRole(n,p,r)");
			System.out.println("\n********Exception in checking the user " + e.getMessage());

		} finally {
			close();
		}

		return loginUser;

	}

	
	
	
}
