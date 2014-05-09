package com.pesse.sms.common;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import com.pesse.sms.Helper;
import com.pesse.sms.Person;

public class Session {
	
	private static Logger logger = Logger.getLogger(Session.class);
	
	public static final String EMAIL_ID = "email";
	
	public static boolean isValidSession(HttpSession session) {
		return session != null && 
			session.getAttribute(Session.EMAIL_ID) != null;
	}
	
	public static boolean isNotLoggedIn(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HashMap<String, String> reqParams = Helper.httpParseQuery(request.getQueryString()); 
		
		String loginPage = WebPages.LOGIN_PAGE;
		String homePage = WebPages.HOME_PAGE;
		String continueTo = null;
		
		HttpSession session = request.getSession();
		
		if(!Session.isValidSession(session)) {
			
			logger.info("Not logged in");
			if(!homePage.equals(request.getRequestURI())) {
				
				continueTo = request.getRequestURI();
				if(request.getQueryString() != null) {
					
					continueTo += "?" + request.getQueryString();
					logger.info(continueTo);
				} 
				
				reqParams.put(UrlKeys.CONTINUE, continueTo);
				loginPage = WebPages.LOGIN_PAGE + "?" + Helper.httpBuildQuery(reqParams);
				
				logger.info("Redirecting to " + loginPage);
			} 
			
			response.sendRedirect(loginPage);
			return true;
		} else {
			logger.info("Already logged in");
			return false;
		}
	}

	public static void doLogin(HttpSession session, Person person) {
		
		logger.info("Setting session attributes, email = " + person.getEmailId());
		session.setAttribute(Session.EMAIL_ID, person.getEmailId());
	}
}

