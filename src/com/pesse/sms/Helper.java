package com.pesse.sms;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

public class Helper
{
	public final static String PARAM_HASH = "hash";
	private static final String NUM_STRING = "0123456789";
	private static final String ALPHA_NUM_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static Logger logger = Logger.getLogger(Helper.class);
	
	public static boolean verifyData(String data, String hash)
	{
		String md5Hex = DigestUtils.md5Hex(data);
		if(hash.equals(md5Hex))
			return true;
		return false;
	}

	public static boolean verifyParams(Map<String, String> params, String clientHashKey)
	{
		String hash = params.get(PARAM_HASH);
		if(hash == null || hash.length() == 0)
			return false;
		String md5Hex = genHash(params, clientHashKey);
		if(hash.equals(md5Hex))
			return true;
		return false;
	}

	public static String genHash(Map<String, String> params, String clientHashKey)
	{
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		StringBuilder strb = new StringBuilder(100);
		for(String sk: keys)
		{
			if(!sk.equals(PARAM_HASH))
				strb.append(params.get(sk) + "|");
		}
		strb.append(clientHashKey);
		logger.info(strb);

		return DigestUtils.md5Hex(strb.toString());
	}
	
	public static String getRandomString(int length) {
		StringBuffer randomStr = new StringBuffer();
		
		for(int i = 0; i < length; i++) {
			randomStr.append(ALPHA_NUM_STRING.charAt((int) Math.floor(Math.random() * ALPHA_NUM_STRING.length()))); 
		}
		return randomStr.toString();
	}

	public static String getRandomNumberString(int length) {
		StringBuffer randomStr = new StringBuffer();
		
		for(int i = 0; i < length; i++) {
			randomStr.append(NUM_STRING.charAt((int) Math.floor(Math.random() * NUM_STRING.length()))); 
		}
		return randomStr.toString();
	}

	public static String escapeJson(String string)
	{
		return string != null ? string.replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t") : null;
	}
	
	public static String getStringFromJson(JSONObject params, String key) {
		
		return (params != null && params.get(key) != null) ? params.getString(key) : "";
	}
	
	public static String getStringFromMap(HashMap<String, String> map, String key) {
		
		return (map != null && map.containsKey(key)) ? map.get(key) : "";
	}
	
	public static String getStringFromRs(ResultSet rs, String key) throws SQLException {
		return (rs != null && rs.getObject(key) != null) ? rs.getString(key) : "";
	}

	public static String httpBuildQuery(Map<String, String> map) {
		
		StringBuffer queryString = new StringBuffer();
		
		if(map == null || map.size() == 0) {
			return "";
		}
		
		for(Entry<String, String> e : map.entrySet()) {
			try {
				queryString.append(URLEncoder.encode(e.getKey(), "UTF-8") + "=");
				queryString.append(URLEncoder.encode(e.getValue(), "UTF-8") + "&");
			} catch (UnsupportedEncodingException exception) { // Will never happen
				logger.error("URL Encoding exception", exception);
				return null;
			}
		}

		if(queryString.length() > 0) {
			queryString.deleteCharAt(queryString.length() - 1);
		}

		return queryString.toString();
	}

	public static HashMap<String, String> httpParseQuery(String url) {
		
		String query = null, key = null, value = null;
		HashMap<String, String> paramsMap = new HashMap<String, String>();
		
		if(url == null || url.length() == 0) {
			return paramsMap;
		}

		int pos = url.indexOf("?");
		query = (pos < 0) ? url : url.substring(pos);

		for (String param : query.split("&")) {
			
			String[] pair = param.split("=");
			if(pair.length == 0) continue;
			
			try {
				key = URLDecoder.decode(pair[0], "UTF-8");
				value = (pair.length > 1) ? URLDecoder.decode(pair[1], "UTF-8") : "";
			} catch (UnsupportedEncodingException e) {
				logger.error("Unsupported encoding", e);
			}

			paramsMap.put(key, value);
		}
		return paramsMap;
	}
	
	public static void mapToJson(HashMap<String, String> map, JSONObject json) { 
		if(map == null) {
			return;
		}
		
		for(Entry<String, String> e: map.entrySet()) {
			json.put(e.getKey(), e.getValue());
		}
	}
	/*
	public static String compressNumStr(String numStr, boolean keepFixedLength) {
		long num = 0;
		try {
			num = Long.parseLong(numStr);
		} catch(NumberFormatException e) {
			logger.error("compressNumStr", e);
			return null;
		}
		
		StringBuffer compressedStr = new StringBuffer("");
		while(num > 0) {
			compressedStr.append(ALPHA_NUM_STRING.charAt((int) (num % ALPHA_NUM_STRING.length())));
			num /= ALPHA_NUM_STRING.length();
		}
		
		if(keepFixedLength) {
			int fixedLength = (int)Math.ceil(64 * Math.log(2) / Math.log(ALPHA_NUM_STRING.length()));
			logger.info("fixedLength = " + fixedLength);
			
			while(compressedStr.length() < fixedLength) {
				compressedStr.append("0");
			}
		}
		
		compressedStr.reverse();
		logger.info(numStr + " is compressed to " + compressedStr);
		return compressedStr.toString();
	}
	
	public static String decompressToNumStr(String compressedStr) {
		long decompressedNum = 0;
		
		StringBuffer revCompressedStr = new StringBuffer(compressedStr).reverse();
		for(int i = 0; i < revCompressedStr.length(); i++) {
			decompressedNum += ALPHA_NUM_STRING.indexOf(revCompressedStr.charAt(i)) * Math.pow(ALPHA_NUM_STRING.length(), i);
		}
		
		String decompressedNumStr = String.valueOf(decompressedNum);
		logger.info(compressedStr + " is decompressed to " + decompressedNumStr);
		return decompressedNumStr;
	}
	*/
	public static void writeStartHtml(PrintWriter writer, HashMap<String, String> map) {
		
		writer.println("<!doctype html>");
		writer.println("<html><head>");
		writer.println("<title>" + Helper.getStringFromMap(map, "title") + "</title>");
		writer.println("</head><body>");
	}
	
	public static void writeEndHtml(PrintWriter writer) {
		
		writer.println("</body></html>");
	}
	
	public static  HashMap<String, String> modifyConditions(HashMap<String, String> conditions) {
		
		if(Designation.HEADOFDEPT.name().equals(conditions.get(Person.F_DESIGNATION))) {
				conditions.remove(Person.F_SECTION);
				conditions.remove(Person.F_SEMESTER);
		}
		if(Designation.ADMINISTRATIVE.name().equals(conditions.get(Person.F_DESIGNATION)) || Designation.ADMIN.name().equals(conditions.get(Person.F_DESIGNATION)) || Designation.MANAGEMENT.name().equals(conditions.get(Person.F_DESIGNATION))) {
			conditions.remove(Person.F_DEPT);
			conditions.remove(Person.F_SECTION);
			conditions.remove(Person.F_SEMESTER);
		}
		logger.info("Helper modified conditions " + conditions);
		return conditions;
	}
	
	public static String getSqlQuery(String tableName, HashMap<String, String> conditions) {

		String query = null;
		
		if(conditions == null || conditions.isEmpty()) {
			query = "SELECT * FROM " + tableName;
			return query;
		}
		
		for(String value : conditions.values()) {
			if(value != null && value.trim().length() > 0) {
				query = "SELECT * FROM " + tableName + " WHERE ";
				boolean first = true;
				for(Entry<String, String> e: conditions.entrySet()) {
					String key = e.getKey();
					String val = e.getValue();
					
					if(val == null || val.trim().length() == 0) continue;
					if(!first) {
						query += "AND ";
					} 
					if(val.equals("true") || val.equals("false")) {
						query += key + " = " + val + " ";
					} else {
						query += key + " = '" + val + "' ";
					} 
					first = false;
				}
				return query;
			}
		}

		query = "SELECT * FROM " + tableName;
		return query;
	}
	
	public static void main(String[] args) {
		
//		for(int i = 0; i < 100; i++) {
//			String randomNumStr = getRandomNumberString();
//			String compressedStr = compressNumStr(randomNumStr, false);
//			String decompressedStr = decompressToNumStr(compressedStr);
//			
//			if(!decompressedStr.equals(randomNumStr)) {
//				System.out.println(i + ") Error: Num = " + randomNumStr + ", Compr = " + compressedStr + ", Decompr = " + decompressedStr);
//			}
//		}
		
		HashMap<String, String> map = new HashMap<String, String>();
		long timer = System.currentTimeMillis();
		
		for(int i = 0; i < 10; i++) {
			//map.put(Helper.getRandomString(7), "");
			System.out.println(Helper.getRandomNumberString(20));
		}

		System.out.println("Size = " + map.size() + "Time elapsed: " + (System.currentTimeMillis() - timer));
	}
}
