<%@page import="java.util.Calendar"%>
<%@ page import="com.pesse.sms.*"%>
<%@ page import="com.pesse.sms.common.*"%>
<%@ page import="com.pesse.sms.db.*"%>
<%@ page import="org.apache.log4j.*"%>
<%@ page import="java.sql.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="java.util.HashMap"%>
<%@page import="com.pesse.sms.servlet.LoginServlet"%>


<%!HttpServletRequest req = null;%>

<%
	req = request;
%>
<%
	Logger logger = Logger.getLogger(getClass());
	logger.info("Inside home servlet. Checking whether logged in");
	if (Session.isNotLoggedIn(request, response)) {
		return;
	}
	HashMap<String, String> conditions = new HashMap<String, String>();
	conditions.put(Person.F_EMAILID,
	String.valueOf(session.getAttribute(Session.EMAIL_ID)));
	logger.info(session.getAttribute(Session.EMAIL_ID));
	
	Person per = Person.getPersons(conditions).getFirst();
	logger.info("Logged in");
%>

<%!boolean checkParam(String key, String value) {
		String queryVal = req.getParameter(key);
		if(queryVal == null) {
			return true;
		}
		if (queryVal == null || !queryVal.equals(value)) {
			return false;
		}
		return true;
	}

%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<%@page import="com.pesse.sms.servlet.ErrorConstants"%><html
	xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<title>Home page</title>
<script type='text/javascript' src='js/jquery.js'></script>
<script type='text/javascript' src='js/jquery-ui.js'></script>
<script type='text/javascript' src='js/register.js'></script>
<link rel='stylesheet' href='jquery-ui.css' />
<link rel="stylesheet" href="main.css" />
</head>
<body>
	<div class="mainWrapper">
	<div class='headerStrip headerGradient'><div class='greeting floatLeft'>Hello, "<%=per.getFirstName()%>"</div>.
			<div class="rightLinks floatRight">
			<br/>
				<ul>
					<li><a href="#help">Help</a></li>
					<li><a href="#account">Account</a></li>
					<li><a href="<%=WebPages.LOGOUT_PAGE%>">Logout</a></li>
				</ul>
			</div>
		
		<div class='headerContent'><h1><div class='headText'><img class='logoHead' src='img/pesse.png' /> PESSE SMS Alerts </div></h1></div>.
		</div>
		<div class="mainContent clearfix">
			<div class="leftNav">
				<ul>
					<li><a href="?rc=qs">Quick SMS</a></li>
					<li><a href="?rc=gs">Group SMS</a></li>
					<li><a href="?rc=ab">Address Book</a></li>
					<li><a href="?rc=ib">Inbox</a></li>
					<li><a href="?rc=sm">Sent Messages</a></li>
					<% if(!per.getDesignation().equals(Designation.STUDENT.name())) { %>
					<li><a href="?rc=as">Activate Students</a></li>
					<% } %>
				</ul>
			</div>
			<div id="rightContent" class="rightContent">
				<%
					if (checkParam("rc", "qs") || checkParam("rc", null)) {
						String to = request.getParameter("to");
						if(to == null) to = "";
				%>
						<form action="" method="post" onsubmit="return sendProcessing();">
							<p>
								<span id="sendErrorMsg"></span>
								<input class="box-shadow" type="hidden" name="rc" value="qs" /> Email Ids:<br />
								<textarea rows="2" cols="40" name="<%=UrlKeys.TO_EMAIL%>" id="email"><%= to %></textarea>
								<br /> Message: (Word Limit: 140)<br />
								<textarea class="box-shadow" rows="6" cols="40" name="<%=UrlKeys.MESSAGE%>" id="msg"></textarea>
								<br /><input class='button' type="submit" value="Send" /> <span id="sendProcessingMsg" style="display:none">Please wait...</span>
							</p>
						</form>
				<%
						logger.info("To email: " + request.getParameter(UrlKeys.TO_EMAIL));
						if(request.getParameter(UrlKeys.TO_EMAIL) != null && request.getParameter(UrlKeys.MESSAGE) != null) {
							logger.info("Attempting quick sms");
							LinkedList<Person> personList = Person.getMobileList(request.getParameter(UrlKeys.TO_EMAIL));
							logger.info("person send list " + personList.size());
							DateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
							Date currentDate = null;
							for(Person person : personList) {
								logger.info("TO session EMAIL -" + per.getEmailId());
								Date sqldate = new Date((Calendar.getInstance().getTime()).getTime());
								Message message = new Message (Helper.getRandomString(11),
												person.getEmailId(),
												per.getEmailId(),
												sqldate,
												request.getParameter(UrlKeys.MESSAGE),
												request.getParameter(UrlKeys.MESSAGE));
								if(message.validateMessage(message) == 0) {
									out.println("<br />You cannot send a message to an Employee...");
								} else {
									message.insertMessage();
									logger.info(ErrorConstants.SUCCESS.getMessage());
									Way2sms.sendSms(person.getMobileNo(), request.getParameter(UrlKeys.MESSAGE));
									out.println("<br />Message sent...");
								}
							}
						}
				} else if (checkParam("rc", "gs")) { 
					logger.info("Attempting group sms");
				%>
					<form action="" method="post"><br />
					<p>
						<select name="<%=UrlKeys.DEPARTMENT %>"id="<%=UrlKeys.DEPARTMENT %>">
							<% 
							out.println("<option selected='selected' disabled='disabled' value=''>" + Person.F_DEPT + "</option>");
							for(Department dep : Department.values()) {
								out.println("<option value='" + dep.name() + "'>" + dep.name() + "</option>");
							}
							%>
						</select>
						<select name="<%=UrlKeys.SEMESTER %>">
							<% 
							int sem = 8;
							out.println("<option selected='selected' disabled='disabled' value=''>" + Person.F_SEMESTER + "</option>"); 
							for(int s = 1; s <= sem; s++) { 
 								out.println("<option value='" + s + "'>" + s + "</option>"); 
							}
							%>
						</select> 
						<select name="<%=UrlKeys.SECTION %>">
							<% 
							out.println("<option selected='selected' disabled='disabled' value=''>" + Person.F_SECTION + "</option>");
							for(Section sec : Section.values()) {
								out.println("<option value='" + sec.name() + "'>" + sec.name() + "</option>");
							}
							%>
						</select>
						<% if(!per.getDesignation().equals(Designation.STUDENT.name())) { %>
							<select name="<%=UrlKeys.DESIGNATION %>">
								<% 
								out.println("<option selected='selected' disabled='disabled' value=''>" + Person.F_DESIGNATION + "</option>");
								for(Designation des : Designation.values()) {
									out.println("<option value='" + des.name() + "'>" + des.name() + "</option>");
								}
								%>
							</select>
						<% } %>
					</p>
					<p>
						<input type="hidden" name="rc" value="gs" /> 
						<br /> Message: (Word Limit: 140)<br />
						<textarea rows="6" cols="40" name="<%=UrlKeys.MESSAGE%>"></textarea>
						<br /> <input class="button" type="submit" value="Send" />
					</p>
					</form>
					<% 
						logger.info("To email: " + request.getParameter(UrlKeys.DEPARTMENT) + request.getParameter(UrlKeys.SEMESTER) + 
								request.getParameter(UrlKeys.SECTION) + request.getParameter(UrlKeys.DESIGNATION));
 						
						
						if(request.getParameter(UrlKeys.MESSAGE) != null) {
							conditions.clear();
	 						
							conditions.put(Person.F_DEPT, request.getParameter(UrlKeys.DEPARTMENT));
							conditions.put(Person.F_SEMESTER, request.getParameter(UrlKeys.SEMESTER));
							conditions.put(Person.F_SECTION, request.getParameter(UrlKeys.SECTION));
							conditions.put(Person.F_DESIGNATION, request.getParameter(UrlKeys.DESIGNATION));
							
							logger.info("Conditions " + conditions);
							LinkedList<Person> personList = Person.getPersons(conditions);
							logger.info("person send list " + personList.toString());
							DateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
							Date currentDate = null;
							ErrorConstants error = null;
							for(Person person : personList) {
								logger.info("from session EMAIL -" + per.getEmailId());
								Date sqldate = new Date((Calendar.getInstance().getTime()).getTime());
								Message message = new Message (Helper.getRandomString(11),
												person.getEmailId(),
												per.getEmailId(),
												sqldate,
												request.getParameter(UrlKeys.MESSAGE),
												request.getParameter(UrlKeys.MESSAGE));
								
								error = message.insertMessage();
								logger.info(ErrorConstants.SUCCESS.getMessage());
								Way2sms.sendSms(person.getMobileNo(), request.getParameter(UrlKeys.MESSAGE));
							}
							out.println("<br />Message sent...");
						}
					} else if (checkParam("rc", "ab")) {
					%>
					<div class="addTab">
						<ul>
							<li><a href="?rc=ab&v=eab">Employee</a></li>
							<li><a href="?rc=ab&v=sab">Student</a></li>
						</ul>
					</div>
					<%
					if(checkParam("v", "eab") || checkParam("v", null)) {
						LinkedList<Person> employeeList = Person.getFacultyList();
						
						if(employeeList == null || employeeList.isEmpty()) {
							out.println("No record found");
						} else {
							out.println("<table class='tabHead'><thead><td><div class='gvName'>FirtsName</td><td><div class='gvName'>" +
								"LastName</td><td><div class='gvEmailId'>EmailId</td>" +
								"<td><div class='gvDept'>Dept</td><td><div class='gvDesg'>Designation</td></thead></tbody>");
							for(Person p : employeeList) {
								HashMap<String, String> params = new HashMap<String, String>(3);
								params.put("rc", "qs");
								params.put("to", p.getEmailId());
								
								out.print("<tr><td>");
								out.print("<div class='gvName'><div class='inf'><b>" + p.getFirstName() + "</b></div></div>");
								out.print("</td><td>");
								out.print("<div class='gvName'><div class='inf'>" + p.getLastName() + "</div></div>");
								out.print("</td><td>");
								out.print("<div class='gvEmailId'><div class='inf'><a href='?" + Helper.httpBuildQuery(params) + "'>" + p.getEmailId() + "</a></div></div>");
								out.print("</td><td>");
								out.print("<div class='gvDept'><div class='inf'>" + p.getDept() + "</div></div>");
								out.print("</td><td>");
								out.print("<div class='gvDesg'><div class='inf'>" + p.getDesignation() + "</div></div>");
								out.print("</td></tr>");
							}
							out.println("</tbody></table>");
						}
					} else if (checkParam("v", "sab")) {
						conditions.clear();
						conditions.put(Person.F_DESIGNATION, Designation.STUDENT.name());;
						LinkedList<Person> studentList = Person.getPersons(conditions);
						if(studentList == null || studentList.isEmpty()) {
							out.println("No record found");
						} else {
							out.println("<table class='tabHead'><thead><td><div class='gvName'>FirtsName</td><td><div class='gvName'>" +
							"LastName</td><td><div class='gvEmailId'>EmailId</td><td><div class='gvUsn'>" +
							"USN</td><td><div class='gvDept'>Dept</td><td><div class='gvSem'>Sem</td></thead><tbody>");
							for(Person p : studentList) {
								HashMap<String, String> params = new HashMap<String, String>(3);
								params.put("rc", "qs");
								params.put("to", p.getEmailId());
								out.print("<tr><td>");
								out.print("<div class='gvName'><div class='inf'><b>" + p.getFirstName() + "</b></div></div>");
								out.print("</td><td>");
								out.print("<div class='gvName'><div class='inf'>" + p.getLastName() + "</div></div>");
								out.print("</td><td>");
								out.print("<div class='gvEmailId'><div class='inf'><a href='?" + Helper.httpBuildQuery(params) + "'>" + p.getEmailId() + "</a></div></div>");
								out.print("</td><td>");
								out.print("<div class='gvUsn'><div class='inf'>" + p.getusn() + "</div></div>");
								out.print("</td><td>");
								out.print("<div class='gvDept'><div class='inf'>" + p.getDept() + "</div></div>");
								out.print("</td><td>");
								out.print("<div class='gvSem'><div class='inf'>" + p.getSemester() + "</div></div>");
								out.print("</td></tr>");
							}
							out.println("</tbody></table>");
						}
					}
				} else if(checkParam("rc", "ib")) {
					conditions.clear();
					conditions.put(Message.F_TO, per.getEmailId());
					LinkedList<Message> messageReceived = Message.getMessageToList(per.getEmailId());
					out.println("<div class='accordion demo'>");
					for(Message m : messageReceived) {
						out.println("<h3><a href='#'>From:  " + m.getFrom() + "<div class='floatRight>'" + m.getDate() + "</div></a></h3>");
						out.println("<div><p>");
						out.println(m.getMessage() + "<br />");
						out.println("</p></div>");
					}
					out.println("</div>");
				} else if(checkParam("rc", "sm")) {
					conditions.clear();
					conditions.put(Message.F_FROM, per.getEmailId());
					LinkedList<Message> messageReceived = Message.getMessageFromList(per.getEmailId());
					out.println("<div class='accordion demo'>");
					for(Message m : messageReceived) {
						out.println("<h3><a href='#'>To:  " + m.getTo() + "<div class='floatRight>'" + m.getDate() + "</div></a></h3>");
						out.println("<div><p>");
						out.println(m.getMessage() + "<br />");
						out.println("</p></div>");
					}
					out.println("</div>");
				} else if(checkParam("rc", "as") && !per.getDesignation().equals(Designation.STUDENT.name())) {
					conditions.clear();
					conditions.put(Person.F_ACTIVE,"false");
					conditions.put(Person.F_DESIGNATION, Designation.STUDENT.name());
					out.println("<table class='tabHead'><thead><td><div class='gvActive'>FirstName</td><td><div class='gActive'>" +
							"LastName</td><td><div class='gvActive'>USN</td><td><div class='gvActive'>Active</td></thead>");
					LinkedList<Person> personList = Person.getPersons(conditions);
					for(Person person : personList) {
						logger.info("I am in activate  " + person.getFirstName() + person.getLastName() + person.getusn());
						out.println("<tbody><form action='' method='post'");
						out.println("<input type='hidden' name='" + UrlKeys.EMAIL + "' value='" + person.getEmailId() + "' />");
						out.println("<tr><td><label><div class='gvActive'>" + person.getFirstName() + "<input type='hidden' name='" + UrlKeys.FIRST_NAME + "' value='" + person.getFirstName() + "'></div></label></td>");
						out.println("<td><label><div class='gvActive'>" + person.getLastName() + "<input type='hidden' name='" + UrlKeys.LAST_NAME + "' value='" + person.getLastName() + "'></div></label></td>");
						out.println("<td><label><div class='gvActive'>" + person.getusn() + "<input type='hidden' name='" + UrlKeys.USN + "' value='" + person.getusn() + "'></div></label></td>");
						out.println("<td><input type='submit' value='Activate'/></td>");
						out.println("</form>");
					}
					out.println("</div></tbody></table>");
					String emailId = request.getParameter(UrlKeys.EMAIL);
					if(emailId != null) {
						logger.info("activate me mum hai");
						Person person = Person.getPerson(Person.F_EMAILID, emailId);
						logger.info(person);
						person.setActive(true);
						int rows = person.updatePerson();
						if(rows == 1) {
							out.println("Account is now activated");
						} else {
							out.println("Sorry, there was some error!");
						}
					}
				}
				%>
			</div>
		</div>
	</div>
	<div class="footer">
		<span>&copy; 2011 PESSE</span>
		- <a href="">Terms</a>
		- <a href="">PrivacyPolicy</a>
</div>
</body>
</html>
