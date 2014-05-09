<%@page import="java.util.Calendar"%>
<%@ page import="com.pesse.sms.*"%>
<%@ page import="com.pesse.sms.common.*"%>
<%@ page import="com.pesse.sms.db.*"%>
<%@ page import="org.apache.log4j.*,org.apache.commons.codec.digest.DigestUtils"%>
<%@ page import="java.sql.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.util.HashMap" %>
<%@page import="com.pesse.sms.servlet.*"%>

<%

	Logger logger = Logger.getLogger(getClass());
	ErrorConstants loginError = ErrorConstants.SYSTEM_ERROR;

	if(Session.isValidSession(request.getSession())) {
	logger.info("Already logged in. Redirecting to home page...");
	response.sendRedirect(WebPages.HOME_PAGE);
	return;
	}
	
	String user = request.getParameter(UrlKeys.EMAIL);
	
	if(user != null) {
		HashMap<String, String> hm = new HashMap<String, String>();
		logger.info("Posted to Login page");
		logger.info(user);
		hm.put(Person.F_EMAILID, request.getParameter(UrlKeys.EMAIL));
		LinkedList<Person> personList = Person.getPersons(hm);
		
		Person person = personList.getFirst();
		
		if(person == null || !person.getPassword().equals(DigestUtils.md5Hex(request.getParameter(UrlKeys.PASSWORD)))){
			loginError = ErrorConstants.INVALID_LOGIN_CREDENTIALS;		
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
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<%@page import="com.pesse.sms.servlet.ErrorConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<title>Home page</title>
<link rel="stylesheet" href="login.css" />
</head>
<body>
	<div class='mainWrapper'>
		<div class='header'>
			<div class='headerStrip headerGradient'>
				<div class='headerContent'>
					<div><img class='logoHead' src='img/pesse.png' /> <span class='headText'>PESSE SMS Alerts </span></div>
				</div>
			</div>
		</div>
		<div class='contentWrapper clearfix'>
			<div class='leftContent'>
				<table>
					<tr>
						<td><img src='img/chat.png' class='lFloat' /></td>
						<td>
							<div class='imgText'>
								<ul>
									<li>hi</li>
									<li>hi</li>
									<li>hi</li>
									<li>hi</li>
									<li>hi</li>
								</ul>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div class='loginWrapper'><div class='loginForm'>
				<div class='center loginText'><h1>Login</h1></div>
					<form action='' method='post'>
						<table>
							<tr><td class='right'><label for='" + UrlKeys.EMAIL + "'>EmailId: </label></td><td><input type='text' id='" + UrlKeys.EMAIL + "'name='" + UrlKeys.EMAIL + "' /> </td></tr>
							<tr><td class='right'><label for='" + UrlKeys.PASSWORD + "'>Password: </label></td><td><input type='password' id='" + UrlKeys.PASSWORD + "'name='" + UrlKeys.PASSWORD + "' /> </td></tr>
							<tr><td colspan='2'><div class='center'><input type='submit' value='Login'/></div></td></tr>
							<tr><td colspan='2'><div class='createAccLink tcenter'><a href='" + WebPages.REGISTER_PAGE + "'><div class='loginAcctText'> Create an account</div></a></div></td></tr>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>