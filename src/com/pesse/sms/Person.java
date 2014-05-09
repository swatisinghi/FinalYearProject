package com.pesse.sms;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.pesse.sms.common.Constraints;
import com.pesse.sms.common.RegexCheck;
import com.pesse.sms.db.DBConnection;
import com.pesse.sms.servlet.ErrorConstants;

public class Person {

	private static Logger logger = Logger.getLogger(Person.class);

	public static String F_EMAILID = "EMAILID";
	public static String F_USN = "USN";
	public static String F_DATEOFBIRTH = "BIRTHDATE";
	public static String F_DESIGNATION = "DESIGNATION";
	public static String F_DEPT = "DEPT";
	public static String F_SECTION = "SECTION";
	public static String F_SEMESTER = "SEMESTER";
	public static String F_ACTIVE = "ISACTIVE";


	private String emailId;
	private String usn;
	private String firstName;
	private String lastName;
	private String mobileNo;
	private String parentMobileNo;
	private String designation;
	private Date dateOfJoin;
	private Date birthDate;
	private String address;
	private String city;
	private String state;
	private String country;
	private String pincode;
	private String dept;
	private String section;
	private Integer semester;
	private String password;
	private boolean isActive;



	public Person(String emailId, String usn, String firstName, String lastName,
			String mobileNo, String parentMobileNo, String designation, Date dateOfJoin, Date birthDate, String address,
			String city, String state, String country, String pincode,
			String dept, String section, Integer semester,
			String password, boolean active) {
		this.emailId = emailId;
		this.usn = usn;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNo = mobileNo;
		this.parentMobileNo = parentMobileNo;
		this.designation = designation;
		this.dateOfJoin = dateOfJoin;
		this.birthDate = birthDate;
		this.address = address;
		this.city = city;
		this.state = state;
		this.country = country;
		this.pincode = pincode;
		this.dept = dept;
		this.section = section;
		this.semester = semester;
		this.password = password;
		this.isActive = active;
	}

	public String getusn() {
		return usn;
	}
	public void setusn(String usn) {
		this.usn = usn;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public void setParentMobileNo(String parentMobileNo) {
		this.parentMobileNo = parentMobileNo;
	}
	public String getParentMobileNo() {
		return parentMobileNo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Date getDateOfJoin() {
		return dateOfJoin;
	}
	public void setDateOfJoin(Date dateOfJoin) {
		this.dateOfJoin = dateOfJoin;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDesignation() {
		return designation;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setActive(boolean active) {
		this.isActive = active;
	}
	public boolean getActive() {
		return isActive;
	}
	public ErrorConstants validateFormat() {

		logger.info("Track 1");
		if((designation.equals(Designation.STUDENT) && ((usn == null) || !usn.matches(RegexCheck.USN)))){
			return ErrorConstants.INVALID_USN;
		}
		if(password == null || !password.matches(RegexCheck.PASSWORD) || password.length() < Constraints.MIN_PASSWORD_LENGTH 
				|| password.length() > Constraints.MAX_PASSWORD_LENGTH) {
			return ErrorConstants.INVALID_PASSWORD;
		}


		logger.info("Track 2");
		if(emailId == null || !emailId.matches(RegexCheck.EMAIL_ID)) { 
			logger.info("Invalid email");
			return ErrorConstants.INVALID_EMAILID;
		}

		if(firstName == null || firstName.length() > Constraints.MAX_STRING_LENGTH) { 
			return ErrorConstants.STRING_FIELD_TOO_LONG;
		}

		logger.info("Track 3");
		if(lastName == null || lastName.length() > Constraints.MAX_STRING_LENGTH) { 
			return ErrorConstants.STRING_FIELD_TOO_LONG;
		}

		if(designation.equals(Designation.STUDENT) && ((parentMobileNo == null || !parentMobileNo.matches(RegexCheck.MOBILENO)))) { 
			return ErrorConstants.INVALID_GUARDIAN_MOBILENO;
		}
		if(mobileNo == null || !mobileNo.matches(RegexCheck.MOBILENO)) { 
			return ErrorConstants.INVALID_MOBILENO;
		}


		if((designation.equals(Designation.STUDENT) && ((usn == null) || !usn.matches(RegexCheck.USN)))) {
			if(!parentMobileNo.matches(RegexCheck.MOBILENO)) { 
				return ErrorConstants.INVALID_MOBILENO;
			}
		}

		logger.info("Track 5");
		if(designation.equals(Designation.STUDENT) && ((parentMobileNo != null && !parentMobileNo.matches(RegexCheck.MOBILENO)))) { 
			return ErrorConstants.INVALID_MOBILENO;
		}

		if(designation.equals(Designation.STUDENT) && ((semester != null && !String.valueOf(semester).matches(RegexCheck.SEMESTER)))) { 
			return ErrorConstants.INVALID_SEMESTER;
		}

		logger.info("Track 6");
		if(address != null && address.length() > Constraints.MAX_TEXT_LENGTH) {
			return ErrorConstants.TEXT_FIELD_TOO_LONG;
		}

		if(!Arrays.asList(Designation.values()).contains(Designation.valueOf(designation))) {
			logger.info(designation);
			return ErrorConstants.INVALID_DESIGNATION;
		}

		logger.info("Track 7");
		if(!Arrays.asList(Department.values()).contains(Department.valueOf(dept))) {
			logger.info(dept);
			return ErrorConstants.INVALID_DEPARTMENT;
		}

		if(designation.equals(Designation.STUDENT) && ((section == null || !Arrays.asList(Section.values()).contains(Section.valueOf(section))))) {
			logger.info(section);
			return ErrorConstants.INVALID_SECTION;
		}
		return ErrorConstants.SUCCESS;
	}
	
	public static Person getPerson(String key, String value) {
		HashMap<String, String> conditions = new HashMap<String, String>();
		conditions.put(key, value);
		return getPerson(conditions);
	}
	
	public static Person getPerson(HashMap<String, String > conditions) {
		LinkedList<Person> personList = getPersonList(Helper.getSqlQuery("PERSON", conditions));
		if(personList != null && !personList.isEmpty()) {
			logger.info(personList.getFirst().toString());
			return personList.getFirst();
		}
		return null;
	}
	
	public static LinkedList<Person> getPersons(HashMap<String, String> conditions) {
		logger.info("Hi I am in get Persons");
		conditions = Helper.modifyConditions(conditions);
		return getPersonList(Helper.getSqlQuery("PERSON", conditions));
	}
	
	public static LinkedList<Person> getFacultyList() {
		return getPersonList("SELECT * FROM PERSON WHERE DESIGNATION != 'STUDENT'");
	}
	
	public static LinkedList<Person> getMobileList(String emailList) {
		String emailStr = StringEscapeUtils.escapeSql(emailList);
		String emails[] = emailStr.split("\\s*,\\s*");
		return getPersonList("SELECT * FROM PERSON WHERE " + F_EMAILID + " IN ('" + StringUtils.join(emails, "','") + "')");
	}
	
	private static LinkedList<Person> getPersonList(String query) {
		Connection conn = DBConnection.getConnection();
		ResultSet rs = null;
		Statement st = null;

		LinkedList<Person> personList = new LinkedList<Person>();

		Person person = null;
		try {
			st = conn.createStatement();

			logger.info("query: " + query);

			rs = st.executeQuery(query);
			if(rs.next()) {

				logger.info("Found record");
				do {
					person = new Person(
							rs.getString("EMAILID"),
							rs.getString("USN"),
							rs.getString("FIRSTNAME"),
							rs.getString("LASTNAME"),
							rs.getString("MOBILENO"),
							rs.getString("PARENT_MOBILE_NO"),
							rs.getString("DESIGNATION"),
							rs.getDate("DATEOFJOIN"),
							rs.getDate("BIRTHDATE"),
							rs.getString("ADDRESS"),
							rs.getString("CITY"),
							rs.getString("STATE"),
							rs.getString("COUNTRY"),
							rs.getString("PINCODE"),
							rs.getString("DEPT"),
							rs.getString("SECTION"),
							rs.getInt("SEMESTER"),
							rs.getString("PASSWORD"),
							rs.getBoolean("ISACTIVE")
					);
					personList.add(person);
				}while(rs.next());
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

		return personList;

	}

	public int insertPerson() {
		Connection conn = DBConnection.getConnection();
		Statement st = null;
		int rows = 0;

		try {
			st = conn.createStatement();

			String usn = StringEscapeUtils.escapeSql(this.usn);
			usn = usn == null ? usn = "NULL" : "'" + usn + "'";

			String parentMobileNo = StringEscapeUtils.escapeSql(this.parentMobileNo);
			parentMobileNo = parentMobileNo == null ? parentMobileNo = "NULL" : "'" + parentMobileNo + "'";

			String sec = StringEscapeUtils.escapeSql(this.section);
			sec = sec == null ? sec = "NULL" : "'" + sec + "'";

			String sem = String.valueOf(this.semester);
			sem = this.semester == null ? sem = "NULL" : "'" + sem + "'";

			
			String query = "INSERT INTO PERSON (`EMAILID`, `USN`,`FIRSTNAME`,`LASTNAME`,`MOBILENO`,`PARENT_MOBILE_NO`,`DESIGNATION`,`DATEOFJOIN`,"
				+ "`BIRTHDATE`,`ADDRESS`,`CITY`,`STATE`,`COUNTRY`,`PINCODE`, `DEPT`,`SECTION`,`SEMESTER`,`PASSWORD`,`ISACTIVE`) VALUES ('"
				+ StringEscapeUtils.escapeSql(this.emailId) + "', "
				+ usn + ",'"
				+ StringEscapeUtils.escapeSql(this.firstName) + "','"
				+ StringEscapeUtils.escapeSql(this.lastName) + "','"
				+ StringEscapeUtils.escapeSql(this.mobileNo) + "',"
				+ parentMobileNo + ",'"
				+ StringEscapeUtils.escapeSql(this.designation) + "','"
				+ this.dateOfJoin + "','"
				+ this.birthDate + "','"
				+ StringEscapeUtils.escapeSql(this.address) + "','"
				+ StringEscapeUtils.escapeSql(this.city) + "','"
				+ StringEscapeUtils.escapeSql(this.state) + "','"
				+ StringEscapeUtils.escapeSql(this.country) + "','"
				+ StringEscapeUtils.escapeSql(this.pincode) + "','" 	
				+ StringEscapeUtils.escapeSql(this.dept) + "',"
				+ sec + ","
				+ sem + ",'"
				+ StringEscapeUtils.escapeSql(this.password) + "',"
				+ this.isActive + ")";

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
	public int updatePerson() {
		Connection conn = DBConnection.getConnection();
		Statement st = null;
		int rows = 0;

		try {
			st = conn.createStatement();
			String query = "UPDATE PERSON SET "
				+ "`USN` = '" + StringEscapeUtils.escapeSql(this.usn) + "', "
				+ "`FIRSTNAME` = '" + StringEscapeUtils.escapeSql(this.firstName) + "', "
				+ "`LASTNAME` = '" + StringEscapeUtils.escapeSql(this.lastName) + "', "
				+ "`ADDRESS` = '" + StringEscapeUtils.escapeSql(this.address) + "', "
				+ "`CITY` = '" + StringEscapeUtils.escapeSql(this.address) + "', "
				+ "`STATE` = '" + StringEscapeUtils.escapeSql(this.address) + "', "
				+ "`PINCODE` = '" + StringEscapeUtils.escapeSql(this.address) + "', "
				+ "`COUNTRY` = '" + StringEscapeUtils.escapeSql(this.address) + "', "
				+ "`MOBILENO` = '" + StringEscapeUtils.escapeSql(this.mobileNo) + "', "
				+ "`PARENT_MOBILE_NO` = '" + StringEscapeUtils.escapeSql(this.parentMobileNo) + "', "
				+ "`DESIGNATION` = '" + StringEscapeUtils.escapeSql(this.designation) + "', "
				+ "`DATEOFJOIN` = '" + this.dateOfJoin + "', "
				+ "`BIRTHDATE` = '" + this.birthDate + "', "
				+ "`DEPT` = '" + StringEscapeUtils.escapeSql(this.dept) + "', "
				+ "`SECTION` = '" + StringEscapeUtils.escapeSql(String.valueOf(this.section)) + "', "
				+ "`SEMESTER` = '" + this.semester + "', "
				+ "`PASSWORD` = '" + StringEscapeUtils.escapeSql(this.password) + "', " 
				+ "`ISACTIVE` = " + this.isActive 
				+ " WHERE "
				+ "`EMAILID` = '" + StringEscapeUtils.escapeSql(this.emailId) + "'; ";
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
	public List<String> getContactList(String dept, String sec, Integer sem, String designation) {

		Connection conn = DBConnection.getConnection();

		ResultSet rs = null;
		Statement st = null;

		LinkedList<String> contactList = new LinkedList<String>();

		boolean flag = false;
		try {
			st = conn.createStatement();
			// DOUBTEntry
			String query = "SELECT MOBILENO FROM PERSON WHERE ";
			if(dept != null) {
				query += " DEPT = '" + StringEscapeUtils.escapeSql(dept);
				flag = true;
			}
			if(sec != null) {
				if(flag) {
					query += " AND ";
				}
				query += "' SECTION ='" + sec; 
				flag = true;
			}
			if(sem != null) {
				if(flag) {
					query += " AND ";
				}
				query += "' SEMESTER ="+ sem ;
				flag = true;
			}
			if(designation != null) {
				if(flag) {
					query += " AND ";
				}
				query += "' DESIGNATION ='" + designation; 
				flag = true;
			}
			logger.info("query: " + query);

			rs = st.executeQuery(query);
			if(rs.next()) {
				logger.info("Found record");

				do {
					contactList.add(rs.getString("mobileNo"));
				} while(rs.next());
			} else {
				logger.info("No record found");
			}

		} catch (SQLException e) {
			logger.error("getSMS exception", e);
		} finally {
			try {
				conn.close();
			} catch(Exception ignored) { }
		}

		return contactList;
	}
	
	@Override
	public String toString() {
		return this.getEmailId() + " " + this.getMobileNo();
	}

	public static void main(String args[]) {
		System.out.println(Section.valueOf("A"));
	}
}

