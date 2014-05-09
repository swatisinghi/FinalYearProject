package com.pesse.sms.common;

public class RegexCheck {
	public static final String EMAIL_ID = "[0-9a-zA-Z_]+@[a-zA-Z]+\\.[a-zA-Z]+";
	public static final String PASSWORD = "(.*\\d.*[a-zA-Z]+.*|.*[a-zA-Z]+.*\\d.*)";
	public static final String MOBILENO = "[0-9]{10}";
	public static final String PINCODE = "[0-9]{6}";
	public static final String USN = "[1-4]{1}[a-zA-z]{2}0[1-9]{1}[a-zA-Z]{2}[0-9]{3}";
	public static final String SEMESTER = "[1-8]{1}";
	public static final String DATE = "[0-9]{4}-[0-1][0-9]-[0-3][0-9]";

	public static void main(String args[]) {
		System.out.println("a@a.com".matches(RegexCheck.EMAIL_ID));
		System.out.println("zz".matches(RegexCheck.PASSWORD));
		System.out.println("asdes123".matches(RegexCheck.PASSWORD));
		System.out.println("9731231268123".matches(RegexCheck.MOBILENO));
		System.out.println("1pe07cs108".matches(RegexCheck.USN));
	}
}
