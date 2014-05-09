package com.pesse.sms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import com.pesse.sms.Helper;
import com.pesse.sms.common.Session;
import com.pesse.sms.common.WebPages;

public class HomeServlet extends HttpServlet {
	
	private static final Logger logger = Logger.getLogger(HomeServlet.class);

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		logger.info("Inside home servlet. Checking whether logged in");
		if(Session.isNotLoggedIn(request, response)) {
			return;
		}
		logger.info("Logged in");
		
		HttpSession session = request.getSession();
		HashMap<String, String> htmlParams = new HashMap<String, String>();
		htmlParams.put("title", "Home page");
		
		Helper.writeStartHtml(out, htmlParams);
		out.println("Hello " + session.getAttribute(Session.EMAIL_ID));
		out.println("Home page");
		out.println("<br /> <a href='" + WebPages.LOGOUT_PAGE + "'>Logout</a>");
		
		Helper.writeEndHtml(out);
	}
	
}
