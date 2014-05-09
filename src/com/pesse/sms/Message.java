package com.pesse.sms;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.pesse.sms.db.DBConnection;
import com.pesse.sms.servlet.ErrorConstants;

public class Message {

	private static Logger logger = Logger.getLogger(Message.class);
	
	public static String F_ID = "`ID`";
	public static String F_TO = "`TO`";
	public static String F_FROM = "`FROM`";
	public static String F_DATE = "`DATE`";
	public static String F_SUBJECT = "`SUBJECT`";
	public static String F_MESSAGE = "`MESSAGE`";
	

	String id;
	String to;
	String from;
	Date date;
	String subject;
	String msg;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return msg;
	}
	public void setMessage(String message) {
		this.msg = message;
	}
	public Message(String id, String to, String from, Date date, String subject,
			String message) {
		super();
		this.id = id;
		this.to = to;
		this.from = from;
		this.date = date;
		this.subject = subject;
		this.msg = message;
	}
	
	public static LinkedList<Message> getMessageToList(HashMap<String, String> conditions) {
		return getMessageList(Helper.getSqlQuery("MESSAGE", conditions));
	}
	
	public static LinkedList<Message> getMessageFromList(HashMap<String, String> conditions) {
		return getMessageList(Helper.getSqlQuery("MESSAGE", conditions));
	}
	
	public static LinkedList<Message> getMessageToList(String emailId) {
		return getMessageList("SELECT * FROM MESSAGE WHERE `TO` = '" + emailId + "' ORDER BY `DATE` DESC");
	}
	
	public static LinkedList<Message> getMessageFromList(String emailId) {
		return getMessageList("SELECT * FROM MESSAGE WHERE `FROM` = '" + emailId + "' ORDER BY `DATE` DESC");
	}
	
	public int validateMessage(Message msg) {
		
		Person fromPerson = Person.getPerson(Person.F_EMAILID, msg.from);
		Person toPerson = Person.getPerson(Person.F_EMAILID, msg.to);
		if(fromPerson.getDesignation().equals(Designation.STUDENT.name()) && !toPerson.getDesignation().equals(Designation.STUDENT.name())) {
			return 0;
		}
		return 1;
		
	}
	public ErrorConstants insertMessage() {
		int valid = validateMessage(this);
		if(valid == 0) {
			return ErrorConstants.INVALID_SENDER;
		}
		Connection conn = DBConnection.getConnection();
		Statement st = null;
		int rows = 0;
		
		try {
			st = conn.createStatement();
			String query = "INSERT INTO MESSAGE (`ID`, `TO`,`FROM`,`DATE`,`SUBJECT`,`MESSAGE`) VALUES ('"
				+ StringEscapeUtils.escapeSql(this.id) + "','"
				+ StringEscapeUtils.escapeSql(this.to) + "','"
				+ StringEscapeUtils.escapeSql(this.from) + "','"
				+ this.date + "','"
				+ StringEscapeUtils.escapeSql(this.subject) + "','"
				+ StringEscapeUtils.escapeSql(this.msg) + "')";

			logger.info("query: " + query);

			rows = st.executeUpdate(query);
			logger.info(rows + " rows affected");

		} catch (SQLException e) {
			logger.error("insertIntoDb exception", e);
		} finally {
			try {
				conn.close();
			} catch(Exception ignored) { }
		}
		if(rows != 1) {
			return ErrorConstants.SYSTEM_ERROR;
		}
		return ErrorConstants.MESSAGE_SENT;
			
	}
	
	public static LinkedList<Message> getMessageList(String query) {

		Connection conn = DBConnection.getConnection();
		ResultSet rs = null;
		Statement st = null;

		LinkedList<Message> messageList = new LinkedList<Message>();
		Message msg = null;
		try {
			st = conn.createStatement();
			logger.info("query: " + query);

			rs = st.executeQuery(query);
			if(rs.next()) {
				do {
						logger.info("Found record");
						msg = new Message(
								rs.getString("ID"),
								rs.getString("TO"), 
								rs.getString("FROM"),
								rs.getDate("DATE"),
								rs.getString("SUBJECT"),
								rs.getString("MESSAGE")
						);
						messageList.add(msg);
				}	while(rs.next());
			} else {
				logger.info("No record found");
			}

		} catch (SQLException e) {
			logger.error("getMessage exception", e);
		} finally {
			try {
				conn.close();
			} catch(Exception ignored) { }
		}
		return messageList;
	}

}
