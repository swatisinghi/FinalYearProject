package com.pesse.sms;

import com.pesse.sms.common.Config;
import com.pesse.sms.common.ConfigKeys;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Vector;

public class Way2sms {
	
	public static final String GATEWAY_USERID = Config.getProperty(ConfigKeys.GATEWAY_USERID);
	public static final String GATEWAY_PASSWORD = Config.getProperty(ConfigKeys.GATEWAY_PASSWORD);
	
	public static void send(String uid, String pwd, String phone, String msg)
			throws IOException {

		if (uid == null || 0 == uid.length())
			throw new IllegalArgumentException("User ID should be present.");
		else
			uid = URLEncoder.encode(uid, "UTF-8");

		if (pwd == null || 0 == pwd.length())
			throw new IllegalArgumentException("Password should be present.");
		else
			pwd = URLEncoder.encode(pwd, "UTF-8");

		if (phone == null || 0 == phone.length())
			throw new IllegalArgumentException("At least one phone number should be present.");

		if (msg == null || 0 == msg.length())
			throw new IllegalArgumentException("SMS message should be present.");
		else
			msg = URLEncoder.encode(msg, "UTF-8");

		Vector<Long> numbers = new Vector<Long>();
		String pharr[];
		if (phone.indexOf(';') >= 0) {
			pharr = phone.split(";");
			for (String t : pharr) {
				try
				{
					numbers.add(Long.valueOf(t));
				}
				catch (NumberFormatException ex)
				{
					throw new IllegalArgumentException("Give proper phone numbers.");
				}
			}
		} else {
			try
			{
				numbers.add(Long.valueOf(phone));
			}
			catch (NumberFormatException ex)
			{
				throw new IllegalArgumentException("Give proper phone numbers.");
			}
		}

		if (0 == numbers.size())
			throw new IllegalArgumentException("At least one proper phone number should be present to send SMS.");

		String temp = "";
		String content = "username=" + uid + "&password=" + pwd;
		URL u = new URL("http://wwwd.way2sms.com/auth.cl");
		HttpURLConnection uc = (HttpURLConnection) u.openConnection();
		uc.setDoOutput(true);
		uc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.0.5) Gecko/2008120122 Firefox/3.0.5");
		uc.setRequestProperty("Content-Length", String.valueOf(content.length()));
		uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		uc.setRequestProperty("Accept", "*/*");
		uc.setRequestMethod("POST");
		uc.setInstanceFollowRedirects(false); // very important line :)
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(uc.getOutputStream()), true);
		pw.print(content);
		pw.flush();
		pw.close();
		BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
		while ( (temp = br.readLine()) != null ) {}
		String cookie = uc.getHeaderField("Set-Cookie");

		// Send SMS to each of the phone numbers
		u = null; uc = null;
		for (long num : numbers)
		{
			content = "catnamedis=Birthday&HiddenAction=instantsms&Action=sa65sdf656fdfd&login=&pass=&MobNo=" + num + "&textArea=" + msg + "&qlogin1=Gmail+Id&qpass1=******&gincheck=on&ylogin1=Yahoo+Id&ypass1=******&yincheck=on";
			u = new URL("http://wwwd.way2sms.com/FirstServletsms?custid=");
			uc = (HttpURLConnection) u.openConnection();
			uc.setDoOutput(true);
			uc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.0.5) Gecko/2008120122 Firefox/3.0.5");
			uc.setRequestProperty("Content-Length", String.valueOf(content.getBytes().length));
			uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			uc.setRequestProperty("Accept", "*/*");
			uc.setRequestProperty("Cookie", cookie);
			uc.setRequestMethod("POST");
			uc.setInstanceFollowRedirects(false);
			pw = new PrintWriter(new OutputStreamWriter(uc.getOutputStream()), true);
			pw.print(content);
			pw.flush();
			pw.close();
			br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			while ( (temp = br.readLine()) != null ) {}
			br.close();
			u = null;
			uc = null;
		}
		
		u = new URL("http://wwwd.way2sms.com/jsp/logout.jsp");
		uc = (HttpURLConnection) u.openConnection();
		uc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.0.5) Gecko/2008120122 Firefox/3.0.5");
		uc.setRequestProperty("Accept", "*/*");
		uc.setRequestProperty("Cookie", cookie);
		uc.setRequestMethod("GET");
		uc.setInstanceFollowRedirects(false);
		br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
		while ( (temp = br.readLine()) != null ) {}
		br.close();
		u = null;
		uc = null;
	}
	
	public static void sendSms(List<String> mobileNoList, String message) {
		if(mobileNoList == null) {
			return;
		}
		
		for(String mobileNo : mobileNoList) {
			sendSms(mobileNo, message);
		}
	}
	
	public static void sendSms(String mobileNoList[], String message) {
		if(mobileNoList == null) {
			return;
		}
		
		for(String mobileNo : mobileNoList) {
			sendSms(mobileNo, message);
		}
	}
	
	public static boolean sendSms(String mobileNo, String message) {
		try {
			send(GATEWAY_USERID, GATEWAY_PASSWORD, mobileNo, message);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static void main(String args[]) {
                System.out.println("Swatis");
		sendSms("9831516276", "comeonline!!!");
	}
}
