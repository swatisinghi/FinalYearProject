package com.pesse.sms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.pesse.sms.db.DBConnection;

public class Event {

	private static Logger logger = Logger.getLogger(Event.class);

	private Integer eventId;
	private String eventDesc;
	private String dept;

	public Event(Integer eventId, String eventDesc, String dept) {
		super();
		this.eventId = eventId;
		this.eventDesc = eventDesc;
		this.dept = dept;
	}

	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	public String getEventDesc() {
		return eventDesc;
	}
	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}

	public Event getEvent(int eventId) {
		Connection conn = DBConnection.getConnection();
		ResultSet rs = null;
		Statement st = null;

		Event event = null;

		try {
			st = conn.createStatement();
			String query = "SELECT * FROM EVENT WHERE EVENTID = '" + eventId + "'";
			logger.info("query: " + query);

			rs = st.executeQuery(query);
			if(rs.next()) {

				logger.info("Found record");
				event = new Event(
						rs.getInt("eventId"),
						rs.getString("eventDesc"),
						rs.getString("dept")
				);
			} else {
				logger.info("No record found");
			}

		} catch (SQLException e) {
			logger.error("getAppraisal exception", e);
		} finally {
			try {
				conn.close();
			} catch(Exception ignored) { }
		}

		return event;
	}

	public int insertEvent() {
		Connection conn = DBConnection.getConnection();
		Statement st = null;
		int rows = 0;

		try {
			st = conn.createStatement();
			String query = "INSERT INTO EVENT (`EVENTDESC`, `DEPT`) VALUES ('"
				+ StringEscapeUtils.escapeSql(this.eventDesc) + "', '"
				+ StringEscapeUtils.escapeSql(this.dept) +  "')";

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
		return rows;
	}
	public int updateEvent() {	 
		Connection conn = DBConnection.getConnection();
		Statement st = null;
		int rows = 0;

		try {
			st = conn.createStatement();
			String query = "UPDATE EVENT SET "
				+ "`eventDesc` = '" + StringEscapeUtils.escapeSql(this.eventDesc) + "', "
				+ "`dept` = '" + StringEscapeUtils.escapeSql(this.dept)+ "', "
				+ " WHERE "
				+ "`eventId` = '" + this.eventId + "'";
			logger.info("query: " + query);

			rows = st.executeUpdate(query);
			logger.info(rows + " rows affected");

		} catch (SQLException e) {
			logger.error("updateInDb exception", e);
		} finally {
			try {
				conn.close();
			} catch(Exception ignored) { }
		}

		return rows;
	}
}
