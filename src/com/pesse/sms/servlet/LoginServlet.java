package com.pesse.sms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import com.pesse.sms.Helper;
import com.pesse.sms.Person;
import com.pesse.sms.common.Config;
import com.pesse.sms.common.ConfigKeys;
import com.pesse.sms.common.Session;
import com.pesse.sms.common.UrlKeys;
import com.pesse.sms.common.WebPages;

public class LoginServlet extends HttpServlet {
	
	private static Logger logger = Logger.getLogger(LoginServlet.class);

	private static final long serialVersionUID = 1L;

	private static final String SERVER_CONTEXT = Config.getProperty(ConfigKeys.SERVER_CONTEXT);
	HashMap<String, String> hm = new HashMap<String, String>();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		if(Session.isValidSession(request.getSession())) {
			logger.info("Already logged in. Redirecting to home page...");
			response.sendRedirect(WebPages.HOME_PAGE);
			return;
		}
		
		HashMap<String, String> htmlParams = new HashMap<String, String>();
		htmlParams.put("title", "Home page");
		
		Helper.writeStartHtml(out, htmlParams);
		out.println("<link rel='stylesheet' href='login.css' />");
		out.println("<div class='backgroundGradient'");
		out.println("<div class='headerStrip headerGradient'>.<div class='headerContent'><h1><div class='headText'><img class='logoHead' src='" + SERVER_CONTEXT + "/img/pesse.png' /> PESSE SMS Alerts </div></h1></div>.</div>");
		
		out.println("<div class='mainWrapper clearfix'>");
		
		out.println("<div class='loginWrapper'>");
		out.println("<div class='loginForm'>");
		out.println("<h1><div class='tcenter'>Login</div></h1>");
		out.println("<form action='' method='post'><table>");
		out.println("<tr><td class='right><label for='" + UrlKeys.EMAIL + "'>EmailId: </label></td><td><input type='text' id='" + UrlKeys.EMAIL + "'name='" + UrlKeys.EMAIL + "' /></td></tr>");
		out.println("<tr><td></td><td><span class='fieldDesc'>e.g.pat@example.com</span></td></tr>");
		out.println("<tr><td class='right'><label for='" + UrlKeys.PASSWORD + "'>Password: </label></td><td><input type='password' id='" + UrlKeys.PASSWORD + "'name='" + UrlKeys.PASSWORD + "' /></td></tr>");
		if(request.getParameter(UrlKeys.STATUS) != null) {
			out.println("<tr><td colspan='2' class='cAlign'><span class='error'>Invalid username or password</span></td></tr>");
		}
		out.println("<tr><td colspan='2'><div class='tcenter'><input type='submit' value='Login'/></div></td></tr>");
		out.println("<tr><td colspan='2'><div class='createAccLink '><a href='" + WebPages.REGISTER_PAGE + "'> Create an account >></a></div></td></tr>");
		out.println("</table></form>");
		out.println("</div>");
		out.println("</div>");
		
		
		out.println("<div class='lFloat imgDesg'><img src='" + SERVER_CONTEXT + "/img/chat.png' /></div>");
		out.println("<div class='features'>");
		out.println("PESSE SMS Alerts is a smart way to stay <br />" +
				"connected and send out updates and notifications");
		out.println("</div>");
		out.println("</div>");
		
		out.println("</div>");
		out.println("</div>");
		Helper.writeEndHtml(out);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		ErrorConstants loginError = null;
		
		if(Session.isValidSession(request.getSession())) {
			logger.info("Already logged in. Redirecting to home page...");
			response.sendRedirect(WebPages.HOME_PAGE);
			return;
		}
		
		logger.info("Posted to Login page");
		logger.info(request.getParameter(UrlKeys.EMAIL));
		hm.put(Person.F_EMAILID, request.getParameter(UrlKeys.EMAIL));
		logger.info("log in conditions " + hm);
		LinkedList<Person> personList = Person.getPersons(hm);
		
		if(personList.isEmpty()){

			String loginPage = WebPages.LOGIN_PAGE + "?" + UrlKeys.STATUS + "=" + ErrorConstants.INVALID_LOGIN_CREDENTIALS.name();
			logger.info(ErrorConstants.INVALID_LOGIN_CREDENTIALS.name() + ", Redirecting to " + loginPage);
			response.sendRedirect(loginPage);
			return;
		} 
		Person person = personList.getFirst();
		if(personList.isEmpty() || person == null || !person.getPassword().equals(DigestUtils.md5Hex(request.getParameter(UrlKeys.PASSWORD)))){
			loginError = ErrorConstants.INVALID_LOGIN_CREDENTIALS;
			String loginPage = WebPages.LOGIN_PAGE + "?" + UrlKeys.STATUS + "=" + ErrorConstants.INVALID_LOGIN_CREDENTIALS.name();
			logger.info(ErrorConstants.INVALID_LOGIN_CREDENTIALS.name() + ", Redirecting to " + loginPage);
			response.sendRedirect(loginPage);
			return;
		} else if(person.getActive() == false){
			out.println("<br /><p><h3>Account not activated yet</h3></p>");
		} else {
			
			logger.info("Logging in..");
			Session.doLogin(request.getSession(), person);
			
			String continueTo = request.getParameter(UrlKeys.CONTINUE);
			if(continueTo != null) {
				logger.info("Redirecting to continueTo " + continueTo);
				response.sendRedirect(continueTo);
				return;
			} else {
				logger.info("Redirecting to " + WebPages.HOME_PAGE);
				response.sendRedirect(WebPages.HOME_PAGE);
				return;
			}
		}
	}

	public static void main(String args[]) {
		System.out.println(DigestUtils.md5Hex("123"));
		System.out.println(System.getProperties());
		HashMap<String, String> conditions = new HashMap<String, String>();
		conditions.put(Person.F_EMAILID, "swatisinghi89@gmail.com");
		System.out.println(Person.getPersons(conditions));
	}
}
