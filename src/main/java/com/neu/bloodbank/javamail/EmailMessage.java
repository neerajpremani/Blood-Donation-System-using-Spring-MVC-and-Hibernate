package com.neu.bloodbank.javamail;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.neu.bloodbank.pojo.RequestBlood;


public class EmailMessage {

		public static void registrationEmail(String user, String receiver, String role) throws EmailException {
		    Email email = new SimpleEmail();
		    email.setHostName("smtp.gmail.com");
		    email.setSmtpPort(465);
		    email.setAuthenticator(new DefaultAuthenticator("neerajpremani@gmail.com", "JMDd@01021818"));
		    email.setSSLOnConnect(true);
		    email.setFrom("do-not-reply@gmail.com");
		    email.setSubject("Welcome to Blood Bank Facility  " + user);
		    email.setMsg("You have successfully signed up into "+ role.toUpperCase() + " as username "+ user  );
		    email.addTo("neerajpremani01@gmail.com");
		    email.send();
		}
		
		public static void bloodRequest(String hospitalEmail, String bbEmail) throws EmailException {
		    Email email = new SimpleEmail();
		    email.setHostName("smtp.gmail.com");
		    email.setSmtpPort(465);
		    email.setAuthenticator(new DefaultAuthenticator("neerajpremani@gmail.com", "JMDd@01021818"));
		    email.setSSLOnConnect(true);
		    email.setFrom("do-not-reply@gmail.com");
		    email.setSubject("Blood Request Created");
		    email.setMsg("Blood Request Sent from hospital" + hospitalEmail + "to BloodBank "+ bbEmail );
		    email.addTo("neerajpremani01@gmail.com");
		    email.send();
		}
		
		public static void bloodRequestAction(String hospEmail, String bbEmail,long id, String action) throws EmailException {
			System.out.println("BLOOD REQUEST ACTION-----------------------");
		    Email email = new SimpleEmail();
		    email.setHostName("smtp.gmail.com");
		    email.setSmtpPort(465);
		    email.setAuthenticator(new DefaultAuthenticator("neerajpremani@gmail.com", "JMDd@01021818"));
		    email.setSSLOnConnect(true);
		    email.setFrom("do-not-reply@gmail.com");
		    email.setSubject("Blood Request Action Taken!");
		    email.setMsg("Blood Request ID  " + id + " has been "+ action+ "by BloodBank " );
		    email.addTo("neerajpremani01@gmail.com");
		    email.send();
		}
		
		

	
}
