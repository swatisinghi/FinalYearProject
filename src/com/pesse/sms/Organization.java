package com.pesse.sms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import com.pesse.sms.Organization;
import com.pesse.sms.db.DBConnection;

public class Organization {

	private static Logger logger = Logger.getLogger(Organization.class);

	private String organizationId;
	private String password;
	private boolean active;

	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean isActive() {
		return active;
	}
	public Organization(String organizationId, String password, boolean active) {
		super();
		this.organizationId = organizationId;
		this.password = password;
		this.active = active;
	}

	public static Organization getOrganization(String organizationId) {

		Connection conn = DBConnection.getConnection();
		ResultSet rs = null;
		Statement st = null;

		Organization org = null;

		try {
			st = conn.createStatement();
			String query = "SELECT * FROM Organizations WHERE id = '" + StringEscapeUtils.escapeSql(organizationId) + "'";
			logger.info("query: " + query);

			rs = st.executeQuery(query);

			if(rs.next()) {

				logger.info("Found record");
				org = new Organization(
						rs.getString("id"), 
						rs.getString("password"), 
						rs.getBoolean("active")
				);
			} else {
				logger.info("No record found");
			}

		} catch (SQLException e) {
			logger.error("getOrganization exception", e);
		} finally {
			try {
				conn.close();
			} catch(Exception ignored) { }
		}

		return org;
	}

	public int insertIntoDb() {

		Connection conn = DBConnection.getConnection();
		Statement st = null;

		int rows = 0;

		try {
			st = conn.createStatement();
			String query = "INSERT INTO Organizations (`id`, `password`, `active`) VALUES ('"
				+ this.organizationId + "', '"
				+ this.password + "', '"
				+ this.active + ")";
			logger.info("query: " + query);

			rows = st.executeUpdate(query);
			logger.info(rows + " rows affected");

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("insertIntoDb exception", e);
		} finally {
			try {
				conn.close();
			} catch(Exception ignored) { }
		}

		return rows;
	}

	public int updateInDb() {

		Connection conn = DBConnection.getConnection();
		Statement st = null;

		int rows = 0;

		try {
			st = conn.createStatement();
			String query = "UPDATE Organizations SET "
				+ "`password` = '" + this.password + "', "
				+ "`active` = " + this.active
				+ " WHERE "
				+ "`id` = '" + this.organizationId + "'";
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
}

