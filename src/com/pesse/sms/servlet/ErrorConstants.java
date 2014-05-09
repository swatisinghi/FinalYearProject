package com.pesse.sms.servlet;

import com.pesse.sms.common.Constraints;

public enum ErrorConstants {
	
	INVALID_LOGIN_CREDENTIALS("Invalid login credentials"),
	INVALID_INSTITUTE_PASSWORD("Invalid institute password"),
	INVALID_DESIGNATION("Invalid Position"),
	INVALID_DEPARTMENT("Invalid Department"),
	INVALID_EMAILID("Invalid EmailId"),
	INVALID_GUARDIAN_MOBILENO("Invalid Guardian Mobile No"),
	INVALID_USN("Invalid USN"),
	INVALID_SENDER("Student can send a message only to a student"),
	INVALID_MOBILENO("Invalid MobileNo"),
	INVALID_PINCODE("Invalid Pincode"),
	INVALID_SEMESTER("Invalid Semester"),
	INVALID_SECTION("Section can only be 'A' or 'B'"),
	INVALID_USERNAME("Username can contain only letters and numbers " 
			+ Constraints.MIN_ID_LENGTH + " to " + Constraints.MAX_ID_LENGTH + " characters long"),
	INVALID_PASSWORD("Password should contain at least one number and one character "
			+ Constraints.MIN_PASSWORD_LENGTH + " to " + Constraints.MAX_PASSWORD_LENGTH + " characters long"),
	INVALID_DATE("Invalid date"),
	USERNAME_ALREADY_TAKEN("This username is not available"),
	MAX_EMPLOYEE_LIMIT_REACHED("Cannot add more employees for your organization"),
	INVALID_NUMBER("Field is not a number"),
	PASSWORD_MISMATCH("Passwords did not match"),
	STRING_FIELD_TOO_LONG("Field to long. Maximum characters allowed is " + Constraints.MAX_STRING_LENGTH),
	TEXT_FIELD_TOO_LONG("Field to long. Maximum characters allowed is " + Constraints.MAX_TEXT_LENGTH),
	SYSTEM_ERROR("There was an internal error"),
	ACCOUNT_INACTIVE("Your account is inactive. Please check your mail to activate your account"),
	MESSAGE_SENT("Message Sent....."),
	SUCCESS("OK");
	
	private String message;
	
	ErrorConstants(String message) {
		setMessage(message);
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
