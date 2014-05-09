package com.pesse.sms;

public enum Semester {
	
	SEM1("1"),
	SEM2("2"),
	SEM3("3"),
	SEM4("4"),
	SEM5("5"),
	SEM6("6"),
	SEM7("7"),
	SEM8("8"),
	SEM9("9");
	
	
	private String message;
	
	Semester(String message) {
		setMessage(message);
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
