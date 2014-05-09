package com.pesse.sms.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import com.pesse.sms.common.WebPages;

public class LogoutServlet extends HttpServlet {
	
	private static Logger logger = Logger.getLogger(LogoutServlet.class);

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		
		logger.info("Logging out...");
		request.getSession().invalidate();
		
		logger.info("Redirecting to Login page");
		response.sendRedirect(WebPages.LOGIN_PAGE);
	}
}
