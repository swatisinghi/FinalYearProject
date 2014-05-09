package com.pesse.sms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import com.pesse.sms.Department;
import com.pesse.sms.Designation;
import com.pesse.sms.Helper;
import com.pesse.sms.Person;
import com.pesse.sms.Section;
import com.pesse.sms.common.Config;
import com.pesse.sms.common.ConfigKeys;
import com.pesse.sms.common.UrlKeys;
import com.pesse.sms.common.WebPages;

public class RegisterServlet extends HttpServlet {

	private static Logger logger = Logger.getLogger(RegisterServlet.class);

	private static final long serialVersionUID = 1L;

	private static final String SERVER_CONTEXT = Config.getProperty(ConfigKeys.SERVER_CONTEXT);

	HashMap<String, String> condtitions = new HashMap<String, String>();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		logger.info("Inside register servlet");

		writeForm(out, request);

		if(request.getParameter(UrlKeys.STATUS) != null) {
			out.println("<tr><td colspan='2' class='cAlign'><span class='error'>" + ErrorConstants.valueOf(request.getParameter(UrlKeys.STATUS)).getMessage() + "</span></td></tr>");
		}

		Helper.writeEndHtml(out);
	}

	private void writeForm(PrintWriter out, HttpServletRequest request) {
		HashMap<String, String> htmlParams = new HashMap<String, String>();
		htmlParams.put("title", "Register page");

		Helper.writeStartHtml(out, htmlParams);
		
		out.println("<div class='backgroundGradient'");
		out.println("<div class='headerStrip headerGradient'><div class='headerContent'><h1><div class='headText'><img class='logoHead' src='" + SERVER_CONTEXT + "/img/pesse.png' /> PESSE SMS Alerts </div></h1></div></div>");
		
		out.println("<div class='mainWrapperReg clearfix'>");

		out.println("<br /><h2>Create new Account</h2><br />");
		
		out.println("<link rel='stylesheet' href='login.css' />");
		out.println("<link rel='stylesheet' href='jquery-ui.css' />");
		out.println("<link rel='stylesheet' href='register.css' />");
		out.println("<script type='text/javascript' src='" + Config.getProperty(ConfigKeys.SERVER_CONTEXT) + "/js/jquery.js'></script>");
		out.println("<script type='text/javascript' src='" + Config.getProperty(ConfigKeys.SERVER_CONTEXT) + "/js/jquery-ui.js'></script>");
		out.println("<script type='text/javascript' src='" + Config.getProperty(ConfigKeys.SERVER_CONTEXT) + "/js/register.js'></script>");
		
		out.println("<select onchange='showForm(this)'><option value='f'>FACULTY</option><option value='s'>" + Designation.STUDENT.name() + "</option></select>");
		
		out.println("<br /><form id='facultyForm' action='' method='post'>");
		out.println("<table class='tabHead'>");
		out.println("<tr><td>Institute Password: </td><td><input type='password' name='" + UrlKeys.INSTITUTE_PASSWORD + "' autocomplete='off' /> <br /></td></tr>");
		out.println("<tr><td>EmailId: </td><td><input type='text' name='" + UrlKeys.EMAIL + "' autocomplete='off' /> <br /></td></tr>");
		out.println("<tr><td>Password: </td><td><input type='password' name='" + UrlKeys.PASSWORD + "' autocomplete='off' /> <br /></td></tr>");
		out.println("<tr><td>Reenter Password: </td><td><input type='password' name='" + UrlKeys.PASSWORD_RETRY + "' autocomplete='off' /> <br /></td></tr>");
		out.println("<tr><td>First Name: </td><td><input type='text' name='" + UrlKeys.FIRST_NAME + "' autocomplete='off' /> <br /></td></tr>");
		out.println("<tr><td>Last Name: </td><td><input type='text' name='" + UrlKeys.LAST_NAME + "' autocomplete='off' /> <br /></td></tr>");
		out.println("<tr><td>Mobile No: </td><td><input type='text' name='" + UrlKeys.MOBILENO + "' autocomplete='off' /> <br /></td></tr>");
		out.println("<tr><td>Date of Birth: </td><td><input type='text' name='" + UrlKeys.DATE_OF_BIRTH + "' class='datePicker' autocomplete='off' /> <br /></td></tr>");
		out.println("<tr><td>Date of Join: </td><td><input type='text' name='" + UrlKeys.DATE_OF_JOINING + "' class='datePicker' autocomplete='off' /> <br /></td></tr>");
//		out.println("<tr><td>Address: </td><td><input type='text' name='" + UrlKeys.ADDRESS+ "' autocomplete='off' /> <br /></td></tr>");
//		out.println("<tr><td>City: </td><td><input type='text' name='" + UrlKeys.CITY+ "' autocomplete='off' /> <br /></td></tr>");
//		out.println("<tr><td>State: </td><td><input type='text' name='" + UrlKeys.STATE+ "' autocomplete='off' /> <br /></td></tr>");
//		out.println("<tr><td>Country: </td><td><input type='text' name='" + UrlKeys.COUNTRY+ "' autocomplete='off' /> <br /></td></tr>");
//		out.println("<tr><td>Pincode: </td><td><input type='text' name='" + UrlKeys.PINCODE+ "' autocomplete='off' /> <br /></td></tr>");
		out.println("<tr><td>Designation: </td><td><select name='" + UrlKeys.DESIGNATION + "'>");
		for(Designation desg : Designation.values()) {
			if(!desg.equals(Designation.STUDENT)) {
				out.println("<option value='" + desg.name() + "'>" + desg.name() + "</option>");
			}
		}
		out.println("</select> <br /></td></tr>");
		out.println("<tr><td>Department: </td><td><select name='" + UrlKeys.DEPARTMENT + "'>");
		for(Department dep : Department.values()) {
			out.println("<option value='" + dep.name() + "'>" + dep.name() + "</option>");
		}
		out.println("</select> <br /></td></tr>");

		out.println("<tr><td><input type='submit' /></td></tr>");
		out.println("</table>");
		out.println("</form>");	
		

		
		out.println("<form id='studentForm' action='' method='post' style='display: none;'>");
		out.println("<table class='tabHead'>");
		out.println("<tr><td>EmailId: </td><td><input type='text' name='" + UrlKeys.EMAIL + "' autocomplete='off' /> <br /></td></tr>");
		out.println("<tr><td>Password: </td><td><input type='password' name='" + UrlKeys.PASSWORD + "' autocomplete='off' /> <br /></td></tr>");
		out.println("<tr><td>Reenter Password: </td><td><input type='password' name='" + UrlKeys.PASSWORD_RETRY + "' autocomplete='off' /> <br /></td></tr>");
		out.println("<tr><td>USN: </td><td><input type='text' name='" + UrlKeys.USN + "' autocomplete='off' /> <br /></td></tr>");
		out.println("<tr><td>First Name: </td><td><input type='text' name='" + UrlKeys.FIRST_NAME + "' autocomplete='off' /> <br /></td></tr>");
		out.println("<tr><td>Last Name: </td><td><input type='text' name='" + UrlKeys.LAST_NAME + "' autocomplete='off' /> <br /></td></tr>");
		out.println("<tr><td>Mobile No : </td><td><input type='text' name='" + UrlKeys.MOBILENO + "' autocomplete='off' /> <br /></td></tr>");
		out.println("<tr><td>Guardian Mobile No : </td><td><input type='text' name='" + UrlKeys.PARENT_MOBILENO + "' autocomplete='off' /> <br /></td></tr>");
		out.println("<tr><td>Date of Birth : </td><td><input type='text' name='" + UrlKeys.DATE_OF_BIRTH + "' class='datePicker' autocomplete='off' /> <br /></td></tr>");
		out.println("<tr><td>Date of Join : </td><td><input type='text' name='" + UrlKeys.DATE_OF_JOINING+ "' class='datePicker' autocomplete='off' /> <br /></td></tr>");
//		out.println("<tr><td>Address : </td><td><input type='text' name='" + UrlKeys.ADDRESS+ "' autocomplete='off' /> <br /></td></tr>");
//		out.println("<tr><td>City : </td><td><input type='text' name='" + UrlKeys.CITY+ "' autocomplete='off' /> <br /></td></tr>");
//		out.println("<tr><td>State : </td><td><input type='text' name='" + UrlKeys.STATE+ "' autocomplete='off' /> <br /></td></tr>");
//		out.println("<tr><td>Country : </td><td><input type='text' name='" + UrlKeys.COUNTRY+ "' autocomplete='off' /> <br /></td></tr>");
//		out.println("<tr><td>Pincode : </td><td><input type='text' name='" + UrlKeys.PINCODE+ "' autocomplete='off' /> <br /></td></tr>");
		out.println("<input type='hidden' name='" + UrlKeys.DESIGNATION + "'value='STUDENT' /> <br /></td></tr>");
		out.println("<tr><td>Department: </td><td><select name='" + UrlKeys.DEPARTMENT + "'>");
		for(Department dep : Department.values()) {
			out.println("<option value='" + dep.name() + "'>" + dep.name() + "</option>");
		}
		out.println("</select> <br /></td></tr>");

		out.println("<tr><td>Semester: </td><td><input type='text' name='" + UrlKeys.SEMESTER + "' autocomplete='off' /> <br /></td></tr>");

		out.println("<tr><td>Section: </td><td><select name='" + UrlKeys.SECTION + "'>");
		for(Section dep : Section.values()) {
			out.println("<option value='" + dep.name() + "'>" + dep.name() + "</option>");
		}
		out.println("</select> <br /></td></tr>");

		out.println("<tr><td><input type='submit' /></td></tr>");
		out.println("</table>");
		out.println("</form>");	
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("Posted to register page");
		condtitions.put(Person.F_EMAILID, request.getParameter(UrlKeys.EMAIL));
		Person person = Person.getPerson(condtitions);

		logger.info("track 0");
		
		
		
		if(!request.getParameter(UrlKeys.DESIGNATION).equals(Designation.STUDENT.name()) && request.getParameter(UrlKeys.INSTITUTE_PASSWORD) == null) {
			String passwordInstitute = request.getParameter(UrlKeys.INSTITUTE_PASSWORD);
			if(!passwordInstitute.equals(Config.getProperty(ConfigKeys.INSTITUTE_PASSWORD))) {
				logger.info(ErrorConstants.INVALID_INSTITUTE_PASSWORD.getMessage());
				response.sendRedirect(WebPages.REGISTER_PAGE + "?" + UrlKeys.STATUS + "=" + ErrorConstants.INVALID_INSTITUTE_PASSWORD.name());
				return;
			}
		}
		if(person != null) {
			logger.info(ErrorConstants.USERNAME_ALREADY_TAKEN.getMessage());
			response.sendRedirect(WebPages.REGISTER_PAGE + "?" + UrlKeys.STATUS + "=" + ErrorConstants.USERNAME_ALREADY_TAKEN.name());
			return;
		}
		logger.info("register Track 1");

		String password = request.getParameter(UrlKeys.PASSWORD);
		String passwordRetry = request.getParameter(UrlKeys.PASSWORD_RETRY);

		if(password == null || !passwordRetry.equals(password)) {
			logger.info(ErrorConstants.PASSWORD_MISMATCH.getMessage());
			response.sendRedirect(WebPages.REGISTER_PAGE + "?" + UrlKeys.STATUS + "=" + ErrorConstants.PASSWORD_MISMATCH.name());
			return;
		}
		
		String designation = request.getParameter(UrlKeys.DESIGNATION);
		String mobileNo = request.getParameter(UrlKeys.MOBILENO);
		String guardianMobileNo = request.getParameter(UrlKeys.PARENT_MOBILENO);
		logger.info("mobile no" + mobileNo);
		logger.info("GUARDIAN mobile no" + guardianMobileNo);
		if(designation.equalsIgnoreCase(Designation.STUDENT.name()) && !mobileNo.equalsIgnoreCase(guardianMobileNo)) {
			logger.info(ErrorConstants.INVALID_GUARDIAN_MOBILENO.getMessage());
			response.sendRedirect(WebPages.REGISTER_PAGE + "?" + UrlKeys.STATUS + "=" + ErrorConstants.INVALID_GUARDIAN_MOBILENO.name());
			return;
		}
		logger.info("register Track 2");
		DateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
		Date dob;
		try {
			dob = new Date(dfDate.parse(request.getParameter(UrlKeys.DATE_OF_BIRTH)).getTime());
		} catch (ParseException e) {
			logger.error("Cannot parse date", e);
			logger.info(ErrorConstants.INVALID_DATE.getMessage());
			response.sendRedirect(WebPages.REGISTER_PAGE + "?" + UrlKeys.STATUS + "=" + ErrorConstants.INVALID_DATE.name());
			return;
		}
		logger.info(" register Track 3");
		Date doj;
		try {
			doj = new Date(dfDate.parse(request.getParameter(UrlKeys.DATE_OF_JOINING)).getTime());
		} catch (ParseException e) {
			logger.error("Cannot parse date", e);
			logger.info(ErrorConstants.INVALID_DATE.getMessage());
			response.sendRedirect(WebPages.REGISTER_PAGE + "?" + UrlKeys.STATUS + "=" + ErrorConstants.INVALID_DATE.name());
			return;
		}

		logger.info("register Track 4");
		Integer sem = null;
		try {
			String semester = request.getParameter(UrlKeys.SEMESTER);
			if(semester != null) {
				sem = new Integer(Integer.parseInt(semester));
			}
		} catch (Exception e) {
			logger.error("Cannot parse integer", e);
			logger.info(ErrorConstants.INVALID_SEMESTER.getMessage());
			response.sendRedirect(WebPages.REGISTER_PAGE + "?" + UrlKeys.STATUS + "=" + ErrorConstants.INVALID_SEMESTER.name());
			return;
		}


		boolean active = true;
		String desg = request.getParameter(UrlKeys.DESIGNATION);
		logger.info(desg);
		if(desg.equals(Designation.STUDENT.name())) {
			active = false;
		}
		logger.info(active);
		logger.info("register Track 5");

		person = new Person(
				request.getParameter(UrlKeys.EMAIL),
				request.getParameter(UrlKeys.USN),
				request.getParameter(UrlKeys.FIRST_NAME),
				request.getParameter(UrlKeys.LAST_NAME),
				request.getParameter(UrlKeys.MOBILENO),
				request.getParameter(UrlKeys.PARENT_MOBILENO),
				request.getParameter(UrlKeys.DESIGNATION),
				doj,
				dob,
				request.getParameter(UrlKeys.ADDRESS),
				request.getParameter(UrlKeys.CITY),
				request.getParameter(UrlKeys.STATE),
				request.getParameter(UrlKeys.COUNTRY),
				request.getParameter(UrlKeys.PINCODE),
				request.getParameter(UrlKeys.DEPARTMENT),
				request.getParameter(UrlKeys.SECTION),
				sem,
				request.getParameter(UrlKeys.PASSWORD),
				active
		);
		logger.info(person.getEmailId());
		logger.info(" register Track 6");
		ErrorConstants validationError = person.validateFormat();
		if(!validationError.equals(ErrorConstants.SUCCESS)) {
			logger.info(validationError.getMessage());
			response.sendRedirect(WebPages.REGISTER_PAGE + "?" + UrlKeys.STATUS + "=" + validationError.name());
			return;
		}
		logger.info("Track 7");

		person.setPassword(DigestUtils.md5Hex(person.getPassword()));
		int rows = person.insertPerson();
		if(rows != 1) {
			logger.info(ErrorConstants.SYSTEM_ERROR.getMessage());
			response.sendRedirect(WebPages.REGISTER_PAGE + "?" + UrlKeys.STATUS + "=" + ErrorConstants.SYSTEM_ERROR.name());
			return;
		}
		logger.info(ErrorConstants.SUCCESS.getMessage());
		response.sendRedirect(WebPages.REGISTER_PAGE + "?" + UrlKeys.STATUS + "=" + ErrorConstants.SUCCESS.name());
	}
	public static void main(String args[]) {
		boolean active;
		if(("STUDENT").equals(Designation.STUDENT)) active = false;
		active = true;
		System.out.println(active);
	}


}
